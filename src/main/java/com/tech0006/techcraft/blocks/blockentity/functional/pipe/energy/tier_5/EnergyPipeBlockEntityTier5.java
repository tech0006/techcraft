package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_5;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier5 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier5() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_5.get());
        energy.setMax(25);
        use_val = 5;
    }*/

    public EnergyPipeBlockEntityTier5(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_1.get(), pos, state);
        energy.setMax(25);
        use_val = 5;
    }
}
