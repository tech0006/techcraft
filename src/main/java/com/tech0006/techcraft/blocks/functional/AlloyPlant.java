package com.tech0006.techcraft.blocks.functional;

import com.tech0006.techcraft.blocks.tileentity.functional.AlloyPlantTileEntity;
import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;


public class AlloyPlant extends FacedBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public AlloyPlant(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TCTileEntityTypes.ALLOY_PLANT.get().create();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasCustomHoverName()) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof AlloyPlantTileEntity) {
                ((AlloyPlantTileEntity) tile).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isClientSide) {
            if (FluidUtil.interactWithFluidHandler(player, handIn, worldIn, pos, Direction.NORTH)) {
                worldIn.sendBlockUpdated(pos, state, state, 3);
                return ActionResultType.SUCCESS;
            }
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof AlloyPlantTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof AlloyPlantTileEntity && state.getBlock() != newState.getBlock()) {
            AlloyPlantTileEntity furnace = (AlloyPlantTileEntity) tile;
            furnace.getInventory().setStackInSlot(4, ItemStack.EMPTY);
            ((TCItemHandler) furnace.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addFreshEntity(itemEntity);
            });
        }

        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeBlockEntity(pos);
        }
    }

}
