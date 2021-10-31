package com.tech0006.techcraft.init;


import com.tech0006.techcraft.blocks.TileEntity.*;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_1.EnergyPipeTileEntityTier1;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_2.EnergyPipeTileEntityTier2;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_3.EnergyPipeTileEntityTier3;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_4.EnergyPipeTileEntityTier4;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_5.EnergyPipeTileEntityTier5;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_6.EnergyPipeTileEntityTier6;
import com.tech0006.techcraft.blocks.TileEntity.pipe.energy.tier_7.EnergyPipeTileEntityTier7;
import com.tech0006.techcraft.techcraft;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, techcraft.MOD_ID);
    public static final RegistryObject<TileEntityType<CopperSafeTileEntity>> COPPER_SAFE = TILE_ENTITY_TYPES.register("copper_safe",
            () -> TileEntityType.Builder.of(CopperSafeTileEntity::new, Blocks.COPPER_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<TinSafeTileEntity>> TIN_SAFE = TILE_ENTITY_TYPES.register("tin_safe",
            () -> TileEntityType.Builder.of(TinSafeTileEntity::new, Blocks.TIN_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<BronzeSafeTileEntity>> BRONZE_SAFE = TILE_ENTITY_TYPES.register("bronze_safe",
            () -> TileEntityType.Builder.of(BronzeSafeTileEntity::new, Blocks.BRONZE_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<IronSafeTileEntity>> IRON_SAFE = TILE_ENTITY_TYPES.register("iron_safe",
            () -> TileEntityType.Builder.of(IronSafeTileEntity::new, Blocks.IRON_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<GoldSafeTileEntity>> GOLD_SAFE = TILE_ENTITY_TYPES.register("gold_safe",
            () -> TileEntityType.Builder.of(GoldSafeTileEntity::new, Blocks.GOLD_SAFE.get()).build(null));
    public static final RegistryObject<TileEntityType<TCbenchTileEntity>> TC_BENCH = TILE_ENTITY_TYPES.register("tc_bench",
            () -> TileEntityType.Builder.of(TCbenchTileEntity::new, Blocks.TC_BENCH.get()).build(null));
    public static final RegistryObject<TileEntityType<TCforgeTileEntity>> TC_FORGE = TILE_ENTITY_TYPES.register("tc_forge",
            () -> TileEntityType.Builder.of(TCforgeTileEntity::new, Blocks.TC_FORGE.get()).build(null));

    public static final RegistryObject<TileEntityType<CoalGeneratorTileEntity>> COAL_GENERATOR = TILE_ENTITY_TYPES.register("coal_generator",
            () -> TileEntityType.Builder.of(CoalGeneratorTileEntity::new, Blocks.COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE = TILE_ENTITY_TYPES.register("electric_furnace",
            () -> TileEntityType.Builder.of(ElectricFurnaceTileEntity::new, Blocks.ELECTRIC_FURNACE.get()).build(null));

    public static final RegistryObject<TileEntityType<WireShaperTileEntity>> WIRE_SHAPER = TILE_ENTITY_TYPES.register("wire_shaper",
            () -> TileEntityType.Builder.of(WireShaperTileEntity::new, Blocks.WIRE_SHAPER.get()).build(null));
    public static final RegistryObject<TileEntityType<AlloyPlantTileEntity>> ALLOY_PLANT = TILE_ENTITY_TYPES.register("alloy_plant",
            () -> TileEntityType.Builder.of(AlloyPlantTileEntity::new, Blocks.ALLOY_PLANT.get()).build(null));

    public static final RegistryObject<TileEntityType<ElectricCrusherTileEntity>> ELECTRIC_CRUSHER = TILE_ENTITY_TYPES.register("electric_crusher",
            () -> TileEntityType.Builder.of(ElectricCrusherTileEntity::new, Blocks.ELECTRIC_CRUSHER.get()).build(null));

    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier1>> ENERGY_PIPE_TIER_1 = TILE_ENTITY_TYPES.register("energy_pipe_tier_1",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier1::new, Blocks.ENERGY_PIPE_TIER_1.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier2>> ENERGY_PIPE_TIER_2 = TILE_ENTITY_TYPES.register("energy_pipe_tier_2",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier2::new, Blocks.ENERGY_PIPE_TIER_2.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier3>> ENERGY_PIPE_TIER_3 = TILE_ENTITY_TYPES.register("energy_pipe_tier_3",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier3::new, Blocks.ENERGY_PIPE_TIER_3.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier4>> ENERGY_PIPE_TIER_4 = TILE_ENTITY_TYPES.register("energy_pipe_tier_4",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier4::new, Blocks.ENERGY_PIPE_TIER_4.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier5>> ENERGY_PIPE_TIER_5 = TILE_ENTITY_TYPES.register("energy_pipe_tier_5",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier5::new, Blocks.ENERGY_PIPE_TIER_5.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier6>> ENERGY_PIPE_TIER_6 = TILE_ENTITY_TYPES.register("energy_pipe_tier_6",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier6::new, Blocks.ENERGY_PIPE_TIER_6.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyPipeTileEntityTier7>> ENERGY_PIPE_TIER_7 = TILE_ENTITY_TYPES.register("energy_pipe_tier_7",
            () -> TileEntityType.Builder.of(EnergyPipeTileEntityTier7::new, Blocks.ENERGY_PIPE_TIER_7.get()).build(null));
}
