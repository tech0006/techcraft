package com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_3;

import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class EnergyPipeTileEntityTier3 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier3() {
        super(ModTileEntityTypes.ENERGY_PIPE_TIER_3.get());
        energy.setMax(15);
        use_val = 3;
    }
}
