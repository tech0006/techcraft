package com.tech0006.techcraft.blocks.TileEntity;

import com.tech0006.techcraft.GUI.Container.CoalGeneratorContainer;
import com.tech0006.techcraft.blocks.CoalGenerator;
import com.tech0006.techcraft.blocks.TileEntity.update.UpdateCoalGenerator;
import com.tech0006.techcraft.init.ModContainerTypes;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.util.TCEnergyStorage;
import com.tech0006.techcraft.util.handler.CoalGeneratorItemHandler;
import com.tech0006.techcraft.util.handler.PacketHandler;
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
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class CoalGeneratorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);
    public int energyGeneration, maxEnergyOutput;
    public int maxEnergy;
    public int energyClient, energyProductionClient;

    public int currBurnTime, sumBurnTime;

    private CoalGeneratorItemHandler inventory;

    public CoalGeneratorTileEntity() {
        super(ModTileEntityTypes.COAL_GENERATOR.get());
        energyGeneration = 5;
        maxEnergyOutput = energyGeneration * 2;
        maxEnergy = 10000;
        energyClient = energyProductionClient = -1;
        inventory = new CoalGeneratorItemHandler(1);

        currBurnTime = sumBurnTime = 0;
    }

    public CoalGeneratorTileEntity(TileEntityType<?> coalGeneratorTileEntity) {
        super(coalGeneratorTileEntity);
        energyGeneration = 5;
        maxEnergyOutput = energyGeneration * 2;
        maxEnergy = energyGeneration * 1000;
        energyClient = energyProductionClient = -1;
        inventory = new CoalGeneratorItemHandler(1);

        currBurnTime = sumBurnTime = 0;
    }

    public CoalGeneratorItemHandler getInventory() {
        return inventory;
    }

    private IEnergyStorage createEnergy() {
        return new TCEnergyStorage(maxEnergyOutput, maxEnergy);
    }

    @Override
    public void tick() {

        if (!world.isRemote) {
            if (currBurnTime <= 0 && AbstractFurnaceTileEntity.isFuel(this.inventory.getStackInSlot(0)) && getEnergy() != getMaxEnergy()) {
                ItemStack cp = this.inventory.getStackInSlot(0).copy();
                cp.setCount(1);
                currBurnTime = sumBurnTime = ForgeHooks.getBurnTime(cp);
                this.inventory.decrStackSize(0, 1);
                this.world.setBlockState(this.getPos(), this.getBlockState().with(CoalGenerator.LIT, true));
            }
            if (currBurnTime > 0 && getEnergy() != getMaxEnergy())
                currBurnTime--;
            if (currBurnTime <= 0 && !AbstractFurnaceTileEntity.isFuel(this.inventory.getStackInSlot(0))) {
                currBurnTime = sumBurnTime = 0;
                this.world.setBlockState(this.getPos(), this.getBlockState().with(CoalGenerator.LIT, false));
            }
            energy.ifPresent(e -> ((TCEnergyStorage) e).generatePower(currentAmountEnergyProduced()));
            sendEnergy();
            if (energyClient != getEnergy() || energyProductionClient != currentAmountEnergyProduced()) {
                int energyProduced = (getEnergy() != getMaxEnergy()) ? currentAmountEnergyProduced() : 0;
                PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateCoalGenerator(getPos(), getEnergy(), energyProduced, currBurnTime, sumBurnTime));
            }
        }
    }

    private int getMaxEnergy() {
        return getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    private int getEnergy() {
        return getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int currentAmountEnergyProduced() {
        if (currBurnTime <= 0) {
            return 0;
        } else {
            return energyGeneration;
        }
    }

    private void sendEnergy() {
        energy.ifPresent(energy ->
        {
            AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());

            for (int i = 0; (i < Direction.values().length) && (capacity.get() > 0); i++) {
                Direction facing = Direction.values()[i];
                if (facing != Direction.UP) {
                    TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                    if (tileEntity != null) {
                        tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).ifPresent(handler ->
                        {
                            if (handler.canReceive()) {
                                int received = handler.receiveEnergy(Math.min(capacity.get(), maxEnergyOutput), false);
                                capacity.addAndGet(-received);
                                ((TCEnergyStorage) energy).consumePower(received);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
        if (capability == CapabilityEnergy.ENERGY && facing != Direction.UP) {
            return energy.cast();
        }
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this.inventory));
        }
        return super.getCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(CompoundNBT compound) {
        CompoundNBT energyTag = compound.getCompound("energy");
        energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));

        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        this.currBurnTime = compound.getInt("CurrBurnTime");
        this.sumBurnTime = compound.getInt("SumBurnTime");
        this.maxEnergy = compound.getInt("maxEn");
        this.energyGeneration = compound.getInt("gen");

        super.read(compound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        energy.ifPresent(h ->
        {
            CompoundNBT tag = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            compound.put("energy", tag);
        });

        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList(), false);
        compound.putInt("CurrBurnTime", this.currBurnTime);
        compound.putInt("SumBurnTime", this.sumBurnTime);

        compound.putInt("maxEn", this.maxEnergy);
        compound.putInt("gen", this.energyGeneration);

        return super.write(compound);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CoalGeneratorContainer(ModContainerTypes.COAL_GENERATOR.get(), id, world, pos, playerEntity, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getBlockState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT nbt) {
        this.read(nbt);
    }
}
