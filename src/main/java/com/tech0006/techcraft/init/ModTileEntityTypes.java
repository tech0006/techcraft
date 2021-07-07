package com.tech0006.techcraft.init;


import com.tech0006.techcraft.blocks.TileEntity.*;
import com.tech0006.techcraft.blocks.solarPanel.TileEntity.*;
import com.tech0006.techcraft.techcraft;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, techcraft.MOD_ID);
    public static final RegistryObject<TileEntityType<CopperSafeTileEntity>> COPPER_SAFE = TILE_ENTITY_TYPES.register("copper_safe",
            () -> TileEntityType.Builder.create(CopperSafeTileEntity::new, Blocks.COPPER_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<TinSafeTileEntity>> TIN_SAFE = TILE_ENTITY_TYPES.register("tin_safe",
            () -> TileEntityType.Builder.create(TinSafeTileEntity::new, Blocks.TIN_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<BronzeSafeTileEntity>> BRONZE_SAFE = TILE_ENTITY_TYPES.register("bronze_safe",
            () -> TileEntityType.Builder.create(BronzeSafeTileEntity::new, Blocks.BRONZE_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<IronSafeTileEntity>> IRON_SAFE = TILE_ENTITY_TYPES.register("iron_safe",
            () -> TileEntityType.Builder.create(IronSafeTileEntity::new, Blocks.IRON_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<GoldSafeTileEntity>> GOLD_SAFE = TILE_ENTITY_TYPES.register("gold_safe",
            () -> TileEntityType.Builder.create(GoldSafeTileEntity::new, Blocks.GOLD_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<TCbenchTileEntity>> TC_BENCH = TILE_ENTITY_TYPES.register("tc_bench",
            () -> TileEntityType.Builder.create(TCbenchTileEntity::new, Blocks.TC_BENCH.get()).build(null));
    public static final RegistryObject<TileEntityType<TCforgeTileEntity>> TC_FORGE = TILE_ENTITY_TYPES.register("tc_forge",
            () -> TileEntityType.Builder.create(TCforgeTileEntity::new, Blocks.TC_FORGE.get()).build(null));



    public static final RegistryObject<TileEntityType<TileEntityLeadstoneSolarPanel>> LEADSTONE_TILE = TILE_ENTITY_TYPES.register("solar_panel_leadstone",
            () -> TileEntityType.Builder.create(TileEntityLeadstoneSolarPanel::new, Blocks.SOLAR_PANEL_LEADSTONE.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntityHardenedSolarPanel>> HARDENED_TILE = TILE_ENTITY_TYPES.register("solar_panel_hardened",
            () -> TileEntityType.Builder.create(TileEntityHardenedSolarPanel::new, Blocks.SOLAR_PANEL_HARDENED.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntityAdvancedSolarPanel>> ADVANCED_TILE = TILE_ENTITY_TYPES.register("solar_panel_advanced",
            () -> TileEntityType.Builder.create(TileEntityAdvancedSolarPanel::new, Blocks.SOLAR_PANEL_ADVANCED.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntityRedstoneSolarPanel>> REDSTONE_TILE = TILE_ENTITY_TYPES.register("solar_panel_redstone",
            () -> TileEntityType.Builder.create(TileEntityRedstoneSolarPanel::new, Blocks.SOLAR_PANEL_REDSTONE.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntityResonantSolarPanel>> RESONANT_TILE = TILE_ENTITY_TYPES.register("solar_panel_resonant",
            () -> TileEntityType.Builder.create(TileEntityResonantSolarPanel::new, Blocks.SOLAR_PANEL_RESONANT.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntitySignalumSolarPanel>> SIGNALUM_TILE = TILE_ENTITY_TYPES.register("solar_panel_signalum",
            () -> TileEntityType.Builder.create(TileEntitySignalumSolarPanel::new, Blocks.SOLAR_PANEL_SIGNALUM.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntityUltimateSolarPanel>> ULTIMATE_TILE = TILE_ENTITY_TYPES.register("solar_panel_ultimate",
            () -> TileEntityType.Builder.create(TileEntityUltimateSolarPanel::new, Blocks.SOLAR_PANEL_ULTIMATE.get()).build(null));


    public static final RegistryObject<TileEntityType<CoalGeneratorTileEntity>> COAL_GENERATOR = TILE_ENTITY_TYPES.register("coal_generator",
            () -> TileEntityType.Builder.create(CoalGeneratorTileEntity::new, Blocks.COAL_GENERATOR.get()).build(null));
}
