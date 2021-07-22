package com.tech0006.techcraft.GUI.Container;

import com.tech0006.techcraft.blocks.TileEntity.CoalGeneratorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.items.SlotItemHandler;

public class CoalGeneratorContainer extends Container {

    public final CoalGeneratorTileEntity tile;
    private final PlayerEntity player;

    public CoalGeneratorContainer(ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerEntity player) {
        super(type, windowId);
        this.tile = (CoalGeneratorTileEntity) world.getTileEntity(pos);
        this.player = player;

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 58, 12));
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(player.inventory, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (si = 0; si < 9; ++si)
            this.addSlot(new Slot(player.inventory, si, 8 + si * 18, 142));
    }

    public CoalGeneratorContainer(ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerEntity player, CoalGeneratorTileEntity tileentity) {
        super(type, windowId);
        this.tile = (CoalGeneratorTileEntity) world.getServer().getWorld(DimensionType.OVERWORLD).getTileEntity(pos);
        this.player = player;

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 58, 12));
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(player.inventory, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (si = 0; si < 9; ++si)
            this.addSlot(new Slot(player.inventory, si, 8 + si * 18, 142));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), player, tile.getBlockState().getBlock());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 1) {
                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    public boolean isBurning() {
        return this.tile.processTime > 0;
    }

    public int getBurnTimeScaled() {
        int i = this.tile.processTime;
        int j = this.tile.processTimeTotal;
        return i != 0 && j != 0 ? i * 13 / j : 0;
    }

}