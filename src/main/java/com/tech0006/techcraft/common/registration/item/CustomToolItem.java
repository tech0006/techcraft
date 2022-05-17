package com.tech0006.techcraft.common.registration.item;

import com.tech0006.techcraft.common.registration.TCCreativeTabs;
import net.minecraft.world.item.Item;

public class CustomToolItem extends Item {
    public CustomToolItem() {
        super(new Item.Properties().tab(TCCreativeTabs.techcraft_tool_tab));
    }
}
