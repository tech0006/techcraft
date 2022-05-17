package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_3;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier3 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier3() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_3.get());
        energy.setMax(15);
        use_val = 3;
    }*/

    public EnergyPipeBlockEntityTier3(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_3.get(), pos, state);
        energy.setMax(15);
        use_val = 3;
    }
}
