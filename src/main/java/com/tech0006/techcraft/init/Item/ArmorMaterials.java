package com.tech0006.techcraft.init.Item;


import com.tech0006.techcraft.init.Items;
import com.tech0006.techcraft.techcraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public enum ArmorMaterials implements IArmorMaterial {

    COPPER("copper", 12, new int[]{1, 2, 3, 1}, 9, Items.COPPER_INGOT.get(),
            "item.armor.equip_generic", 0.0f, 0.1f),
    TIN("tin", 11, new int[]{1, 1, 2, 1}, 9, Items.TIN_INGOT.get(),
            "item.armor.equip_generic", 0.0f, 0.01f),
    BRONZE("bronze", 12, new int[]{2, 3, 4, 2}, 9, Items.BRONZE_INGOT.get(),
            "item.armor.equip_generic", 0.0f, 0.1f);

    private static final int[] max_damage_array = new int[]{13, 15, 16, 11};
    private String name, equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmounts;
    private float toughness;
    float knockbackResistance;

    private ArmorMaterials(String name, int durability, int[] damageReductionAmounts, int enchantability, 
    		Item repairItem, String equipSound, float toughness, float knockbackResistance) {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmounts = damageReductionAmounts;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public String getName() {
        return techcraft.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

	@Override
	public int getDurabilityForSlot(EquipmentSlotType slot) {
		return max_damage_array[slot.getIndex()] * this.durability;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType slot) {
		return this.damageReductionAmounts[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return new SoundEvent(new ResourceLocation(equipSound));
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(this.repairItem);
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
