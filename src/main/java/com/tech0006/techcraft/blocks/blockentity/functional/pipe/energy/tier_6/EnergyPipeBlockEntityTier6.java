package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_6;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier6 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier6() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_6.get());
        energy.setMax(30);
        use_val = 6;
    }*/

    public EnergyPipeBlockEntityTier6(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_6.get(), pos, state);
        energy.setMax(30);
        use_val = 6;
    }
}
