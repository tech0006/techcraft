package com.tech0006.techcraft.GUI.Container;

import com.tech0006.techcraft.blocks.TileEntity.AlloyPlantTileEntity;
import com.tech0006.techcraft.init.Blocks;
import com.tech0006.techcraft.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class AlloyPlantContainer extends Container {

    public AlloyPlantTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;


    // Server Constructor
    public AlloyPlantContainer(int windowID, PlayerInventory playerInv,
                               AlloyPlantTileEntity tile) {
        super(ModContainerTypes.ALLOY_PLANT.get(), windowID);

        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 50, 10));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 1, 50, 34));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 2, 50, 58));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 3, 105, 34) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInv, si, 8 + si * 18, 142));

    }

    // Client Constructor
    public AlloyPlantContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static AlloyPlantTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof AlloyPlantTileEntity) {
            return (AlloyPlantTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, Blocks.ALLOY_PLANT.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 2) {
                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
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

    public int getProgressScaled(int width) {
        int i = tileEntity.processTime;
        int j = tileEntity.processTimeTotal;
        return i != 0 && j != 0 ? width * (j - i) / j : 0;
    }
}
