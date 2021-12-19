package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.tier_1;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;

public class EnergyPipeTileEntityTier1 extends EnergyPipeTileEntity {

    public EnergyPipeTileEntityTier1() {
        super(TCTileEntityTypes.ENERGY_PIPE_TIER_1.get());
        energy.setMax(5);
        use_val = 1;
    }
}
