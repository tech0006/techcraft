package com.tech0006.techcraft.common.registration;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.RegistryObject;


public class TCCreativeTabs {

    public static CreativeModeTab createGroup(String id, RegistryObject<Item> logo) {
        return new CreativeModeTab(id) {
            @Override
			public ItemStack makeIcon() {
				return new ItemStack(logo.get());
			}
        };
    }

    public static CreativeModeTab techcraft_material_tab = createGroup("techcraft_materials_tab", TCItems.BRONZE_INGOT);
    public static CreativeModeTab techcraft_tool_tab = createGroup("techcraft_tool_tab", TCItems.BRONZE_AXE);
    public static CreativeModeTab techcraft_armor_tab = createGroup("techcraft_armor_tab", TCItems.BRONZE_HELMET);
    public static CreativeModeTab techcraft_mechanism_tab = createGroup("techcraft_mechanism_tab", TCItems.BRONZE_SAFE);
    public static CreativeModeTab techcraft_components_tab = createGroup("techcraft_components_tab", TCItems.HEATING_ELEMENT);
    public static CreativeModeTab techcraft_transport_tab = createGroup("techcraft_transport_tab", TCItems.ENERGY_PIPE_TIER_7);
}
