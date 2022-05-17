package com.tech0006.techcraft.blocks.blockentity.update;

import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.EnergyPipeBlockEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;


public class UpdateEnergyPipe {
    private final BlockPos pos;
    private final int currentEnergy;

    public UpdateEnergyPipe(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        currentEnergy = buf.readInt();
    }

    public UpdateEnergyPipe(BlockPos pos, int currentEnergy) {
        this.pos = pos;
        this.currentEnergy = currentEnergy;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(currentEnergy);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Level world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof EnergyPipeBlockEntity) {
                    EnergyPipeBlockEntity gen = (EnergyPipeBlockEntity) te;
                    gen.getEnergy().setStored(currentEnergy);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
