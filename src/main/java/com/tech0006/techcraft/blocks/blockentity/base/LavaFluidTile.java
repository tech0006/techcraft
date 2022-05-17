package com.tech0006.techcraft.blocks.blockentity.base;

import com.tech0006.techcraft.util.tools.fluid.LavaFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class LavaFluidTile extends BlockEntity {

    protected LavaFluidTank tank = new LavaFluidTank(10000);

    protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public LavaFluidTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        tank.readFromNBT(compound);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag = super.save(tag);
        tank.writeToNBT(tag);
        return tag;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(capability, facing);
    }
}
