package com.tech0006.techcraft.blocks.functional.pipe.energy.tier_4;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.blocks.functional.pipe.energy.EnergyPipe;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;


public class EnergyPipeTier4 extends EnergyPipe {
    public EnergyPipeTier4(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new EnergyPipeTileEntity(TCTileEntityTypes.ENERGY_PIPE_TIER_4.get());
    }
}
