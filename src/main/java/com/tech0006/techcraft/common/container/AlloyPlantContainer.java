package com.tech0006.techcraft.common.container;

import com.tech0006.techcraft.blocks.tileentity.functional.AlloyPlantTileEntity;
import com.tech0006.techcraft.common.registration.TCBlocks;
import com.tech0006.techcraft.common.registration.TCContainerTypes;
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
    private final IWorldPosCallable canInteractWithCallable;


    // Server Constructor
    public AlloyPlantContainer(int windowID, PlayerInventory playerInv,
                               AlloyPlantTileEntity tile) {
        super(TCContainerTypes.ALLOY_PLANT.get(), windowID);

        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.create(Objects.requireNonNull(tile.getLevel()), tile.getBlockPos());

        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 50, 10));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 1, 50, 34));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 2, 50, 58));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 3, 105, 34) {
            @Override
            public boolean mayPlace(ItemStack stack) {
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
        final TileEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof AlloyPlantTileEntity) {
            return (AlloyPlantTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(canInteractWithCallable, playerIn, TCBlocks.ALLOY_PLANT.get());
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
        int i = tileEntity.processTime;
        int j = tileEntity.processTimeTotal;
        return i != 0 && j != 0 ? width * (j - i) / j : 0;
    }
}
