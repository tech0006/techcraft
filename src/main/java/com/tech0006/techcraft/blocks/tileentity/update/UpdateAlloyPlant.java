package com.tech0006.techcraft.blocks.tileentity.update;

import com.tech0006.techcraft.blocks.tileentity.functional.AlloyPlantTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateAlloyPlant {
    private final BlockPos pos;
    private final int currentFluid;
    private final int currBurnTime, sumBurnTime;

    public UpdateAlloyPlant(PacketBuffer buf) {
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

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(currentFluid);
        buf.writeInt(currBurnTime);
        buf.writeInt(sumBurnTime);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            World world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                TileEntity te = world.getBlockEntity(pos);
                if (te instanceof AlloyPlantTileEntity) {
                    AlloyPlantTileEntity gen = (AlloyPlantTileEntity) te;
                    gen.setFluid(currentFluid);
                    gen.processTime = currBurnTime;
                    gen.processTimeTotal = sumBurnTime;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}