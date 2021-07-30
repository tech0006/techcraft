package com.tech0006.techcraft.blocks.TileEntity.base;

import com.tech0006.techcraft.util.fluidTank.LavaFluidTank;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LavaFluidTile extends TileEntity implements ITickableTileEntity {

    protected LavaFluidTank tank = new LavaFluidTank(10000);

    protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public LavaFluidTile(@Nonnull TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag = super.write(tag);
        tank.writeToNBT(tag);
        return tag;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void tick() {

    }
}
