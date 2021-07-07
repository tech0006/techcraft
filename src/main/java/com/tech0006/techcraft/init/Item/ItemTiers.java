package com.tech0006.techcraft.init.Item;

import com.tech0006.techcraft.init.Items;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ItemTiers implements IItemTier {
    COPPER(1, 350, 3.5F, 2.3F, 12, () -> {
        return Ingredient.fromItems(Items.COPPER_INGOT.get());
    }),
    TIN(1, 400, 3.5f, 2.3f, 12, () ->
    {
        return Ingredient.fromItems(Items.TIN_INGOT.get());
    }),
    BRONZE(2, 450, 4.5f, 3.0f, 12, () -> {
        return Ingredient.fromItems(Items.BRONZE_INGOT.get());
    });


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    ItemTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}