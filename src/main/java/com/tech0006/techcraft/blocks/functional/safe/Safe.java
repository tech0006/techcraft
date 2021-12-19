package com.tech0006.techcraft.blocks.functional.safe;

import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.blocks.tileentity.functional.SafeTileEntity;
import com.tech0006.techcraft.common.registration.TCTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;


public abstract class Safe extends FacedBlock {

    public Safe(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TCTileEntityTypes.SAFE.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        if (!worldIn.isClientSide) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof SafeTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (SafeTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof SafeTileEntity) {
                InventoryHelper.dropContents(worldIn, pos, ((SafeTileEntity) te).getItems());
            }
        }
    }
}
