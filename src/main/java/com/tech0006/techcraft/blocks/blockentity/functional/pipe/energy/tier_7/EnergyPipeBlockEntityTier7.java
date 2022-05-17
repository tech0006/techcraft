package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_7;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier7 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier7() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_7.get());
        energy.setMax(35);
        use_val = 7;
    }*/

    public EnergyPipeBlockEntityTier7(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_7.get(), pos, state);
        energy.setMax(35);
        use_val = 7;
    }
}
