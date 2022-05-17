package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.common.registration.item.ArmorMaterials;
import com.tech0006.techcraft.common.registration.item.CustomToolItem;
import com.tech0006.techcraft.common.registration.item.ItemTiers;
import com.tech0006.techcraft.common.registration.item.MaterialItems;
import com.tech0006.techcraft.techcraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, techcraft.MOD_ID);


    //TIN
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", MaterialItems::new);
    //BRONZE
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", MaterialItems::new);

    //TOOL
    //COPPER
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ItemTiers.COPPER,
                    1.0F,
                    5.0F,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ItemTiers.COPPER,
                    1.5f,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ItemTiers.COPPER,
                    2,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ItemTiers.COPPER,
                    2,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ItemTiers.COPPER,
            		1,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    //TIN
    public static final RegistryObject<Item> TIN_AXE = ITEMS.register("tin_axe",
            () -> new AxeItem(ItemTiers.TIN,
                    1.0F,
                    5.0F,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_SHOVEL = ITEMS.register("tin_shovel",
            () -> new ShovelItem(ItemTiers.TIN,
                    1.5f,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword",
            () -> new SwordItem(ItemTiers.TIN,
                    2,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe",
            () -> new PickaxeItem(ItemTiers.TIN,
                    2,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe",
            () -> new HoeItem(ItemTiers.TIN,
            		1,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    //BRONZE
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(ItemTiers.BRONZE,
                    2.0F,
                    5.0F,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(ItemTiers.BRONZE,
                    2.0f,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(ItemTiers.BRONZE,
                    3,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ItemTiers.BRONZE,
                    3,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(ItemTiers.BRONZE,
            		1,
                    5.0f,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab)));

    //BLOCKS
    //TIN
    public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore",
            () -> new BlockItem(TCBlocks.TIN_ORE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_material_tab)));
    public static final RegistryObject<Item> TIN_BLOCK = ITEMS.register("tin_block",
            () -> new BlockItem(TCBlocks.TIN_BLOCK.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_material_tab)));
    //BRONZE
    public static final RegistryObject<Item> BRONZE_BLOCK = ITEMS.register("bronze_block",
            () -> new BlockItem(TCBlocks.BRONZE_BLOCK.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_material_tab)));

    //ARMOR
    //COPPER
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlot.HEAD,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlot.CHEST,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlot.LEGS,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlot.FEET,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    //TIN
    public static final RegistryObject<Item> TIN_HELMET = ITEMS.register("tin_helmet",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlot.HEAD,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlot.CHEST,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> TIN_LEGGINGS = ITEMS.register("tin_leggings",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlot.LEGS,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> TIN_BOOTS = ITEMS.register("tin_boots",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlot.FEET,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    //BRONZE
    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.CHEST,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.LEGS,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.FEET,
                    new Item.Properties().tab(TCCreativeTabs.techcraft_armor_tab)));

    //SAFE
    public static final RegistryObject<Item> COPPER_SAFE = ITEMS.register("copper_safe",
            () -> new BlockItem(TCBlocks.COPPER_SAFE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> TIN_SAFE = ITEMS.register("tin_safe",
            () -> new BlockItem(TCBlocks.TIN_SAFE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> BRONZE_SAFE = ITEMS.register("bronze_safe",
            () -> new BlockItem(TCBlocks.BRONZE_SAFE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> IRON_SAFE = ITEMS.register("iron_safe",
            () -> new BlockItem(TCBlocks.IRON_SAFE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> GOLD_SAFE = ITEMS.register("gold_safe",
            () -> new BlockItem(TCBlocks.GOLD_SAFE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> TC_BENCH = ITEMS.register("tc_bench",
            () -> new BlockItem(TCBlocks.TC_BENCH.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> TC_FORGE = ITEMS.register("tc_forge",
            () -> new BlockItem(TCBlocks.TC_FORGE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));


    //PLATE
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", MaterialItems::new);
    public static final RegistryObject<Item> TIN_PLATE = ITEMS.register("tin_plate", MaterialItems::new);
    public static final RegistryObject<Item> BRONZE_PLATE = ITEMS.register("bronze_plate", MaterialItems::new);
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", MaterialItems::new);
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", MaterialItems::new);

    //HAMMER
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", CustomToolItem::new);

    public static final RegistryObject<Item> COAL_GENERATOR = ITEMS.register("coal_generator",
            () -> new BlockItem(TCBlocks.COAL_GENERATOR.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));

    public static final RegistryObject<Item> ELECTRIC_FURNACE = ITEMS.register("electric_furnace",
            () -> new BlockItem(TCBlocks.ELECTRIC_FURNACE.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));

    public static final RegistryObject<Item> HEATING_ELEMENT = ITEMS.register("heating_element",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));

    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> TIN_WIRE = ITEMS.register("tin_wire",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> BRONZE_WIRE = ITEMS.register("bronze_wire",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> IRON_WIRE = ITEMS.register("iron_wire",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> GOLD_WIRE = ITEMS.register("gold_wire",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));

    public static final RegistryObject<Item> WIRE_SHAPER = ITEMS.register("wire_shaper",
            () -> new BlockItem(TCBlocks.WIRE_SHAPER.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> ALLOY_PLANT = ITEMS.register("alloy_plant",
            () -> new BlockItem(TCBlocks.ALLOY_PLANT.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));

    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> TIN_DUST = ITEMS.register("tin_dust",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> BRONZE_DUST = ITEMS.register("bronze_dust",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust",
            () -> new Item(new Item.Properties().tab(TCCreativeTabs.techcraft_components_tab)));

    public static final RegistryObject<Item> ELECTRIC_CRUSHER = ITEMS.register("electric_crusher",
            () -> new BlockItem(TCBlocks.ELECTRIC_CRUSHER.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_mechanism_tab)));

    public static final RegistryObject<Item> ENERGY_PIPE_TIER_1 = ITEMS.register("energy_pipe_tier_1",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_1.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
    public static final RegistryObject<Item> ENERGY_PIPE_TIER_2 = ITEMS.register("energy_pipe_tier_2",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_2.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
    public static final RegistryObject<Item> ENERGY_PIPE_TIER_3 = ITEMS.register("energy_pipe_tier_3",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_3.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
    public static final RegistryObject<Item> ENERGY_PIPE_TIER_4 = ITEMS.register("energy_pipe_tier_4",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_4.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
    public static final RegistryObject<Item> ENERGY_PIPE_TIER_5 = ITEMS.register("energy_pipe_tier_5",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_5.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
    public static final RegistryObject<Item> ENERGY_PIPE_TIER_6 = ITEMS.register("energy_pipe_tier_6",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_6.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
    public static final RegistryObject<Item> ENERGY_PIPE_TIER_7 = ITEMS.register("energy_pipe_tier_7",
            () -> new BlockItem(TCBlocks.ENERGY_PIPE_TIER_7.get(),
                    new Item.Properties().tab(TCCreativeTabs.techcraft_transport_tab)));
}
