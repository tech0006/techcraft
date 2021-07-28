package com.tech0006.techcraft.blocks.TileEntity.base;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidTile extends TileEntity implements ITickableTileEntity {

    protected FluidTank tank = new FluidTank(1000);

    protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public FluidTile(@Nonnull TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    @Override
    public void read(CompoundNBT tag)
    {
        super.read(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        tag = super.write(tag);
        tank.writeToNBT(tag);
        return tag;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void tick() {

    }
}
