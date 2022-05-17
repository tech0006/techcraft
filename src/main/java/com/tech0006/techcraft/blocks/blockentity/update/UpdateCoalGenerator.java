package com.tech0006.techcraft.blocks.blockentity.update;

import com.tech0006.techcraft.blocks.blockentity.functional.CoalGeneratorBlockEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateCoalGenerator {
    private final BlockPos pos;
    private final int currentEnergy;
    private final int currBurnTime, sumBurnTime;

    public UpdateCoalGenerator(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        currentEnergy = buf.readInt();
        currBurnTime = buf.readInt();
        sumBurnTime = buf.readInt();
    }

    public UpdateCoalGenerator(BlockPos pos, int currentEnergy, int currBurnTime, int sumBurnTime) {
        this.pos = pos;
        this.currentEnergy = currentEnergy;
        this.currBurnTime = currBurnTime;
        this.sumBurnTime = sumBurnTime;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(currentEnergy);
        buf.writeInt(currBurnTime);
        buf.writeInt(sumBurnTime);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Level world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof CoalGeneratorBlockEntity) {
                    CoalGeneratorBlockEntity gen = (CoalGeneratorBlockEntity) te;
                    gen.getEnergy().setStored(currentEnergy);
                    gen.processTime = currBurnTime;
                    gen.processTimeTotal = sumBurnTime;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
