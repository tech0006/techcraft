package com.tech0006.techcraft.blocks.TileEntity;

import com.tech0006.techcraft.GUI.Container.ElectricFurnaceContainer;
import com.tech0006.techcraft.blocks.CoalGenerator;
import com.tech0006.techcraft.blocks.TileEntity.base.PoweredTile;
import com.tech0006.techcraft.blocks.TileEntity.update.UpdateElectricFurnace;
import com.tech0006.techcraft.init.ModContainerTypes;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.util.handler.ElectricFurnaceItemHandler;
import com.tech0006.techcraft.util.handler.PacketHandler;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ElectricFurnaceTileEntity extends PoweredTile implements INamedContainerProvider {

    public ElectricFurnaceItemHandler inventory;

    public int processTime, processTimeTotal;
    public AbstractCookingRecipe r;

    public ItemStack getCurrRecipeInput() {
        return inventory.getStackInSlot(3);
    }

    public ElectricFurnaceTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, 2);
        inventory = new ElectricFurnaceItemHandler(4);
    }

    public ElectricFurnaceTileEntity() {
        super(ModTileEntityTypes.ELECTRIC_FURNACE.get(), 2);
        inventory = new ElectricFurnaceItemHandler(4);
    }

    @Override
    public void read(CompoundNBT compound) {

        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        this.processTime = compound.getInt("CurrBurnTime");
        this.processTimeTotal = compound.getInt("SumBurnTime");


        super.read(compound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundNBT write(CompoundNBT compound) {


        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList(), false);
        compound.putInt("CurrBurnTime", this.processTime);
        compound.putInt("SumBurnTime", this.processTimeTotal);

        return super.write(compound);
    }

    @Override
    public void tick() {

        if (!this.world.isRemote) {
            if (isBurn()) {
                if (getEnergyStored() >= energyUse) {
                    processTime--;
                    energy.use(energyUse);
                    if (!isBurn()) {
                        processTime = processTimeTotal = 0;
                        inventory.insertItem(1, inventory.getStackInSlot(2), false);
                        r = null;
                        inventory.setStackInSlot(2, ItemStack.EMPTY);
                        inventory.setStackInSlot(3, ItemStack.EMPTY);
                        this.world.setBlockState(this.getPos(), this.getBlockState().with(CoalGenerator.LIT, false));
                    }
                }
            } else {
                if (inventory.getStackInSlot(0) != ItemStack.EMPTY) {
                    AbstractCookingRecipe recipe = getRecipeBlasting();
                    if (recipe == null) recipe = getRecipeSmelting();
                    if (recipe != null) {
                        if (inventory.getStackInSlot(1).getItem() == recipe.getRecipeOutput().getItem() || inventory.getStackInSlot(1).getItem() == ItemStack.EMPTY.getItem()) {
                            if (inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getItem().getItemStackLimit(inventory.getStackInSlot(1))) {
                                r = recipe;
                                //out = r.getRecipeOutput();
                                inventory.setStackInSlot(2, r.getRecipeOutput());
                                inventory.setStackInSlot(3, inventory.getStackInSlot(0).copy());
                                processTime = processTimeTotal = r.getCookTime();
                                inventory.decrStackSize(0, 1);

                                this.world.setBlockState(this.getPos(), this.getBlockState().with(CoalGenerator.LIT, true));
                            }
                        }
                    } else {
                        r = null;
                        inventory.setStackInSlot(2, ItemStack.EMPTY);
                        inventory.setStackInSlot(3, ItemStack.EMPTY);
                    }
                }
            }
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateElectricFurnace(getPos(), energy, energyUse, processTime, processTimeTotal));
        }
    }

    public ElectricFurnaceItemHandler getInventory() {
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

    @Nullable
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ElectricFurnaceContainer(ModContainerTypes.ELECTRIC_FURNACE.get(), id, world, pos, playerEntity, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getBlockState().getBlock().getTranslationKey());
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this.inventory));
        }
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT nbt) {
        this.read(nbt);
    }

    @Nullable
    public BlastingRecipe getRecipeBlasting() {
        Set<IRecipe<?>> recipes = findRecipesByType(IRecipeType.BLASTING, this.world);
        for (IRecipe<?> iRecipe : recipes) {
            BlastingRecipe recipe = (BlastingRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    public FurnaceRecipe getRecipeSmelting() {
        Set<IRecipe<?>> recipes = findRecipesByType(IRecipeType.SMELTING, this.world);
        for (IRecipe<?> iRecipe : recipes) {
            FurnaceRecipe recipe = (FurnaceRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
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
        ClientWorld world = Minecraft.getInstance().world;
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

}
