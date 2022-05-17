package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_1;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPipeBlockEntityTier1 extends EnergyPipeBlockEntity {

    /*public EnergyPipeBlockEntityTier1() {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_1.get());
        energy.setMax(5);
        use_val = 1;
    }
*/
    public EnergyPipeBlockEntityTier1(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.ENERGY_PIPE_TIER_1.get(), pos, state);
        energy.setMax(5);
        use_val = 1;
    }
}
