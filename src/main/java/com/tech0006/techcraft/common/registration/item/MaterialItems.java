package com.tech0006.techcraft.common.registration.item;

import com.tech0006.techcraft.common.registration.TCCreativeTabs;
import net.minecraft.item.Item;

public class MaterialItems extends Item {

    public MaterialItems() {
        super(new Item.Properties().tab(TCCreativeTabs.techcraft_material_tab));
    }

}
