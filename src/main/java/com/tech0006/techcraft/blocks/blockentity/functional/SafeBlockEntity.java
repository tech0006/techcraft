package com.tech0006.techcraft.blocks.blockentity.functional;

import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class SafeBlockEntity extends BlockEntity {

    private TCItemHandler inventory;

    public SafeBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.inventory = new TCItemHandler(36);
    }

    public SafeBlockEntity(BlockPos pos, BlockState state) {
        this(TCBlockEntityTypes.SAFE.get(), pos, state);
        this.inventory = new TCItemHandler(36);
    }


    public TCItemHandler getInventory() {
        return this.inventory;
    }

    public void setItems(TCItemHandler itemsIn) {
        this.inventory = itemsIn;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());

        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }
}