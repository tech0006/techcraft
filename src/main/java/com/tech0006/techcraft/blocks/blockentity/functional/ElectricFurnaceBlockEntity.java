package com.tech0006.techcraft.blocks.blockentity.functional;

import com.tech0006.techcraft.blocks.blockentity.base.PoweredTile;
import com.tech0006.techcraft.blocks.blockentity.update.UpdateElectricFurnace;
import com.tech0006.techcraft.blocks.functional.CoalGenerator;
import com.tech0006.techcraft.common.packet.PacketHandler;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ElectricFurnaceBlockEntity extends PoweredTile  {

    public TCItemHandler inventory;

    public int processTime, processTimeTotal;
    public AbstractCookingRecipe r;


    public ElectricFurnaceBlockEntity(BlockEntityType tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state, 2);
        inventory = new TCItemHandler(4);
    }

    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ELECTRIC_FURNACE.get(), pos, state, 2);
        inventory = new TCItemHandler(4);
    }

    @Override
    public void load(CompoundTag compound) {

        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        this.processTime = compound.getInt("CurrBurnTime");
        this.processTimeTotal = compound.getInt("SumBurnTime");


        super.load(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {


        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList(), false);
        compound.putInt("CurrBurnTime", this.processTime);
        compound.putInt("SumBurnTime", this.processTimeTotal);

        return super.save(compound);
    }

    public void tick(BlockState state) {

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

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this.inventory));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag Tag = new CompoundTag();
        this.save(Tag);
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, Tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag Tag = new CompoundTag();
        this.save(Tag);
        return Tag;
    }

    @Override
    public void deserializeNBT(CompoundTag Tag) {
        this.load(Tag);
    }

    public BlastingRecipe getRecipeBlasting() {
        Set<Recipe<?>> recipes = findRecipesByType(RecipeType.BLASTING, this.level);
        for (Recipe<?> iRecipe : recipes) {
            BlastingRecipe recipe = (BlastingRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.level)) {
                return recipe;
            }
        }
        return null;
    }

    public SmeltingRecipe getRecipeSmelting() {
        Set<Recipe<?>> recipes = findRecipesByType(RecipeType.SMELTING, this.level);
        for (Recipe<?> iRecipe : recipes) {
            SmeltingRecipe recipe = (SmeltingRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.level)) {
                return recipe;
            }
        }
        return null;
    }

    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn, Level level) {
        return level != null ? level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn) {
        ClientLevel level = Minecraft.getInstance().level;
        return level != null ? level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

}
