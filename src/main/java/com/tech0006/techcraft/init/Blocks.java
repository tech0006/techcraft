package com.tech0006.techcraft.init;

import com.tech0006.techcraft.blocks.*;
import com.tech0006.techcraft.techcraft;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, techcraft.MOD_ID);

    //COPPER
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore",
            () -> new Block(Block.Properties.create(Material.ROCK)
                    .hardnessAndResistance(3.0F, 3.0F)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block",
            () -> new Block(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    //TIN
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore",
            () -> new Block(Block.Properties.create(Material.ROCK)
                    .hardnessAndResistance(3.0F, 3.0F)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block",
            () -> new Block(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    //BRONZE
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block",
            () -> new Block(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(4.0f, 4.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(1)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> COPPER_SAFE = BLOCKS.register("copper_safe",
            () -> new CopperSafe(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> TIN_SAFE = BLOCKS.register("tin_safe",
            () -> new TinSafe(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> BRONZE_SAFE = BLOCKS.register("bronze_safe",
            () -> new BronzeSafe(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> IRON_SAFE = BLOCKS.register("iron_safe",
            () -> new IronSafe(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> GOLD_SAFE = BLOCKS.register("gold_safe",
            () -> new GoldSafe(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> TC_BENCH = BLOCKS.register("tc_bench",
            () -> new TCbench(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> TC_FORGE = BLOCKS.register("tc_forge",
            () -> new TCforge(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<CoalGenerator> COAL_GENERATOR = BLOCKS.register("coal_generator",
            () -> new CoalGenerator(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<ElectricFurnace> ELECTRIC_FURNACE = BLOCKS.register("electric_furnace",
            () -> new ElectricFurnace(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> WIRE_SHAPER = BLOCKS.register("wire_shaper",
            () -> new WireShaper(Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 3.0f)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0)
                    .sound(SoundType.METAL)));

}