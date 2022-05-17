package com.tech0006.techcraft.blocks.functional;

import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.blocks.blockentity.functional.ElectricFurnaceBlockEntity;
import com.tech0006.techcraft.common.container.ElectricFurnaceContainer;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.util.tools.inventory.Tooltip;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ElectricFurnace extends FacedBlock implements EntityBlock {

    private static final ResourceLocation WRENCH = new ResourceLocation("forge", "wrench");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public ElectricFurnace(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!levelIn.isClientSide) {
            if (player.isCrouching()) {
                if (player.getMainHandItem().getItem().getTags().contains(WRENCH)) {
                    dismantleBlock(levelIn, pos);
                    return InteractionResult.SUCCESS;
                }
            }

            BlockEntity tile = levelIn.getBlockEntity(pos);
            if (tile instanceof ElectricFurnaceBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.techcraft.electric_furnace");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new ElectricFurnaceContainer(windowId, levelIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tile.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void dismantleBlock(Level levelIn, BlockPos pos) {
        ItemStack itemStack = new ItemStack(this);

        ElectricFurnaceBlockEntity localTileEntity = (ElectricFurnaceBlockEntity) levelIn.getBlockEntity(pos);
        if (localTileEntity != null)
        {
            int internalEnergy = localTileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
            if (internalEnergy > 0) {
                CompoundTag energyValue = new CompoundTag();
                energyValue.putInt("value", internalEnergy);

                CompoundTag energy = new CompoundTag();
                energy.put("energy", energyValue);

                CompoundTag root = new CompoundTag();
                root.put("BlockEntityTag", energy);
                itemStack.setTag(root);
            }

        }

        levelIn.removeBlock(pos, false);

        ItemEntity entityItem = new ItemEntity(levelIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);

        entityItem.setDeltaMovement(0, entityItem.getMyRidingOffset(), 0);
        levelIn.addFreshEntity(entityItem);
    }

    @Override
    public boolean removedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return willHarvest || super.removedByPlayer(state, level, pos, player, false, fluid);
    }

    @Override
    public void playerDestroy(Level levelIn, Player player, BlockPos pos, BlockState state, BlockEntity te, ItemStack stack) {
        super.playerDestroy(levelIn, player, pos, state, te, stack);
        levelIn.removeBlock(pos, false);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElectricFurnaceBlockEntity(TCBlockEntityTypes.ELECTRIC_FURNACE.get(), pos, state);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag compoundnbt = stack.getTagElement("BlockEntityTag");
        int energy = 0;
        if (compoundnbt != null) {
            if (compoundnbt.contains("energy"))
                energy = compoundnbt.getInt("energy");
        }
        Tooltip.showInfoCtrlElectricFurnace(energy, tooltip);
    }

    @Override
    public void onRemove(BlockState state, Level levelIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = levelIn.getBlockEntity(pos);
        if (tile instanceof ElectricFurnaceBlockEntity && state.getBlock() != newState.getBlock()) {
            ElectricFurnaceBlockEntity furnace = (ElectricFurnaceBlockEntity) tile;

            ItemEntity itemEntity = new ItemEntity(levelIn, pos.getX(), pos.getY(), pos.getZ(), furnace.inventory.getStackInSlot(0));
            levelIn.addFreshEntity(itemEntity);

            itemEntity = new ItemEntity(levelIn, pos.getX(), pos.getY(), pos.getZ(), furnace.inventory.getStackInSlot(1));
            levelIn.addFreshEntity(itemEntity);

            itemEntity = new ItemEntity(levelIn, pos.getX(), pos.getY(), pos.getZ(), furnace.inventory.getStackInSlot(3));
            levelIn.addFreshEntity(itemEntity);
        }

        if (state instanceof EntityBlock && state.getBlock() != newState.getBlock()) {
            levelIn.removeBlockEntity(pos);
        }
    }

}
