package com.tech0006.techcraft.blocks.blockentity.update;

import com.tech0006.techcraft.blocks.blockentity.functional.AlloyPlantBlockEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateAlloyPlant {
    private final BlockPos pos;
    private final int currentFluid;
    private final int currBurnTime, sumBurnTime;

    public UpdateAlloyPlant(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        currentFluid = buf.readInt();
        currBurnTime = buf.readInt();
        sumBurnTime = buf.readInt();
    }

    public UpdateAlloyPlant(BlockPos pos, int currentFluid, int currBurnTime, int sumBurnTime) {
        this.pos = pos;
        this.currentFluid = currentFluid;
        this.currBurnTime = currBurnTime;
        this.sumBurnTime = sumBurnTime;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(currentFluid);
        buf.writeInt(currBurnTime);
        buf.writeInt(sumBurnTime);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Level world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof AlloyPlantBlockEntity) {
                    AlloyPlantBlockEntity gen = (AlloyPlantBlockEntity) te;
                    gen.setFluid(currentFluid);
                    gen.processTime = currBurnTime;
                    gen.processTimeTotal = sumBurnTime;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
