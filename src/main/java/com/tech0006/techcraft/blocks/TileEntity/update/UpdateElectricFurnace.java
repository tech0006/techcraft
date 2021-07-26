package com.tech0006.techcraft.blocks.TileEntity.update;

import com.tech0006.techcraft.blocks.TileEntity.ElectricFurnaceTileEntity;
import com.tech0006.techcraft.techcraft;
import com.tech0006.techcraft.util.tools.MachineEnergy;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateElectricFurnace {

    BlockPos pos;
    int en, enMax;

    int enUse, currT, sumT;

    public UpdateElectricFurnace(PacketBuffer buf) {
        pos = buf.readBlockPos();
        en = buf.readInt();
        enMax = buf.readInt();
        enUse = buf.readInt();
        currT = buf.readInt();
        sumT = buf.readInt();
    }

    public UpdateElectricFurnace(BlockPos pos, MachineEnergy en, int enUse, int currT, int sumT) {
        this.pos = pos;
        this.en = en.getEnergyStored();
        this.enMax = en.getMaxEnergyStored();
        this.enUse = enUse;
        this.currT = currT;
        this.sumT = sumT;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(en);
        buf.writeInt(enMax);
        buf.writeInt(enUse);
        buf.writeInt(currT);
        buf.writeInt(sumT);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            World world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                TileEntity te = world.getBlockEntity(pos);
                if (te instanceof ElectricFurnaceTileEntity) {
                    ElectricFurnaceTileEntity gen = (ElectricFurnaceTileEntity) te;
                    gen.getEnergy().setStored(en);
                    gen.getEnergy().setMax(enMax);
                    gen.setEnergyUse(enUse);
                    gen.processTime = currT;
                    gen.processTimeTotal = sumT;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
