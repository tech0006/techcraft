package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.common.container.*;
import com.tech0006.techcraft.techcraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, techcraft.MOD_ID);

    public static final RegistryObject<ContainerType<SafeContainer>> SAFE = CONTAINER_TYPES.register("safe",
            () -> IForgeContainerType.create(SafeContainer::new));

    public static final RegistryObject<ContainerType<TCbenchContainer>> TC_BENCH = CONTAINER_TYPES.register("tc_bench",
            () -> IForgeContainerType.create(TCbenchContainer::new));
    public static final RegistryObject<ContainerType<TCforgeContainer>> TC_FORGE = CONTAINER_TYPES.register("tc_forge",
            () -> IForgeContainerType.create(TCforgeContainer::new));

    public static final RegistryObject<ContainerType<CoalGeneratorContainer>> COAL_GENERATOR = CONTAINER_TYPES.register("coal_generator",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new CoalGeneratorContainer(TCContainerTypes.COAL_GENERATOR.get(),
                        windowId,
                        techcraft.proxy.getClientWorld(),
                        data.readBlockPos(),
                        techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<ElectricFurnaceContainer>> ELECTRIC_FURNACE = CONTAINER_TYPES.register("electric_furnace",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new ElectricFurnaceContainer(TCContainerTypes.ELECTRIC_FURNACE.get(),
                        windowId,
                        techcraft.proxy.getClientWorld(),
                        data.readBlockPos(),
                        techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<WireShaperContainer>> WIRE_SHAPER = CONTAINER_TYPES.register("wire_shaper",
            () -> IForgeContainerType.create(WireShaperContainer::new));
    public static final RegistryObject<ContainerType<AlloyPlantContainer>> ALLOY_PLANT = CONTAINER_TYPES.register("alloy_plant",
            () -> IForgeContainerType.create(AlloyPlantContainer::new));

    public static final RegistryObject<ContainerType<ElectricCrusherContainer>> ELECTRIC_CRUSHER = CONTAINER_TYPES.register("electric_crusher",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new ElectricCrusherContainer(TCContainerTypes.ELECTRIC_CRUSHER.get(),
                        windowId,
                        techcraft.proxy.getClientWorld(),
                        data.readBlockPos(),
                        techcraft.proxy.getClientPlayer());
            }));
}
