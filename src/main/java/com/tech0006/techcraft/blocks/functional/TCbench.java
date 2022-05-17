package com.tech0006.techcraft.blocks.functional;

import com.tech0006.techcraft.blocks.blockentity.functional.TCbenchBlockEntity;
import com.tech0006.techcraft.common.container.TCbenchContainer;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;


public class TCbench extends Block implements EntityBlock {

    public TCbench(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TCbenchBlockEntity(TCBlockEntityTypes.TC_BENCH.get(), pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player,
                                 InteractionHand handIn, BlockHitResult hit) {
        if (!levelIn.isClientSide) {
            BlockEntity tile = levelIn.getBlockEntity(pos);
            if (tile instanceof TCbenchBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.techcraft.tc_bench");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new TCbenchContainer(windowId, levelIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tile.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level levelIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = levelIn.getBlockEntity(pos);
        if (tile instanceof TCbenchBlockEntity && state.getBlock() != newState.getBlock()) {
            TCbenchBlockEntity furnace = (TCbenchBlockEntity) tile;
            ((TCItemHandler) furnace.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(levelIn, pos.getX(), pos.getY(), pos.getZ(), item);
                levelIn.addFreshEntity(itemEntity);
            });
        }

        if (state instanceof EntityBlock && state.getBlock() != newState.getBlock()) {
            levelIn.removeBlockEntity(pos);
        }
    }
}
