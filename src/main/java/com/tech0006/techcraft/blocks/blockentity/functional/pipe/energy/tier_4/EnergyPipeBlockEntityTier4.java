package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_4;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier4 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier4() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_4.get());
        energy.setMax(20);
        use_val = 4;
    }*/

    public EnergyPipeBlockEntityTier4(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_4.get(), pos, state);
        energy.setMax(20);
        use_val = 4;
    }
}
