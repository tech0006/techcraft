package com.tech0006.techcraft.init;

import com.tech0006.techcraft.GUI.Container.*;
import com.tech0006.techcraft.techcraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, techcraft.MOD_ID);
    public static final RegistryObject<ContainerType<CopperSafeContainer>> COPPER_SAFE = CONTAINER_TYPES.register("copper_safe",
            () -> IForgeContainerType.create(CopperSafeContainer::new));
    public static final RegistryObject<ContainerType<TinSafeContainer>> TIN_SAFE = CONTAINER_TYPES.register("tin_safe",
            () -> IForgeContainerType.create(TinSafeContainer::new));
    public static final RegistryObject<ContainerType<BronzeSafeContainer>> BRONZE_SAFE = CONTAINER_TYPES.register("bronze_safe",
            () -> IForgeContainerType.create(BronzeSafeContainer::new));
    public static final RegistryObject<ContainerType<IronSafeContainer>> IRON_SAFE = CONTAINER_TYPES.register("iron_safe",
            () -> IForgeContainerType.create(IronSafeContainer::new));
    public static final RegistryObject<ContainerType<GoldSafeContainer>> GOLD_SAFE = CONTAINER_TYPES.register("gold_safe",
            () -> IForgeContainerType.create(GoldSafeContainer::new));
    public static final RegistryObject<ContainerType<TCbenchContainer>> TC_BENCH = CONTAINER_TYPES.register("tc_bench",
            () -> IForgeContainerType.create(TCbenchContainer::new));
    public static final RegistryObject<ContainerType<TCforgeContainer>> TC_FORGE = CONTAINER_TYPES.register("tc_forge",
            () -> IForgeContainerType.create(TCforgeContainer::new));

    public static final RegistryObject<ContainerType<CoalGeneratorContainer>> COAL_GENERATOR = CONTAINER_TYPES.register("coal_generator",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new CoalGeneratorContainer(ModContainerTypes.COAL_GENERATOR.get(),
                        windowId,
                        techcraft.proxy.getClientWorld(),
                        data.readBlockPos(),
                        techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<ElectricFurnaceContainer>> ELECTRIC_FURNACE = CONTAINER_TYPES.register("electric_furnace",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new ElectricFurnaceContainer(ModContainerTypes.ELECTRIC_FURNACE.get(),
                        windowId,
                        techcraft.proxy.getClientWorld(),
                        data.readBlockPos(),
                        techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<WireShaperContainer>> WIRE_SHAPER = CONTAINER_TYPES.register("wire_shaper",
            () -> IForgeContainerType.create(WireShaperContainer::new));
}
