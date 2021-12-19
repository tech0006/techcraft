package com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy;

import com.tech0006.techcraft.blocks.tileentity.base.PipeTile;
import com.tech0006.techcraft.blocks.tileentity.update.UpdateEnergyPipe;
import com.tech0006.techcraft.common.packet.PacketHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.concurrent.atomic.AtomicInteger;

public class EnergyPipeTileEntity extends PipeTile {


    public EnergyPipeTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {

        if (this.level != null)
        {
            if (!this.level.isClientSide) {
                sendEnergy();
                PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateEnergyPipe(getBlockPos(), energy.getEnergyStored()));
            }
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    /**
     * level mustn't be null (NullPointerException)
     */
    private void sendEnergy() {
        AtomicInteger capacity = new AtomicInteger(getEnergyStored());

        boolean[] sides = new boolean[6];

        for (int i = getRandomNumber(0, 6); (i < Direction.values().length) && (capacity.get() > 0); i = getRandomNumber(0, 6)) {
            Direction facing = Direction.values()[i];
            sides[i] = true;
            assert level != null;
            TileEntity tileEntity = level.getBlockEntity(worldPosition.relative(facing));
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).ifPresent(handler ->
                {
                    if (handler.canReceive()) {
                        int received = handler.receiveEnergy(use_val, false);
                        capacity.addAndGet(-received);
                        energy.use(received);
                    }
                });
            }

            boolean flag = true;
            for (boolean side : sides) { if (!side) { flag = false; break; } }
            if (flag) { return; }
        }
    }

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
