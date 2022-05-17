package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_2;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier2 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier2() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_2.get());
        energy.setMax(10);
        use_val = 2;
    }*/

    public EnergyPipeBlockEntityTier2(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_1.get(), pos, state);
        energy.setMax(10);
        use_val = 2;
    }
}
