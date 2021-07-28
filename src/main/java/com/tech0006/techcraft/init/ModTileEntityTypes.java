package com.tech0006.techcraft.init;


import com.tech0006.techcraft.blocks.AlloyPlant;
import com.tech0006.techcraft.blocks.TileEntity.*;
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

    public static final RegistryObject<TileEntityType<CoalGeneratorTileEntity>> COAL_GENERATOR = TILE_ENTITY_TYPES.register("coal_generator",
            () -> TileEntityType.Builder.create(CoalGeneratorTileEntity::new, Blocks.COAL_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE = TILE_ENTITY_TYPES.register("electric_furnace",
            () -> TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, Blocks.ELECTRIC_FURNACE.get()).build(null));

    public static final RegistryObject<TileEntityType<WireShaperTileEntity>> WIRE_SHAPER = TILE_ENTITY_TYPES.register("wire_shaper",
            () -> TileEntityType.Builder.create(WireShaperTileEntity::new, Blocks.WIRE_SHAPER.get()).build(null));

    public static final RegistryObject<TileEntityType<AlloyPlantTileEntity>> ALLOY_PLANT = TILE_ENTITY_TYPES.register("alloy_plant",
            () -> TileEntityType.Builder.create(AlloyPlantTileEntity::new, Blocks.ALLOY_PLANT.get()).build(null));
}
