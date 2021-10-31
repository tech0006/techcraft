package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_5;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier5 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier5() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_5.get());
        energy.setMax(25);
        use_val = 5;
    }
}
