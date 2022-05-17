package com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy;

import com.tech0006.techcraft.blocks.blockentity.base.PipeTile;
import com.tech0006.techcraft.blocks.blockentity.update.UpdateEnergyPipe;
import com.tech0006.techcraft.common.packet.PacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.concurrent.atomic.AtomicInteger;

public class EnergyPipeBlockEntity extends PipeTile {


    public EnergyPipeBlockEntity(BlockEntityType tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public void tick(BlockState state) {

        if (level != null)
        {
            if (!level.isClientSide) {
                sendEnergy();
                PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateEnergyPipe(getBlockPos(), getEnergyStored()));
            }
        }
    }

    public static int getRandomNumber(int min, int max) {
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
            BlockEntity tileEntity = level.getBlockEntity(worldPosition.relative(facing));
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
