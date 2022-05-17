package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.common.container.*;
import com.tech0006.techcraft.techcraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCContainerTypes {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, techcraft.MOD_ID);

    public static final RegistryObject<MenuType<SafeContainer>> SAFE = CONTAINER_TYPES.register("safe", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new SafeContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<TCbenchContainer>> TC_BENCH = CONTAINER_TYPES.register("tc_bench", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new TCbenchContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<TCforgeContainer>> TC_FORGE = CONTAINER_TYPES.register("tc_forge", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new TCforgeContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<CoalGeneratorContainer>> COAL_GENERATOR = CONTAINER_TYPES.register("coal_generator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new CoalGeneratorContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<ElectricFurnaceContainer>> ELECTRIC_FURNACE = CONTAINER_TYPES.register("electric_furnace", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new ElectricFurnaceContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<WireShaperContainer>> WIRE_SHAPER = CONTAINER_TYPES.register("wire_shaper", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new WireShaperContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<AlloyPlantContainer>> ALLOY_PLANT = CONTAINER_TYPES.register("alloy_plant", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new AlloyPlantContainer(windowId, level, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<ElectricCrusherContainer>> ELECTRIC_CRUSHER = CONTAINER_TYPES.register("electric_crusher", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new ElectricCrusherContainer(windowId, level, pos, inv, inv.player);
    }));

}
