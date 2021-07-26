package com.tech0006.techcraft.init.Item;

import com.tech0006.techcraft.init.Groups;
import net.minecraft.item.Item;

public class CustomToolItem extends Item {
    public CustomToolItem() {
        super(new Item.Properties().tab(Groups.techcraft_tool_tab));
    }
}
