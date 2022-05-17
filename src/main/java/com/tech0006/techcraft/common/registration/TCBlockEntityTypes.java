package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.blocks.blockentity.functional.*;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_1.EnergyPipeBlockEntityTier1;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_2.EnergyPipeBlockEntityTier2;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_3.EnergyPipeBlockEntityTier3;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_4.EnergyPipeBlockEntityTier4;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_5.EnergyPipeBlockEntityTier5;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_6.EnergyPipeBlockEntityTier6;
import com.tech0006.techcraft.blocks.blockentity.functional.pipe.energy.tier_7.EnergyPipeBlockEntityTier7;
import com.tech0006.techcraft.techcraft;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, techcraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<SafeBlockEntity>> SAFE = TILE_ENTITY_TYPES.register("safe",
            () -> BlockEntityType.Builder.of(SafeBlockEntity::new,
                    TCBlocks.BRONZE_SAFE.get(),
                    TCBlocks.COPPER_SAFE.get(),
                    TCBlocks.GOLD_SAFE.get(),
                    TCBlocks.IRON_SAFE.get(),
                    TCBlocks.TIN_SAFE.get()).build(null));

    public static final RegistryObject<BlockEntityType<TCbenchBlockEntity>> TC_BENCH = TILE_ENTITY_TYPES.register("tc_bench",
            () -> BlockEntityType.Builder.of(TCbenchBlockEntity::new, TCBlocks.TC_BENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<TCforgeBlockEntity>> TC_FORGE = TILE_ENTITY_TYPES.register("tc_forge",
            () -> BlockEntityType.Builder.of(TCforgeBlockEntity::new, TCBlocks.TC_FORGE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = TILE_ENTITY_TYPES.register("coal_generator",
            () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new, TCBlocks.COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE = TILE_ENTITY_TYPES.register("electric_furnace",
            () -> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, TCBlocks.ELECTRIC_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<WireShaperBlockEntity>> WIRE_SHAPER = TILE_ENTITY_TYPES.register("wire_shaper",
            () -> BlockEntityType.Builder.of(WireShaperBlockEntity::new, TCBlocks.WIRE_SHAPER.get()).build(null));
    public static final RegistryObject<BlockEntityType<AlloyPlantBlockEntity>> ALLOY_PLANT = TILE_ENTITY_TYPES.register("alloy_plant",
            () -> BlockEntityType.Builder.of(AlloyPlantBlockEntity::new, TCBlocks.ALLOY_PLANT.get()).build(null));

    public static final RegistryObject<BlockEntityType<ElectricCrusherBlockEntity>> ELECTRIC_CRUSHER = TILE_ENTITY_TYPES.register("electric_crusher",
            () -> BlockEntityType.Builder.of(ElectricCrusherBlockEntity::new, TCBlocks.ELECTRIC_CRUSHER.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier1>> ENERGY_PIPE_TIER_1 = TILE_ENTITY_TYPES.register("energy_pipe_tier_1",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier1::new, TCBlocks.ENERGY_PIPE_TIER_1.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier2>> ENERGY_PIPE_TIER_2 = TILE_ENTITY_TYPES.register("energy_pipe_tier_2",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier2::new, TCBlocks.ENERGY_PIPE_TIER_2.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier3>> ENERGY_PIPE_TIER_3 = TILE_ENTITY_TYPES.register("energy_pipe_tier_3",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier3::new, TCBlocks.ENERGY_PIPE_TIER_3.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier4>> ENERGY_PIPE_TIER_4 = TILE_ENTITY_TYPES.register("energy_pipe_tier_4",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier4::new, TCBlocks.ENERGY_PIPE_TIER_4.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier5>> ENERGY_PIPE_TIER_5 = TILE_ENTITY_TYPES.register("energy_pipe_tier_5",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier5::new, TCBlocks.ENERGY_PIPE_TIER_5.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier6>> ENERGY_PIPE_TIER_6 = TILE_ENTITY_TYPES.register("energy_pipe_tier_6",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier6::new, TCBlocks.ENERGY_PIPE_TIER_6.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPipeBlockEntityTier7>> ENERGY_PIPE_TIER_7 = TILE_ENTITY_TYPES.register("energy_pipe_tier_7",
            () -> BlockEntityType.Builder.of(EnergyPipeBlockEntityTier7::new, TCBlocks.ENERGY_PIPE_TIER_7.get()).build(null));
}
