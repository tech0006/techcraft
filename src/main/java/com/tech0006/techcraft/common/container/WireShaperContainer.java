package com.tech0006.techcraft.common.container;

import com.tech0006.techcraft.blocks.tileentity.functional.WireShaperTileEntity;
import com.tech0006.techcraft.common.registration.TCBlocks;
import com.tech0006.techcraft.common.registration.TCContainerTypes;
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

import java.util.Objects;

public class WireShaperContainer extends Container {

    public WireShaperTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;


    // Server Constructor
    public WireShaperContainer(int windowID, PlayerInventory playerInv,
                               WireShaperTileEntity tile) {
        super(TCContainerTypes.WIRE_SHAPER.get(), windowID);

        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.create(Objects.requireNonNull(tile.getLevel()), tile.getBlockPos());

        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 56, 34));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 1, 110, 34) {
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
    public WireShaperContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static WireShaperTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof WireShaperTileEntity) {
            return (WireShaperTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(canInteractWithCallable, playerIn, TCBlocks.WIRE_SHAPER.get());
    }

    @Override
    public ItemStack quickMoveStack(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            int mycount = this.slots.get(0).getItem().getCount();
            if (index == 1)
            {
                slotStack.setCount(mycount);
            }

            final int containerSlots = this.slots.size() - player.inventory.items.size();
            if (index < containerSlots) {
                if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
                    if (index == 1) {
                        this.slots.get(0).set(ItemStack.EMPTY);
                    }

                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
                if (index == 1) {
                    this.slots.get(0).set(ItemStack.EMPTY);
                }
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                if (index == 1) {
                    this.slots.get(0).set(ItemStack.EMPTY);
                }
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                if (index == 1) {
                    this.slots.get(0).set(ItemStack.EMPTY);
                }
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);

            if (index == 1) {
                for (int i = 0; i < mycount; i++) {
                    int a = (int) (Math.random() * 100);
                    if (a == 1) {
                        player.level.destroyBlock(this.tileEntity.getBlockPos(), false);

                    }
                }
            }
        }
        return returnStack;
    }


    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.clicked(slotId, dragType, clickTypeIn, player);
        int count = stack.getCount();
        if (slotId == 1) {
            if (this.slots.get(0).getItem().getCount() == count) {
                this.slots.get(0).set(ItemStack.EMPTY);
            }
            if (this.slots.get(0).getItem() != ItemStack.EMPTY) {
                this.slots.get(0).getItem().setCount(this.slots.get(0).getItem().getCount() - count);
            }

            int a = (int) (Math.random() * 100);
            if (a == 1) {
                player.level.destroyBlock(this.tileEntity.getBlockPos(), false);

            }
        }
        return stack;
    }
}
