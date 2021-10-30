package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_1;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier1 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier1() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_1.get());
        energy.setMax(5);
        use_val = 1;
    }
}
