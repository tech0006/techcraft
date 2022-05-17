package com.tech0006.techcraft.blocks.blockentity.base;

import com.tech0006.techcraft.util.tools.energy.InEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;


public abstract class PoweredTile extends BlockEntity {
    protected final InEnergy energy = new InEnergy(500_000);
    protected int energyUse;

    public void setEnergyUse(int energyUse) {
        this.energyUse = energyUse;
    }

    public PoweredTile(BlockEntityType blockEntityTypeIn, BlockPos pos, BlockState state, int energyUse) {
        super(blockEntityTypeIn, pos, state);
        this.energyUse = energyUse;
    }


    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        energy.readNBT(compound);
        energyUse = compound.getInt("Use");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        energy.writeNBT(compound);
        compound.putInt("Use", energyUse);
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

    public InEnergy getEnergy() {
        return this.energy;
    }

    public int getEnergyStored() {
        return this.energy.getEnergyStored();
    }

    public int getMaxEnergy() {
        return this.energy.getMaxEnergyStored();
    }
}
