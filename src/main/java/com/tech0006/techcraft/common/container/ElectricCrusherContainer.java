package com.tech0006.techcraft.common.container;

import com.tech0006.techcraft.blocks.tileentity.functional.ElectricCrusherTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ElectricCrusherContainer extends Container {

    public final ElectricCrusherTileEntity tile;
    private final PlayerEntity player;

    public ElectricCrusherContainer(ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerEntity player) {
        super(type, windowId);
        this.tile = (ElectricCrusherTileEntity) world.getBlockEntity(pos);
        this.player = player;

        assert tile != null;
        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 50, 30));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 105, 30) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(player.inventory, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (si = 0; si < 9; ++si)
            this.addSlot(new Slot(player.inventory, si, 8 + si * 18, 142));
    }

    public ElectricCrusherContainer(ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerEntity player, ElectricCrusherTileEntity tileentity) {
        super(type, windowId);
        this.tile = (ElectricCrusherTileEntity) Objects.requireNonNull(Objects.requireNonNull(world.getServer()).getLevel(World.OVERWORLD)).getBlockEntity(pos);
        this.player = player;

        assert tile != null;
        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 50, 30));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 105, 30) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(player.inventory, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (si = 0; si < 9; ++si)
            this.addSlot(new Slot(player.inventory, si, 8 + si * 18, 142));
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(IWorldPosCallable.create(Objects.requireNonNull(tile.getLevel()), tile.getBlockPos()), player, tile.getBlockState().getBlock());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 2) {
                if (!this.moveItemStackTo(itemstack1, 2, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    public int getProgressScaled(int width) {
        int i = tile.processTime;
        int j = tile.processTimeTotal;
        return i != 0 && j != 0 ? width * (j - i) / j : 0;
    }
}
