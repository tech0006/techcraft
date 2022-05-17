package com.tech0006.techcraft.blocks.functional.pipe.energy.tier_1;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_1.EnergyPipeBlockEntityTier1;
import com.tech0006.techcraft.blocks.functional.pipe.energy.EnergyPipe;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;


public class EnergyPipeTier1 extends EnergyPipe implements EntityBlock {
    public EnergyPipeTier1(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyPipeBlockEntity(TCBlockEntityTypes.ENERGY_PIPE_TIER_1.get(), pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        } else {
            return (level1, pos, state1, tile) -> {
                if (tile instanceof EnergyPipeBlockEntityTier1 generator) {
                    generator.tick(state1);
                }
            };
        }
    }
}
