package com.tech0006.techcraft.blocks.blockentity.update;

import com.tech0006.techcraft.blocks.blockentity.functional.ElectricCrusherBlockEntity;
import com.tech0006.techcraft.techcraft;
import com.tech0006.techcraft.util.tools.energy.InEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateElectricCrusher {
    BlockPos pos;
    int en, enMax;

    int enUse, currT, sumT;

    public UpdateElectricCrusher(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        en = buf.readInt();
        enMax = buf.readInt();
        enUse = buf.readInt();
        currT = buf.readInt();
        sumT = buf.readInt();
    }

    public UpdateElectricCrusher(BlockPos pos, InEnergy en, int enUse, int currT, int sumT) {
        this.pos = pos;
        this.en = en.getEnergyStored();
        this.enMax = en.getMaxEnergyStored();
        this.enUse = enUse;
        this.currT = currT;
        this.sumT = sumT;
    }

    public void toBytes(FriendlyByteBuf buf) {
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
            Level world = techcraft.proxy.getClientWorld();
            if (world.isLoaded(pos)) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof ElectricCrusherBlockEntity) {
                    ElectricCrusherBlockEntity gen = (ElectricCrusherBlockEntity) te;
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
