package com.tech0006.techcraft.blocks.functional.pipe;


import com.tech0006.techcraft.blocks.base.BlockTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;



public class PipeBlock extends BlockTransmitter {

    public PipeBlock(Properties builder) {
        super(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        boolean n = false, s = false, w = false, e = false, d = false, u = false;

        TileEntity tile;

        BlockPos n_pos = pos.relative(Direction.UP);
        tile = world.getBlockEntity(n_pos);
        if (tile != null) {
            u = true;
        }

        n_pos = pos.relative(Direction.DOWN);
        tile = world.getBlockEntity(n_pos);
        if (tile != null) {
            d = true;
        }

        n_pos = pos.relative(Direction.NORTH);
        tile = world.getBlockEntity(n_pos);
        if (tile != null) {
            n = true;
        }

        n_pos = pos.relative(Direction.SOUTH);
        tile = world.getBlockEntity(n_pos);
        if (tile != null) {
            s = true;
        }

        n_pos = pos.relative(Direction.EAST);
        tile = world.getBlockEntity(n_pos);
        if (tile != null) {
            e = true;
        }

        n_pos = pos.relative(Direction.WEST);
        tile = world.getBlockEntity(n_pos);
        if (tile != null) {
            w = true;
        }

        tile = world.getBlockEntity(pos);
        if (tile != null && tile.getLevel() != null) {
            tile.getLevel().setBlockAndUpdate(pos, this.stateDefinition.any().setValue(UP, u).setValue(DOWN, d)
                    .setValue(NORTH, n).setValue(SOUTH, s).setValue(EAST, e).setValue(WEST, w));
        }


        NonNullList<VoxelShape> list = NonNullList.create();

        if (u) {
            list.add(Block.box(7, 9, 7, 9, 16, 9));
        }
        if (d) {
            list.add(Block.box(7, 0, 7, 9, 7, 9));
        }
        if (n) {
            list.add(Block.box(7, 7, 0, 9, 9, 7));
        }
        if (s) {
            list.add(Block.box(7, 7, 9, 9, 9, 16));
        }
        if (w) {
            list.add(Block.box(0, 7, 7, 7, 9, 9));
        }
        if (e) {
            list.add(Block.box(9, 7, 7, 16, 9, 9));
        }

        list.add(Block.box(7, 7, 7, 9, 9, 9));

        return list.stream().reduce((v1, v2) -> {
            return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
        }).get();
    }
}
