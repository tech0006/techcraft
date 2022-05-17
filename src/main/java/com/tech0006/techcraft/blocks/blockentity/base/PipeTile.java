package com.tech0006.techcraft.blocks.blockentity.base;

import com.tech0006.techcraft.util.tools.energy.OutInEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;


public abstract class PipeTile extends BlockEntity {
    protected final OutInEnergy energy = new OutInEnergy(5);
    protected int use_val = 1;

    public PipeTile(BlockEntityType tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        energy.readNBT(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        energy.writeNBT(compound);
        return compound;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!isRemoved() && CapabilityEnergy.ENERGY == cap) {
            return energy.lazyCast();
        } else {
            return super.getCapability(cap, side);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energy.invalidate();
    }

    public OutInEnergy getEnergy() {
        return this.energy;
    }

    public int getEnergyStored() {
        return this.energy.getEnergyStored();
    }

}

