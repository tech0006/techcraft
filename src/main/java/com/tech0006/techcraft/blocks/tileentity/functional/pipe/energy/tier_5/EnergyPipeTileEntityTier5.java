package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_5;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier5 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier5() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_5.get());
        energy.setMax(25);
        use_val = 5;
    }
}
