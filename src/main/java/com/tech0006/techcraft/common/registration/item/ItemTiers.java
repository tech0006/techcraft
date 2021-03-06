package com.tech0006.techcraft.common.registration.item;

import com.tech0006.techcraft.common.registration.TCItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ItemTiers implements Tier {
    COPPER(1, 350, 3.5F, 2.3F, 12, () -> {
        return Ingredient.of(Items.COPPER_INGOT);
    }),
    TIN(1, 400, 3.5f, 2.3f, 12, () ->
    {
        return Ingredient.of(TCItems.TIN_INGOT.get());
    }),
    BRONZE(2, 450, 4.5f, 3.0f, 12, () -> {
        return Ingredient.of(TCItems.BRONZE_INGOT.get());
    });


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ItemTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
    }
    

	@Override
	public int getUses() {
		return this.maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

    @Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}
}