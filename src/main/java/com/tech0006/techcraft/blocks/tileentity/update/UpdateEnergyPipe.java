package com.tech0006.techcraft.blocks.tileentity.update;

import com.tech0006.techcraft.blocks.tileentity.functional.pipe.energy.EnergyPipeTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateEnergyPipe {
    private final BlockPos pos;
    private final int currentEnergy;

    public UpdateEnergyPipe(PacketBuffer buf) {
        pos = buf.readBlockPos();
        currentEnergy = buf.readInt();
    }

    public UpdateEnergyPipe(BlockPos pos, int currentEnergy) {
        this.pos = pos;
        this.currentEnergy = currentEnergy;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(currentEnergy);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            World world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                TileEntity te = world.getBlockEntity(pos);
                if (te instanceof EnergyPipeTileEntity) {
                    EnergyPipeTileEntity gen = (EnergyPipeTileEntity) te;
                    gen.getEnergy().setStored(currentEnergy);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}