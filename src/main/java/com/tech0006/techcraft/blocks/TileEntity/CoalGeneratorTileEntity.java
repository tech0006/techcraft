package com.tech0006.techcraft.blocks.TileEntity;

import com.tech0006.techcraft.GUI.Container.CoalGeneratorContainer;
import com.tech0006.techcraft.blocks.CoalGenerator;
import com.tech0006.techcraft.blocks.TileEntity.base.GeneratorTile;
import com.tech0006.techcraft.blocks.TileEntity.update.UpdateCoalGenerator;
import com.tech0006.techcraft.init.ModContainerTypes;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.util.handler.PacketHandler;
import com.tech0006.techcraft.util.handler.TCItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class CoalGeneratorTileEntity extends GeneratorTile implements INamedContainerProvider {

    public TCItemHandler inventory;

    public int processTime, processTimeTotal;

    public CoalGeneratorTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, 2);
        inventory = new TCItemHandler(4);
        this.energyUse = -10;
    }

    public CoalGeneratorTileEntity() {
        super(ModTileEntityTypes.COAL_GENERATOR.get(), 2);
        inventory = new TCItemHandler(4);
        this.energyUse = -10;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {

        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        this.processTime = compound.getInt("CurrBurnTime");
        this.processTimeTotal = compound.getInt("SumBurnTime");


        super.load(state, compound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundNBT save(CompoundNBT compound) {


        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList(), false);
        compound.putInt("CurrBurnTime", this.processTime);
        compound.putInt("SumBurnTime", this.processTimeTotal);

        return super.save(compound);
    }

    @Override
    public void tick() {

        if (!level.isClientSide) {
            if (processTime <= 0 && AbstractFurnaceTileEntity.isFuel(this.inventory.getStackInSlot(0)) && getEnergyStored() != getMaxEnergy()) {
                ItemStack cp = this.inventory.getStackInSlot(0).copy();
                cp.setCount(1);
                processTime = processTimeTotal = ForgeHooks.getBurnTime(cp);
                this.inventory.decrStackSize(0, 1);
                this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CoalGenerator.LIT, true));
            }
            if (processTime > 0 && getEnergyStored() != getMaxEnergy())
                processTime--;
            if (processTime <= 0 && !AbstractFurnaceTileEntity.isFuel(this.inventory.getStackInSlot(0))) {
                processTime = processTimeTotal = 0;
                this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CoalGenerator.LIT, false));
            }
            this.energy.use(this.currentAmountEnergyProduced());
            sendEnergy();
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateCoalGenerator(getBlockPos(), getEnergyStored(), processTime, processTimeTotal));

        }
    }

    private void sendEnergy() {
        AtomicInteger capacity = new AtomicInteger(getEnergyStored());

        for (int i = 0; (i < Direction.values().length) && (capacity.get() > 0); i++) {
            Direction facing = Direction.values()[i];
            TileEntity tileEntity = level.getBlockEntity(worldPosition.relative(facing));
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).ifPresent(handler ->
                {
                    if (handler.canReceive()) {
                        int received = handler.receiveEnergy(5, false);
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

    @Nullable
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CoalGeneratorContainer(ModContainerTypes.COAL_GENERATOR.get(), id, level, worldPosition, playerEntity, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this.inventory));
        }
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return new SUpdateTileEntityPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.load(this.getBlockState(), nbt);
    }


}

