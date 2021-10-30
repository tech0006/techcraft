package com.tech0006.techcraft.util.tools;

public class PipeEnergy extends EnergyPart {

    public PipeEnergy(int max) {
        super(max);
    }


    public boolean use(int amount) {
        boolean b = stored >= amount;
        if (b) {
            stored -= amount;
        }
        return b;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (maxReceive <= 0) return 0;
        int r = Math.min(maxReceive, max - stored);
        if (r > 0 && !simulate) {
            stored += r;
        }
        return r;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (maxExtract <= 0) return 0;
        int r = Math.min(maxExtract, max - stored);
        if(r > 0 && !simulate)
        {
            stored -= r;
        }
        return r;
    }
}