package com.tech0006.techcraft.blocks.functional.safe;

import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.blocks.blockentity.functional.AlloyPlantBlockEntity;
import com.tech0006.techcraft.blocks.blockentity.functional.SafeBlockEntity;
import com.tech0006.techcraft.common.container.AlloyPlantContainer;
import com.tech0006.techcraft.common.container.SafeContainer;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;


public abstract class Safe extends FacedBlock implements EntityBlock {

    public Safe(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SafeBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        if (!levelIn.isClientSide) {
            BlockEntity tile = levelIn.getBlockEntity(pos);
            if (tile instanceof SafeBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.techcraft.safe");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new SafeContainer(windowId, levelIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tile.getBlockPos());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onRemove(BlockState state, Level levelIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity te = levelIn.getBlockEntity(pos);
            if (te instanceof SafeBlockEntity && state.getBlock() != newState.getBlock()) {
                (((SafeBlockEntity) te).getInventory()).toNonNullList().forEach(item -> {
                    ItemEntity itemEntity = new ItemEntity(levelIn, pos.getX(), pos.getY(), pos.getZ(), item);
                    levelIn.addFreshEntity(itemEntity);
                });
            }
        }
        if (state instanceof EntityBlock && state.getBlock() != newState.getBlock()) {
            levelIn.removeBlockEntity(pos);
        }
    }
}
