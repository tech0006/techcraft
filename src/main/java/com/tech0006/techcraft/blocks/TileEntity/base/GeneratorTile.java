package com.tech0006.techcraft.blocks.TileEntity.base;

import com.tech0006.techcraft.util.tools.GeneratorEnergy;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class GeneratorTile extends TileEntity implements ITickableTileEntity {
    protected final GeneratorEnergy energy = new GeneratorEnergy(500_000);
    protected int energyUse;

    public void setEnergyUse(int energyUse) {
        this.energyUse = energyUse;
    }

    public GeneratorTile(TileEntityType tileEntityTypeIn, int energyUse) {
        super(tileEntityTypeIn);
        this.energyUse = energyUse;
    }


    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        energy.readNBT(compound);
        energyUse = compound.getInt("Use");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        energy.writeNBT(compound);
        compound.putInt("Use", energyUse);
        return compound;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        /**/
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

    public GeneratorEnergy getEnergy() {
        return this.energy;
    }

    public int getEnergyStored() {
        return this.energy.getEnergyStored();
    }

    public int getMaxEnergy() {
        return this.energy.getMaxEnergyStored();
    }
}

