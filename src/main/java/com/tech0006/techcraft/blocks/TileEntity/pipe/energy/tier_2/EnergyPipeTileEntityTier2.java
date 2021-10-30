package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_2;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier2 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier2() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_2.get());
        energy.setMax(10);
        use_val = 2;
    }
}
