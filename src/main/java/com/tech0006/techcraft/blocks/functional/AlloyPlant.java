package com.tech0006.techcraft.blocks.functional;

import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.blocks.blockentity.functional.AlloyPlantBlockEntity;
import com.tech0006.techcraft.common.container.AlloyPlantContainer;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;


public class AlloyPlant extends FacedBlock implements EntityBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public AlloyPlant(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlloyPlantBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }


    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {

        if (!levelIn.isClientSide) {
            if (FluidUtil.interactWithFluidHandler(player, handIn, levelIn, pos, Direction.NORTH)) {
                levelIn.sendBlockUpdated(pos, state, state, 3);
                return InteractionResult.SUCCESS;
            }
            BlockEntity tile = levelIn.getBlockEntity(pos);
            if (tile instanceof AlloyPlantBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.techcraft.alloy_plant");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new AlloyPlantContainer(windowId, levelIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tile.getBlockPos());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level levelIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = levelIn.getBlockEntity(pos);
        if (tile instanceof AlloyPlantBlockEntity && state.getBlock() != newState.getBlock()) {
            AlloyPlantBlockEntity furnace = (AlloyPlantBlockEntity) tile;
            furnace.getInventory().setStackInSlot(4, ItemStack.EMPTY);
            ((TCItemHandler) furnace.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(levelIn, pos.getX(), pos.getY(), pos.getZ(), item);
                levelIn.addFreshEntity(itemEntity);
            });
        }

        if (state instanceof EntityBlock && state.getBlock() != newState.getBlock()) {
            levelIn.removeBlockEntity(pos);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        } else {
            return (level1, pos, state1, tile) -> {
                if (tile instanceof AlloyPlantBlockEntity generator) {
                    generator.tick(state1);
                }
            };
        }
    }

}
