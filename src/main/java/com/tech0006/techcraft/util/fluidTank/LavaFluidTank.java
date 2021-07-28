package com.tech0006.techcraft.util.fluidTank;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class LavaFluidTank extends FluidTank {

    public LavaFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        if (stack.getFluid() == Fluids.LAVA)
            return true;
        else
        {
            return false;
        }
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (stack.getFluid() == Fluids.LAVA)
            return true;
        else
        {
            return false;
        }
    }
}
