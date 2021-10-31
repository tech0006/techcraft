package com.tech0006.techcraft.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

public class Groups {

    public static ItemGroup createGroup(String id, RegistryObject<Item> logo) {
        return new ItemGroup(id) {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(logo.get());
			}
        };
    }

    public static ItemGroup techcraft_material_tab = createGroup("techcraft_materials_tab", Items.COPPER_INGOT);
    public static ItemGroup techcraft_tool_tab = createGroup("techcraft_tool_tab", Items.COPPER_AXE);
    public static ItemGroup techcraft_armor_tab = createGroup("techcraft_armor_tab", Items.COPPER_HELMET);
    public static ItemGroup techcraft_mechanism_tab = createGroup("techcraft_mechanism_tab", Items.COPPER_SAFE);
    public static ItemGroup techcraft_components_tab = createGroup("techcraft_components_tab", Items.HEATING_ELEMENT);
    public static ItemGroup techcraft_transport_tab = createGroup("techcraft_transport_tab", Items.ENERGY_PIPE_TIER_7);
}
