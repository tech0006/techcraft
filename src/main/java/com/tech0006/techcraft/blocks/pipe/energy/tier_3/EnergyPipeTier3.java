package com.tech0006.techcraft.blocks.pipe.energy.tier_3;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.blocks.pipe.energy.EnergyPipe;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class EnergyPipeTier3 extends EnergyPipe {
    public EnergyPipeTier3(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new EnergyPipeTileEntity(ModTileEntityTypes.ENERGY_PIPE_TIER_3.get());
    }
}