package com.tech0006.techcraft.GUI.Container;

import com.tech0006.techcraft.blocks.TileEntity.WireShaperTileEntity;
import com.tech0006.techcraft.blocks.WireShaper;
import com.tech0006.techcraft.init.Blocks;
import com.tech0006.techcraft.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class WireShaperContainer extends Container {

    public WireShaperTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;


    // Server Constructor
    public WireShaperContainer(int windowID, PlayerInventory playerInv,
                               WireShaperTileEntity tile) {
        super(ModContainerTypes.WIRE_SHAPER.get(), windowID);

        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 56, 34));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 1, 110, 34) {
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
    public WireShaperContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static WireShaperTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof WireShaperTileEntity) {
            return (WireShaperTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, Blocks.WIRE_SHAPER.get());
    }


    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            int mycount = this.inventorySlots.get(0).getStack().getCount();
            if (index == 1)
            {
                slotStack.setCount(mycount);
            }

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    if (index == 1) {
                        this.inventorySlots.get(0).putStack(ItemStack.EMPTY);
                    }

                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                if (index == 1) {
                    this.inventorySlots.get(0).putStack(ItemStack.EMPTY);
                }
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                if (index == 1) {
                    this.inventorySlots.get(0).putStack(ItemStack.EMPTY);
                }
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                if (index == 1) {
                    this.inventorySlots.get(0).putStack(ItemStack.EMPTY);
                }
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);

            if (index == 1) {
                for (int i = 0; i < mycount; i++) {
                    int a = (int) (Math.random() * 100);
                    if (a == 1) {
                        player.world.destroyBlock(this.tileEntity.getPos(), false);

                    }
                }
            }
        }
        return returnStack;
    }


    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        int count = stack.getCount();
        if (slotId == 1) {
            if (this.inventorySlots.get(0).getStack().getCount() == count) {
                this.inventorySlots.get(0).putStack(ItemStack.EMPTY);
            }
            if (this.inventorySlots.get(0).getStack() != ItemStack.EMPTY) {
                this.inventorySlots.get(0).getStack().setCount(this.inventorySlots.get(0).getStack().getCount() - count);
            }

            int a = (int) (Math.random() * 100);
            if (a == 1) {
                player.world.destroyBlock(this.tileEntity.getPos(), false);

            }
        }
        return stack;
    }
}
