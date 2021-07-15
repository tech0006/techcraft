package com.tech0006.techcraft.blocks.TileEntity.base;

import com.tech0006.techcraft.util.tools.MachineEnergy;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class PoweredTile extends TileEntity implements ITickableTileEntity {
    protected final MachineEnergy energy = new MachineEnergy(500_000);
    protected int energyUse;

    public void setEnergyUse(int energyUse) {
        this.energyUse = energyUse;
    }

    public PoweredTile(TileEntityType tileEntityTypeIn, int energyUse) {
        super(tileEntityTypeIn);
        this.energyUse = energyUse;
    }


    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        energy.readNBT(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        energy.writeNBT(compound);
        return compound;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        /**/
        if (!removed && CapabilityEnergy.ENERGY == cap) {
            return energy.lazyCast();
        } else {
            return super.getCapability(cap, side);
        }
    }

    @Override
    public void remove() {
        super.remove();
        energy.invalidate();
    }

    public MachineEnergy getEnergy() {
        return this.energy;
    }

    public int getEnergyStored() {
        return this.energy.getEnergyStored();
    }

    public int getMaxEnergy() {
        return this.energy.getMaxEnergyStored();
    }
}
