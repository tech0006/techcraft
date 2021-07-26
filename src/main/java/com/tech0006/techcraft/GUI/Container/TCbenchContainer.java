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
        this.canInteractWithCallable = IWorldPosCallable.create(tile.getLevel(), tile.getBlockPos());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                this.addSlot(new SlotItemHandler(tileEntity.getInventory(), i * 6 + j, 17 + 18 * j, 17 + 18 * i));
            }
        }
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 36, 134, 62) {
            @Override
            public boolean mayPlace(ItemStack stack) {
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
        final TileEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof TCbenchTileEntity) {
            return (TCbenchTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(canInteractWithCallable, playerIn, Blocks.TC_BENCH.get());
    }


    @Nonnull
    @Override
    public ItemStack quickMoveStack(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();
            int min = 64;
            if (index == 36) {

                for (int i = 0; i < 36; i++) {
                    if (this.slots.get(i).getItem() != ItemStack.EMPTY) {
                        if (this.slots.get(i).getItem().getCount() < min) {
                            min = this.slots.get(i).getItem().getCount();
                        }
                    }
                }
                slotStack.setCount(min);
            }

            final int containerSlots = this.slots.size() - player.inventory.items.size();
            if (index < containerSlots) {
                if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
                    if (index == 36) {
                        for (int i = 0; i < 36; i++) {
                            if (this.slots.get(i).getItem() != ItemStack.EMPTY) {
                                this.slots.get(i).getItem().setCount(this.slots.get(i).getItem().getCount() - min);
                                if (this.slots.get(i).getItem().getCount() <= 0) {
                                    this.slots.get(i).set(ItemStack.EMPTY);
                                }
                            }
                        }
                    }
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
                if (index == 36) {
                    for (int i = 0; i < 36; i++) {
                        if (this.slots.get(i).getItem() != ItemStack.EMPTY) {
                            this.slots.get(i).getItem().setCount(this.slots.get(i).getItem().getCount() - min);
                            if (this.slots.get(i).getItem().getCount() <= 0) {
                                this.slots.get(i).set(ItemStack.EMPTY);
                            }
                        }
                    }
                }
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                if (index == 36) {
                    for (int i = 0; i < 36; i++) {
                        if (this.slots.get(i).getItem() != ItemStack.EMPTY) {
                            this.slots.get(i).getItem().setCount(this.slots.get(i).getItem().getCount() - min);
                            if (this.slots.get(i).getItem().getCount() <= 0) {
                                this.slots.get(i).set(ItemStack.EMPTY);
                            }
                        }
                    }
                }
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                if (index == 36) {
                    for (int i = 0; i < 36; i++) {
                        if (this.slots.get(i).getItem() != ItemStack.EMPTY) {
                            this.slots.get(i).getItem().setCount(this.slots.get(i).getItem().getCount() - min);
                            if (this.slots.get(i).getItem().getCount() <= 0) {
                                this.slots.get(i).set(ItemStack.EMPTY);
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
                if (this.slots.get(i).getItem().getItem() != Items.AIR) {
                    if (this.slots.get(i).getItem().getCount() == count) {
                        this.slots.get(i).set(ItemStack.EMPTY);
                    }

                }
                if (this.slots.get(i).getItem().getItem() != Items.AIR) {
                    this.slots.get(i).getItem().setCount(this.slots.get(i).getItem().getCount() - count);
                }
            }
        }

        return returnStack;
    }


    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.clicked(slotId, dragType, clickTypeIn, player);
        int count = stack.getCount();
        if (slotId == 36) {
            for (int i = 0; i < 36; i++) {
                if (this.slots.get(i).getItem().getCount() == count) {
                    this.slots.get(i).set(ItemStack.EMPTY);
                }
                if (this.slots.get(i).getItem() != ItemStack.EMPTY) {
                    this.slots.get(i).getItem().setCount(this.slots.get(i).getItem().getCount() - count);
                }
            }
        }
        return stack;
    }

}
