package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_6;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier6 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier6() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_6.get());
        energy.setMax(30);
        use_val = 6;
    }
}
