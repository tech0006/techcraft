package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_7;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier7 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier7() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_7.get());
        energy.setMax(35);
        use_val = 7;
    }
}
