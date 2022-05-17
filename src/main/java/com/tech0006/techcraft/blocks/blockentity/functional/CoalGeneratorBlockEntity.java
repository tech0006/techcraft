package com.tech0006.techcraft.blocks.blockentity.functional;

import com.tech0006.techcraft.blocks.blockentity.base.GeneratorTile;
import com.tech0006.techcraft.blocks.blockentity.update.UpdateCoalGenerator;
import com.tech0006.techcraft.blocks.functional.CoalGenerator;
import com.tech0006.techcraft.common.packet.PacketHandler;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class CoalGeneratorBlockEntity extends GeneratorTile {

    public TCItemHandler inventory;

    public int processTime, processTimeTotal;

    public CoalGeneratorBlockEntity(BlockEntityType tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state,10);
        inventory = new TCItemHandler(4);
        this.energyUse = -10;
    }

    public CoalGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(TCBlockEntityTypes.COAL_GENERATOR.get(),pos, state, 10);
        inventory = new TCItemHandler(4);
        this.energyUse = -10;
    }

    @Override
    public void load(CompoundTag compound) {

        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        this.processTime = compound.getInt("CurrBurnTime");
        this.processTimeTotal = compound.getInt("SumBurnTime");


        super.load(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {


        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList(), false);
        compound.putInt("CurrBurnTime", this.processTime);
        compound.putInt("SumBurnTime", this.processTimeTotal);

        return super.save(compound);
    }

    public void tick(BlockState state) {

        if (level != null && !level.isClientSide) {
            if (processTime <= 0 && AbstractFurnaceBlockEntity.isFuel(this.inventory.getStackInSlot(0)) && getEnergyStored() != getMaxEnergy()) {
                ItemStack cp = this.inventory.getStackInSlot(0).copy();
                cp.setCount(1);
                processTime = processTimeTotal = ForgeHooks.getBurnTime(cp, RecipeType.SMELTING);
                this.inventory.decrStackSize(0, 1);
                this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CoalGenerator.LIT, true));
            }
            if (processTime > 0 && getEnergyStored() != getMaxEnergy())
                processTime--;
            if (processTime <= 0 && !AbstractFurnaceBlockEntity.isFuel(this.inventory.getStackInSlot(0))) {
                processTime = processTimeTotal = 0;
                this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CoalGenerator.LIT, false));
            }
            this.energy.use(this.currentAmountEnergyProduced());
            sendEnergy();
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateCoalGenerator(getBlockPos(), getEnergyStored(), processTime, processTimeTotal));

        }
    }

    /**
     * level mustn't be null (NullPointerException)
     */
    private void sendEnergy() {
        AtomicInteger capacity = new AtomicInteger(getEnergyStored());

        for (int i = 0; (i < Direction.values().length) && (capacity.get() > 0); i++) {
            Direction facing = Direction.values()[i];
            assert level != null;
            BlockEntity tileEntity = level.getBlockEntity(worldPosition.relative(facing));
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).ifPresent(handler ->
                {
                    if (handler.canReceive()) {
                        int received = handler.receiveEnergy(10, false);
                        capacity.addAndGet(-received);
                        energy.use(received);
                    }
                });
            }
        }
    }

    public int currentAmountEnergyProduced() {
        if (processTime <= 0) {
            return 0;
        } else {
            return this.energyUse;
        }
    }

    public TCItemHandler getInventory() {
        return inventory;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this.inventory));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.load(nbt);
    }


}

