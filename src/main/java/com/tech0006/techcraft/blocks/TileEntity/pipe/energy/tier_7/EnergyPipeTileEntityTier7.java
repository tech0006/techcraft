package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_7;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier7 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier7() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_7.get());
        energy.setMax(35);
        use_val = 7;
    }
}
