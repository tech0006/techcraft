package com.tech0006.techcraft.blocks.TileEntity.update;

import com.tech0006.techcraft.blocks.TileEntity.CoalGeneratorTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateCoalGenerator {
    private BlockPos pos;
    private int currentEnergy;
    private int currBurnTime, sumBurnTime;

    public UpdateCoalGenerator(PacketBuffer buf) {
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

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(currentEnergy);
        buf.writeInt(currBurnTime);
        buf.writeInt(sumBurnTime);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            World world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                TileEntity te = world.getBlockEntity(pos);
                if (te instanceof CoalGeneratorTileEntity) {
                    CoalGeneratorTileEntity gen = (CoalGeneratorTileEntity) te;
                    gen.getEnergy().setStored(currentEnergy);
                    gen.processTime = currBurnTime;
                    gen.processTimeTotal = sumBurnTime;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
