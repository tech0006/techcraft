package com.tech0006.techcraft.GUI.Container;

import com.tech0006.techcraft.blocks.TileEntity.TCbenchTileEntity;
import com.tech0006.techcraft.init.Blocks;
import com.tech0006.techcraft.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TCbenchContainer extends Container {

    public TCbenchTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;

    // Server Constructor
    public TCbenchContainer(final int windowID, final PlayerInventory playerInv,
                            final TCbenchTileEntity tile) {
        super(ModContainerTypes.TC_BENCH.get(), windowID);

        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                this.addSlot(new SlotItemHandler(tileEntity.getInventory(), i * 6 + j, 17 + 18 * j, 17 + 18 * i));
            }
        }
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 36, 134, 62) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 53 + 84 + si * 18));
        for (si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInv, si, 8 + si * 18, 53 + 142));

    }

    // Client Constructor
    public TCbenchContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static TCbenchTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof TCbenchTileEntity) {
            return (TCbenchTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, Blocks.TC_BENCH.get());
    }


    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();
            int min = 64;
            if (index == 36) {

                for (int i = 0; i < 36; i++) {
                    if (this.inventorySlots.get(i).getStack() != ItemStack.EMPTY) {
                        if (this.inventorySlots.get(i).getStack().getCount() < min) {
                            min = this.inventorySlots.get(i).getStack().getCount();
                        }
                    }
                }
                slotStack.setCount(min);
            }

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    if (index == 36) {
                        for (int i = 0; i < 36; i++) {
                            if (this.inventorySlots.get(i).getStack() != ItemStack.EMPTY) {
                                this.inventorySlots.get(i).getStack().setCount(this.inventorySlots.get(i).getStack().getCount() - min);
                                if (this.inventorySlots.get(i).getStack().getCount() <= 0) {
                                    this.inventorySlots.get(i).putStack(ItemStack.EMPTY);
                                }
                            }
                        }
                    }
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                if (index == 36) {
                    for (int i = 0; i < 36; i++) {
                        if (this.inventorySlots.get(i).getStack() != ItemStack.EMPTY) {
                            this.inventorySlots.get(i).getStack().setCount(this.inventorySlots.get(i).getStack().getCount() - min);
                            if (this.inventorySlots.get(i).getStack().getCount() <= 0) {
                                this.inventorySlots.get(i).putStack(ItemStack.EMPTY);
                            }
                        }
                    }
                }
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                if (index == 36) {
                    for (int i = 0; i < 36; i++) {
                        if (this.inventorySlots.get(i).getStack() != ItemStack.EMPTY) {
                            this.inventorySlots.get(i).getStack().setCount(this.inventorySlots.get(i).getStack().getCount() - min);
                            if (this.inventorySlots.get(i).getStack().getCount() <= 0) {
                                this.inventorySlots.get(i).putStack(ItemStack.EMPTY);
                            }
                        }
                    }
                }
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                if (index == 36) {
                    for (int i = 0; i < 36; i++) {
                        if (this.inventorySlots.get(i).getStack() != ItemStack.EMPTY) {
                            this.inventorySlots.get(i).getStack().setCount(this.inventorySlots.get(i).getStack().getCount() - min);
                            if (this.inventorySlots.get(i).getStack().getCount() <= 0) {
                                this.inventorySlots.get(i).putStack(ItemStack.EMPTY);
                            }
                        }
                    }
                }
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }

        int count = returnStack.getCount();
        if (index == 36) {
            for (int i = 0; i < 36; i++) {
                if (this.inventorySlots.get(i).getStack().getItem() != Items.AIR) {
                    if (this.inventorySlots.get(i).getStack().getCount() == count) {
                        this.inventorySlots.get(i).putStack(ItemStack.EMPTY);
                    }

                }
                if (this.inventorySlots.get(i).getStack().getItem() != Items.AIR) {
                    this.inventorySlots.get(i).getStack().setCount(this.inventorySlots.get(i).getStack().getCount() - count);
                }
            }
        }

        return returnStack;
    }


    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        int count = stack.getCount();
        if (slotId == 36) {
            for (int i = 0; i < 36; i++) {
                if (this.inventorySlots.get(i).getStack().getCount() == count) {
                    this.inventorySlots.get(i).putStack(ItemStack.EMPTY);
                }
                if (this.inventorySlots.get(i).getStack() != ItemStack.EMPTY) {
                    this.inventorySlots.get(i).getStack().setCount(this.inventorySlots.get(i).getStack().getCount() - count);
                }
            }
        }
        return stack;
    }

}
