package com.tech0006.techcraft.blocks.tileentity.functional;

import com.tech0006.techcraft.common.container.ElectricFurnaceContainer;
import com.tech0006.techcraft.blocks.functional.CoalGenerator;
import com.tech0006.techcraft.blocks.tileentity.base.PoweredTile;
import com.tech0006.techcraft.blocks.tileentity.update.UpdateElectricFurnace;
import com.tech0006.techcraft.common.registration.TCContainerTypes;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;
import com.tech0006.techcraft.common.packet.PacketHandler;
import com.tech0006.techcraft.techcraft;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ElectricFurnaceTileEntity extends PoweredTile implements INamedContainerProvider {

    public TCItemHandler inventory;

    public int processTime, processTimeTotal;
    public AbstractCookingRecipe r;


    public ElectricFurnaceTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, 2);
        inventory = new TCItemHandler(4);
    }

    public ElectricFurnaceTileEntity() {
        super(TCTileEntityTypes.ELECTRIC_FURNACE.get(), 2);
        inventory = new TCItemHandler(4);
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {

        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        this.processTime = compound.getInt("CurrBurnTime");
        this.processTimeTotal = compound.getInt("SumBurnTime");


        super.load(state, compound);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {


        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList(), false);
        compound.putInt("CurrBurnTime", this.processTime);
        compound.putInt("SumBurnTime", this.processTimeTotal);

        return super.save(compound);
    }

    @Override
    public void tick() {

        if (level != null && !this.level.isClientSide) {
            if (isBurn()) {
                if (getEnergyStored() >= energyUse) {
                    processTime--;
                    energy.use(energyUse);
                    if (!isBurn()) {
                        processTime = processTimeTotal = 0;
                        inventory.insertItem(1, inventory.getStackInSlot(2).copy(), false);
                        r = null;
                        inventory.setStackInSlot(2, ItemStack.EMPTY);
                        inventory.setStackInSlot(3, ItemStack.EMPTY);
                        this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CoalGenerator.LIT, false));
                    }
                }
            } else {
                if (inventory.getStackInSlot(0) != ItemStack.EMPTY) {
                    AbstractCookingRecipe recipe = getRecipeBlasting();
                    if (recipe == null) recipe = getRecipeSmelting();
                    if (recipe != null) {
                        if (inventory.getStackInSlot(1).getItem() == recipe.getResultItem().getItem() || inventory.getStackInSlot(1).getItem() == ItemStack.EMPTY.getItem()) {
                            if (inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getItem().getItemStackLimit(inventory.getStackInSlot(1))) {
                                r = recipe;
                                inventory.setStackInSlot(2, r.getResultItem());
                                inventory.setStackInSlot(3, inventory.getStackInSlot(0).copy());
                                processTime = processTimeTotal = r.getCookingTime();
                                inventory.decrStackSize(0, 1);

                                this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CoalGenerator.LIT, true));
                            }
                        }
                    } else {
                        r = null;
                        inventory.setStackInSlot(2, ItemStack.EMPTY);
                        inventory.setStackInSlot(3, ItemStack.EMPTY);
                    }
                }
            }
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateElectricFurnace(getBlockPos(), energy, energyUse, processTime, processTimeTotal));
        }
    }

    public TCItemHandler getInventory() {
        return inventory;
    }

    public int getCost() {
        if (this.processTime > 0) {
            return this.energyUse;
        } else {
            return 0;
        }
    }

    public boolean isBurn() {
        return this.processTime > 0;
    }

    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        assert level != null;
        return new ElectricFurnaceContainer(TCContainerTypes.ELECTRIC_FURNACE.get(), id, level, worldPosition, playerEntity, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container."+ techcraft.MOD_ID+".electric_furnace");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this.inventory));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return new SUpdateTileEntityPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.load(this.getBlockState(), nbt);
    }

    public BlastingRecipe getRecipeBlasting() {
        Set<IRecipe<?>> recipes = findRecipesByType(IRecipeType.BLASTING, this.level);
        for (IRecipe<?> iRecipe : recipes) {
            BlastingRecipe recipe = (BlastingRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.level)) {
                return recipe;
            }
        }
        return null;
    }

    public FurnaceRecipe getRecipeSmelting() {
        Set<IRecipe<?>> recipes = findRecipesByType(IRecipeType.SMELTING, this.level);
        for (IRecipe<?> iRecipe : recipes) {
            FurnaceRecipe recipe = (FurnaceRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.level)) {
                return recipe;
            }
        }
        return null;
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn) {
        ClientWorld world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

}
