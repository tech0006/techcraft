package com.tech0006.techcraft.blocks;

import com.tech0006.techcraft.blocks.TileEntity.EnergyPipeTileEntity;
import com.tech0006.techcraft.blocks.base.PipeBlock;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EnergyPipe extends PipeBlock {

    public EnergyPipe(Properties properties) {
        super(properties);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, state, te, stack);
        worldIn.removeBlock(pos, false);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new EnergyPipeTileEntity(ModTileEntityTypes.ENERGY_PIPE.get());
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeBlockEntity(pos);
        }
    }

}
