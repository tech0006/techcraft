package com.tech0006.techcraft.init;

import com.tech0006.techcraft.init.Item.ArmorMaterials;
import com.tech0006.techcraft.init.Item.CustomToolItem;
import com.tech0006.techcraft.init.Item.ItemTiers;
import com.tech0006.techcraft.init.Item.MaterialItems;
import com.tech0006.techcraft.techcraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, techcraft.MOD_ID);


    //COPPER
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", MaterialItems::new);
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
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ItemTiers.COPPER,
                    1.5f,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ItemTiers.COPPER,
                    2,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ItemTiers.COPPER,
                    2,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ItemTiers.COPPER,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    //TIN
    public static final RegistryObject<Item> TIN_AXE = ITEMS.register("tin_axe",
            () -> new AxeItem(ItemTiers.TIN,
                    1.0F,
                    5.0F,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_SHOVEL = ITEMS.register("tin_shovel",
            () -> new ShovelItem(ItemTiers.TIN,
                    1.5f,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword",
            () -> new SwordItem(ItemTiers.TIN,
                    2,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe",
            () -> new PickaxeItem(ItemTiers.TIN,
                    2,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe",
            () -> new HoeItem(ItemTiers.TIN,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    //BRONZE
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(ItemTiers.BRONZE,
                    2.0F,
                    5.0F,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(ItemTiers.BRONZE,
                    2.0f,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(ItemTiers.BRONZE,
                    3,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ItemTiers.BRONZE,
                    3,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(ItemTiers.BRONZE,
                    5.0f,
                    new Item.Properties().group(Groups.techcraft_tool_tab)));

    //BLOCKS
    //COPPER
    public static final RegistryObject<Item> COPPER_ORE = ITEMS.register("copper_ore",
            () -> new BlockItem(Blocks.COPPER_ORE.get(),
                    new Item.Properties().group(Groups.techcraft_material_tab)));
    public static final RegistryObject<Item> COPPER_BLOCK = ITEMS.register("copper_block",
            () -> new BlockItem(Blocks.COPPER_BLOCK.get(),
                    new Item.Properties().group(Groups.techcraft_material_tab)));
    //TIN
    public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore",
            () -> new BlockItem(Blocks.TIN_ORE.get(),
                    new Item.Properties().group(Groups.techcraft_material_tab)));
    public static final RegistryObject<Item> TIN_BLOCK = ITEMS.register("tin_block",
            () -> new BlockItem(Blocks.TIN_BLOCK.get(),
                    new Item.Properties().group(Groups.techcraft_material_tab)));
    //BRONZE
    public static final RegistryObject<Item> BRONZE_BLOCK = ITEMS.register("bronze_block",
            () -> new BlockItem(Blocks.BRONZE_BLOCK.get(),
                    new Item.Properties().group(Groups.techcraft_material_tab)));

    //ARMOR
    //COPPER
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlotType.HEAD,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlotType.CHEST,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlotType.LEGS,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ArmorMaterials.COPPER, EquipmentSlotType.FEET,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    //TIN
    public static final RegistryObject<Item> TIN_HELMET = ITEMS.register("tin_helmet",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlotType.HEAD,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlotType.CHEST,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> TIN_LEGGINGS = ITEMS.register("tin_leggings",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlotType.LEGS,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> TIN_BOOTS = ITEMS.register("tin_boots",
            () -> new ArmorItem(ArmorMaterials.TIN, EquipmentSlotType.FEET,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    //BRONZE
    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlotType.HEAD,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlotType.CHEST,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlotType.LEGS,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlotType.FEET,
                    new Item.Properties().group(Groups.techcraft_armor_tab)));

    //SAFE
    public static final RegistryObject<Item> COPPER_SAFE = ITEMS.register("copper_safe",
            () -> new BlockItem(Blocks.COPPER_SAFE.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> TIN_SAFE = ITEMS.register("tin_safe",
            () -> new BlockItem(Blocks.TIN_SAFE.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> BRONZE_SAFE = ITEMS.register("bronze_safe",
            () -> new BlockItem(Blocks.BRONZE_SAFE.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> IRON_SAFE = ITEMS.register("iron_safe",
            () -> new BlockItem(Blocks.IRON_SAFE.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> GOLD_SAFE = ITEMS.register("gold_safe",
            () -> new BlockItem(Blocks.GOLD_SAFE.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> TC_BENCH = ITEMS.register("tc_bench",
            () -> new BlockItem(Blocks.TC_BENCH.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
    public static final RegistryObject<Item> TC_FORGE = ITEMS.register("tc_forge",
            () -> new BlockItem(Blocks.TC_FORGE.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));


    //PLATE
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", MaterialItems::new);
    public static final RegistryObject<Item> TIN_PLATE = ITEMS.register("tin_plate", MaterialItems::new);
    public static final RegistryObject<Item> BRONZE_PLATE = ITEMS.register("bronze_plate", MaterialItems::new);
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", MaterialItems::new);
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", MaterialItems::new);

    //HAMMER
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", CustomToolItem::new);

    public static final RegistryObject<Item> COAL_GENERATOR = ITEMS.register("coal_generator",
            () -> new BlockItem(Blocks.COAL_GENERATOR.get(),
                    new Item.Properties().group(Groups.techcraft_mechanism_tab)));
}
