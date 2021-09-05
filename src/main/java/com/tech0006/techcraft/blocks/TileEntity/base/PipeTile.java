package com.tech0006.techcraft.blocks.TileEntity.base;

import com.tech0006.techcraft.util.tools.MachineEnergy;
import com.tech0006.techcraft.util.tools.PipeEnergy;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class PipeTile extends TileEntity implements ITickableTileEntity {
    protected final PipeEnergy energy = new PipeEnergy(5);


    public PipeTile(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }


    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        energy.readNBT(compound);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
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

    public PipeEnergy getEnergy() {
        return this.energy;
    }

    public int getEnergyStored() {
        return this.energy.getEnergyStored();
    }
}

