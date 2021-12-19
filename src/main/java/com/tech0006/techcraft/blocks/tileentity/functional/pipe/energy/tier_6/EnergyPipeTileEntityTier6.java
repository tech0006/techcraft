package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_6;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier6 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier6() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_6.get());
        energy.setMax(30);
        use_val = 6;
    }
}
