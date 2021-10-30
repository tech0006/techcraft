package com.tech0006.techcraft.blocks.pipe.energy.tier_2;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.blocks.pipe.energy.EnergyPipe;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class EnergyPipeTier2 extends EnergyPipe {
    public EnergyPipeTier2(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new EnergyPipeTileEntity(ModTileEntityTypes.ENERGY_PIPE_TIER_2.get());
    }
}
