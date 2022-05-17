package com.tech0006.techcraft.common.container;

import com.tech0006.techcraft.blocks.blockentity.functional.WireShaperBlockEntity;
import com.tech0006.techcraft.common.registration.TCBlocks;
import com.tech0006.techcraft.common.registration.TCContainerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class WireShaperContainer extends AbstractContainerMenu {

    public WireShaperBlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;

    public WireShaperContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(TCContainerTypes.WIRE_SHAPER.get(), windowId);
        blockEntity = (WireShaperBlockEntity) world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 56, 34));
                addSlot(new SlotItemHandler(h, 1, 110, 34) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
            });
        }
        layoutPlayerInventorySlots(10, 70);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, TCBlocks.WIRE_SHAPER.get());
    }

    @Override
    public ItemStack quickMoveStack(final Player player, final int index) {
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

            final int containerSlots = this.slots.size() - player.containerMenu.slots.size();
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
                        player.level.destroyBlock(this.blockEntity.getBlockPos(), false);

                    }
                }
            }
        }
        return returnStack;
    }


    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        super.clicked(slotId, dragType, clickTypeIn, player);
        int count = player.containerMenu.getCarried().getCount();
        if (slotId == 1) {
            if (this.slots.get(0).getItem().getCount() == count) {
                this.slots.get(0).set(ItemStack.EMPTY);
            }
            if (this.slots.get(0).getItem() != ItemStack.EMPTY) {
                this.slots.get(0).getItem().setCount(this.slots.get(0).getItem().getCount() - count);
            }

            int a = (int) (Math.random() * 100);
            if (a == 1) {
                player.level.destroyBlock(this.blockEntity.getBlockPos(), false);

            }
        }
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
