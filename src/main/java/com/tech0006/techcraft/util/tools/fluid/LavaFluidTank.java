package com.tech0006.techcraft.util.tools.fluid;

import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;


public class LavaFluidTank extends FluidTank {

    public LavaFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return stack.getFluid() == Fluids.LAVA;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.getFluid() == Fluids.LAVA;
    }
}
