package com.tech0006.techcraft.blocks.TileEntity;

import com.tech0006.techcraft.GUI.Container.AlloyPlantContainer;
import com.tech0006.techcraft.blocks.AlloyPlant;
import com.tech0006.techcraft.blocks.CoalGenerator;
import com.tech0006.techcraft.blocks.TileEntity.base.LavaFluidTile;
import com.tech0006.techcraft.blocks.TileEntity.update.UpdateAlloyPlant;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.init.RecipeSerializerInit;
import com.tech0006.techcraft.recipes.alloy_plant.AlloyPlantRecipe;
import com.tech0006.techcraft.techcraft;
import com.tech0006.techcraft.util.handler.AlloyPlantItemHandler;
import com.tech0006.techcraft.util.handler.PacketHandler;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AlloyPlantTileEntity extends LavaFluidTile implements INamedContainerProvider {

    private ITextComponent customName;
    private AlloyPlantItemHandler inventory;

    public int processTime = 0, processTimeTotal = 0;

    public AlloyPlantTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

        this.inventory = new AlloyPlantItemHandler(5);
    }

    public AlloyPlantTileEntity() {
        this(ModTileEntityTypes.ALLOY_PLANT.get());
    }

    @Override
    public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new AlloyPlantContainer(windowID, playerInv, this);
    }

    @Override
    public void tick() {

        if (this.world != null && !this.world.isRemote) {
            if (processTime > 0) {
                if (this.getFluid() > 0) {
                    this.decrFluid(1);
                    processTime--;
                    if (processTime == 0) {
                        processTimeTotal = 0;
                        this.inventory.insertItem(3, this.inventory.getStackInSlot(4).copy(), false);
                        this.inventory.setStackInSlot(4, ItemStack.EMPTY);
                        this.world.setBlockState(this.getPos(), this.getBlockState().with(AlloyPlant.LIT, false));
                    }
                } else {
                    if (processTime < processTimeTotal) {
                        processTime++;
                    }
                }
            } else {
                if (getFluid() > 0) {
                    AlloyPlantRecipe recipe = getRecipe();
                    if (recipe != null) {
                        this.processTimeTotal = this.processTime = 200;

                        if (!recipe.getIngredients().get(0).hasNoMatchingItems())
                            this.inventory.decrStackSize(0, recipe.getIngredients().get(0).getMatchingStacks()[0].getCount());

                        if (!recipe.getIngredients().get(1).hasNoMatchingItems())
                            this.inventory.decrStackSize(1, recipe.getIngredients().get(1).getMatchingStacks()[0].getCount());

                        if (!recipe.getIngredients().get(2).hasNoMatchingItems())
                            this.inventory.decrStackSize(2, recipe.getIngredients().get(2).getMatchingStacks()[0].getCount());

                        this.inventory.setStackInSlot(4, recipe.getRecipeOutput().copy());
                        this.world.setBlockState(this.getPos(), this.getBlockState().with(AlloyPlant.LIT, true));
                    }
                }
            }
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateAlloyPlant(getPos(), this.tank.getFluidAmount(), processTime, processTimeTotal));
        }
    }

    public int getFluidMax() {
        return this.tank.getCapacity();
    }

    public int getFluid() {
        return this.tank.getFluid().getAmount();
    }

    public void setFluid(int fluid) {
        if (fluid > 0) {
            FluidStack curr = this.tank.getFluid();
            curr.setAmount(fluid);
            this.tank.setFluid(curr);
        } else {
            this.tank.drain(this.tank.getCapacity(), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public boolean decrFluid(int count) {
        int curr = this.tank.getFluid().getAmount();
        if (curr - count < 0) {
            return false;
        }
        setFluid(curr - count);
        return true;
    }

    public void setCustomName(ITextComponent name) {
        this.customName = name;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + techcraft.MOD_ID + ".alloy_plant");
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

        processTime = compound.getInt("CurrBurnTime");
        processTimeTotal = compound.getInt("SumBurnTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }

        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());

        compound.putInt("CurrBurnTime", processTime);
        compound.putInt("SumBurnTime", processTimeTotal);

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

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)

            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
        return super.getCapability(cap, side);
    }

    @Nullable
    public AlloyPlantRecipe getRecipe() {
        Set<IRecipe<?>> recipes = findRecipesByType(RecipeSerializerInit.ALLOY_PLANT_TYPE, this.world);
        for (IRecipe<?> iRecipe : recipes) {
            AlloyPlantRecipe recipe = (AlloyPlantRecipe) iRecipe;
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

