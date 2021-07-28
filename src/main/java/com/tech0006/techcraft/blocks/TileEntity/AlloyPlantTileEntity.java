package com.tech0006.techcraft.blocks.TileEntity;

import com.tech0006.techcraft.GUI.Container.AlloyPlantContainer;
import com.tech0006.techcraft.blocks.TileEntity.base.FluidTile;
import com.tech0006.techcraft.blocks.TileEntity.base.LavaFluidTile;
import com.tech0006.techcraft.blocks.TileEntity.update.UpdateAlloyPlant;
import com.tech0006.techcraft.blocks.TileEntity.update.UpdateElectricFurnace;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.techcraft;
import com.tech0006.techcraft.util.handler.AlloyPlantItemHandler;
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
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class AlloyPlantTileEntity extends LavaFluidTile implements INamedContainerProvider {

    private ITextComponent customName;
    private AlloyPlantItemHandler inventory;

    public int processTime = 0, processTimeTotal = 0;

    public AlloyPlantTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

        this.inventory = new AlloyPlantItemHandler(2);
    }

    public AlloyPlantTileEntity() {
        this(ModTileEntityTypes.ALLOY_PLANT.get());
    }

    @Override
    public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new AlloyPlantContainer(windowID, playerInv, this);
    }

    @Override
    public void tick() {

        if (this.world != null && !this.world.isRemote) {
            System.out.println(this.tank.getFluid().getAmount() + "\t" + this.tank.getFluid().getTranslationKey());
            decrFluid(1);
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateAlloyPlant(getPos(), this.tank.getFluidAmount(), processTime, processTimeTotal));
        }
    }

    public int getFluid()
    {
        return this.tank.getFluid().getAmount();
    }

    public void setFluid(int fluid)
    {
        FluidStack curr = this.tank.getFluid();
        curr.setAmount(fluid);
        this.tank.setFluid(curr);
    }

    public boolean decrFluid(int count)
    {
        int curr = this.tank.getFluid().getAmount();
        if (curr - count < 0)
        {
            return false;
        }
        setFluid(curr - count);
        return true;
    }

    public void setCustomName(ITextComponent name) {
        this.customName = name;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + techcraft.MOD_ID + ".alloy_plant");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }

        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }

        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());

        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
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

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)

            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
        return super.getCapability(cap, side);
    }

}

