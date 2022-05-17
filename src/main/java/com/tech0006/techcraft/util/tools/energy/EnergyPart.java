package com.tech0006.techcraft.util.tools.energy;

import com.tech0006.techcraft.util.tools.NNSupplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.IEnergyStorage;


public abstract class EnergyPart extends NNSupplier<IEnergyStorage> implements IEnergyStorage {
    protected int max;
    protected int stored;

    protected EnergyPart(int max) {
        this.max = max;
    }

    public void readNBT(CompoundTag nbt) {
        stored = nbt.getInt("energy");
        max = nbt.getInt("Max");
    }

    public void writeNBT(CompoundTag nbt) {
        nbt.putInt("energy", stored);
        nbt.putInt("Max", max);
    }

    @Override
    public final int getEnergyStored() {
        return stored;
    }

    @Override
    public final int getMaxEnergyStored() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setStored(int stored) {
        this.stored = stored;
    }

    @Override
    public final IEnergyStorage get() {
        return this;
    }
}
