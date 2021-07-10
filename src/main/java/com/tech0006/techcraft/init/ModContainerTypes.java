package com.tech0006.techcraft.init;

import com.tech0006.techcraft.GUI.Container.*;
import com.tech0006.techcraft.GUI.Container.SolarPanel.*;
import com.tech0006.techcraft.blocks.TileEntity.CoalGeneratorTileEntity;
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

    public static final RegistryObject<ContainerType<SolarPanelLeadstoneContainer>> SOLAR_PANEL_LEADSTONE = CONTAINER_TYPES.register("solar_panel_leadstone",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelLeadstoneContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<SolarPanelHardenedContainer>> SOLAR_PANEL_HARDENED = CONTAINER_TYPES.register("solar_panel_hardened",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelHardenedContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<SolarPanelAdvancedContainer>> SOLAR_PANEL_ADVANCED = CONTAINER_TYPES.register("solar_panel_advanced",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelAdvancedContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<SolarPanelRedstoneContainer>> SOLAR_PANEL_REDSTONE = CONTAINER_TYPES.register("solar_panel_redstone",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelRedstoneContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<SolarPanelResonantContainer>> SOLAR_PANEL_RESONANT = CONTAINER_TYPES.register("solar_panel_resonant",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelResonantContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<SolarPanelSignalumContainer>> SOLAR_PANEL_SIGNALUM = CONTAINER_TYPES.register("solar_panel_signalum",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelSignalumContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));

    public static final RegistryObject<ContainerType<SolarPanelUltimateContainer>> SOLAR_PANEL_ULTIMATE = CONTAINER_TYPES.register("solar_panel_ultimate",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new SolarPanelUltimateContainer(windowId, techcraft.proxy.getClientWorld(), data.readBlockPos(), techcraft.proxy.getClientPlayer());
            }));





    public static final RegistryObject<ContainerType<CoalGeneratorContainer>> COAL_GENERATOR = CONTAINER_TYPES.register("coal_generator",
            () -> IForgeContainerType.create((windowId, inv, data) ->
            {
                return new CoalGeneratorContainer(ModContainerTypes.COAL_GENERATOR.get(),
                        windowId,
                        techcraft.proxy.getClientWorld(),
                        data.readBlockPos(),
                        techcraft.proxy.getClientPlayer());
            }));
}
