package com.tech0006.techcraft.util.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public abstract class EnergyPart extends SelfLazy<IEnergyStorage> implements IEnergyStorage {
    protected int max;
    protected int stored;

    protected EnergyPart(int max) {
        this.max = max;
    }

    public final int getEnergy16Bit(boolean low) {
        return low ? stored & 0xFFFF : stored >> 16;
    }

    public final void setEnergy16Bit(boolean low, int v) {
        stored = low ? (stored & 0xFFFF0000) + v : (stored & 0xFFFF) + (v << 16);
    }

    public void readNBT(CompoundNBT nbt) {
        stored = MathHelper.clamp(nbt.getInt("E"), 0, max);
    }

    public void writeNBT(CompoundNBT nbt) {
        nbt.putInt("E", stored);
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

    @Nonnull
    @Override
    public final IEnergyStorage get() {
        return this;
    }
}
