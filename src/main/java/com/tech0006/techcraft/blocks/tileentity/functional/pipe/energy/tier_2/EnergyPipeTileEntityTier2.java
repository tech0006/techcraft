package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_2;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier2 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier2() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_2.get());
        energy.setMax(10);
        use_val = 2;
    }
}
