package com.tech0006.techcraft.common.registration.item;


import com.tech0006.techcraft.common.registration.TCItems;
import com.tech0006.techcraft.techcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;


public enum ArmorMaterials implements ArmorMaterial {

    COPPER("copper", 12, new int[]{1, 2, 3, 1}, 9, Items.COPPER_INGOT,
            "item.armor.equip_generic", 0.0f, 0.1f),
    TIN("tin", 11, new int[]{1, 1, 2, 1}, 9, TCItems.TIN_INGOT.get(),
            "item.armor.equip_generic", 0.0f, 0.01f),
    BRONZE("bronze", 12, new int[]{2, 3, 4, 2}, 9, TCItems.BRONZE_INGOT.get(),
            "item.armor.equip_generic", 0.0f, 0.1f);

    private static final int[] max_damage_array = new int[]{13, 15, 16, 11};
    private final String name, equipSound;
    private final int durability, enchantability;
    private final Item repairItem;
    private final int[] damageReductionAmounts;
    private final float toughness;
    float knockbackResistance;

    ArmorMaterials(String name, int durability, int[] damageReductionAmounts, int enchantability,
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
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return max_damage_array[slot.getIndex()] * this.durability;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
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
