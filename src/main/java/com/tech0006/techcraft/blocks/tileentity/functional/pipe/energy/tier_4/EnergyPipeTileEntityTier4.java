package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_4;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier4 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier4() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_4.get());
        energy.setMax(20);
        use_val = 4;
    }
}
