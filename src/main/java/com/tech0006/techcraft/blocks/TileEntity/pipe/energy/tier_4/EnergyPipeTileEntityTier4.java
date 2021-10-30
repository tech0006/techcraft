package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_4;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier4 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier4() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_4.get());
        energy.setMax(20);
        use_val = 4;
    }
}
