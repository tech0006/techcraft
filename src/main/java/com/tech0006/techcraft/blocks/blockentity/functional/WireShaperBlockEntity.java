package com.tech0006.techcraft.blocks.blockentity.functional;

import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.common.registration.TCRecipeSerializerInit;
import com.tech0006.techcraft.recipes.wire_shaper.WireShaperRecipe;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class WireShaperBlockEntity extends BlockEntity {

    private final TCItemHandler inventory;

    public WireShaperBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);

        this.inventory = new TCItemHandler(2);
    }

    public WireShaperBlockEntity(BlockPos pos, BlockState state) {
        this(TCBlockEntityTypes.WIRE_SHAPER.get(), pos, state);
    }

    public void tick(BlockState state) {

        if (this.level != null && !this.level.isClientSide) {
            if (this.getRecipe() != null) {
                ItemStack output = this.getRecipe().getResultItem();
                output.setCount(1);
                this.inventory.setStackInSlot(1, output.copy());
            } else {
                this.inventory.setStackInSlot(1, ItemStack.EMPTY);
            }
        }
    }
    
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());

        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag Tag = new CompoundTag();
        this.save(Tag);
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, Tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
    	this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag Tag = new CompoundTag();
        this.save(Tag);
        return Tag;
    }

    @Override
    public void deserializeNBT(CompoundTag Tag) {
        this.load(Tag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    public WireShaperRecipe getRecipe() {
        Set<Recipe<?>> recipes = findRecipesByType(TCRecipeSerializerInit.WIRE_SHAPER_TYPE, this.level);
        for (Recipe<?> iRecipe : recipes) {
            WireShaperRecipe recipe = (WireShaperRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.level)) {
                return recipe;
            }
        }

        return null;
    }

    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn, Level level) {
        return level != null ? level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn) {
        ClientLevel level = Minecraft.getInstance().level;
        return level != null ? level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }
}

