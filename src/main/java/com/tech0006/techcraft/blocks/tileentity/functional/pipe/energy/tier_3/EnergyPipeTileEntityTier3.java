package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_3;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier3 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier3() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_3.get());
        energy.setMax(15);
        use_val = 3;
    }
}
