package com.tech0006.techcraft.common.container;

import com.tech0006.techcraft.blocks.tileentity.functional.SafeTileEntity;
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

import java.util.Objects;

public class SafeContainer extends Container {
    public final SafeTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public SafeContainer(final int windowId, final PlayerInventory playerInventory,
                         final SafeTileEntity tileEntity) {
        super(TCContainerTypes.SAFE.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos());

        // Main Inventory
        int startX = 8;
        int startY = 18;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(tileEntity, (row * 9) + column, startX + (column * slotSizePlus2),
                        startY + (row * slotSizePlus2)));
            }
        }

        // Main Player Inventory
        int startPlayerInvY = startY * 5 + 12;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
                        startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hotbar
        int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 7;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
        }
    }

    private static SafeTileEntity getTileEntity(final PlayerInventory playerInventory,
                                                   final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof SafeTileEntity) {
            return (SafeTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public SafeContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(canInteractWithCallable, playerIn, TCBlocks.COPPER_SAFE.get()) ||
                stillValid(canInteractWithCallable, playerIn, TCBlocks.TIN_SAFE.get()) ||
                stillValid(canInteractWithCallable, playerIn, TCBlocks.BRONZE_SAFE.get()) ||
                stillValid(canInteractWithCallable, playerIn, TCBlocks.IRON_SAFE.get()) ||
                stillValid(canInteractWithCallable, playerIn, TCBlocks.GOLD_SAFE.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 36) {
                if (!this.moveItemStackTo(itemstack1, 36, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 36, false)) {
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
}