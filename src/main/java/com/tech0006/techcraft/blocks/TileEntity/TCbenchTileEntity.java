package com.tech0006.techcraft.blocks.TileEntity;

import com.tech0006.techcraft.GUI.Container.TCbenchContainer;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.init.RecipeSerializerInit;
import com.tech0006.techcraft.recipes.tc_bench.TCbenchRecipe;
import com.tech0006.techcraft.techcraft;
import com.tech0006.techcraft.util.handler.TCbenchItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class TCbenchTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private ITextComponent customName;
    private TCbenchItemHandler inventory;

    public TCbenchTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

        this.inventory = new TCbenchItemHandler(37);
    }

    public TCbenchTileEntity() {
        this(ModTileEntityTypes.TC_BENCH.get());
    }

    @Override
    public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new TCbenchContainer(windowID, playerInv, this);
    }

    @Override
    public void tick() {

        if (this.world != null && !this.world.isRemote) {
            if (this.getRecipe() != null) {
                ItemStack output = this.getRecipe().getRecipeOutput();
                output.setCount(1);
                this.inventory.setStackInSlot(36, output);
            } else {
                this.inventory.setStackInSlot(36, ItemStack.EMPTY);
            }
        }
    }


    public void setCustomName(ITextComponent name) {
        this.customName = name;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + techcraft.MOD_ID + ".tc_bench");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }

        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }

        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());

        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT nbt) {
        this.read(nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }


    @Nullable
    public TCbenchRecipe getRecipe() {
        boolean notEmpty = false;
        for (int i = 0; i < 36; i++) {
            if (this.inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                notEmpty = true;
            }
        }
        if (!notEmpty) {
            return null;
        }

        Set<IRecipe<?>> recipes = findRecipesByType(RecipeSerializerInit.TC_BENCH_TYPE, this.world);
        for (IRecipe<?> iRecipe : recipes) {
            TCbenchRecipe recipe = (TCbenchRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
                return recipe;
            }
        }

        return null;
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn) {
        ClientWorld world = Minecraft.getInstance().world;
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> typeIn, World worldIn) {
        Set<ItemStack> inputs = new HashSet<ItemStack>();
        Set<IRecipe<?>> recipes = findRecipesByType(typeIn, worldIn);
        for (IRecipe<?> recipe : recipes) {
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            ingredients.forEach(ingredient -> {
                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    inputs.add(stack);
                }
            });
        }
        return inputs;
    }

}
