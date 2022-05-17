package com.tech0006.techcraft.blocks.blockentity.functional;

import com.tech0006.techcraft.blocks.blockentity.base.LavaFluidTile;
import com.tech0006.techcraft.blocks.blockentity.update.UpdateAlloyPlant;
import com.tech0006.techcraft.blocks.functional.AlloyPlant;
import com.tech0006.techcraft.common.packet.PacketHandler;
import com.tech0006.techcraft.common.registration.TCBlockEntityTypes;
import com.tech0006.techcraft.common.registration.TCRecipeSerializerInit;
import com.tech0006.techcraft.recipes.alloy_plant.AlloyPlantRecipe;
import com.tech0006.techcraft.util.tools.inventory.TCItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class AlloyPlantBlockEntity extends LavaFluidTile  {

    private Component customName;
    private final TCItemHandler inventory;

    public int processTime = 0, processTimeTotal = 0;

    public AlloyPlantBlockEntity(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);

        this.inventory = new TCItemHandler(5);
    }

    public AlloyPlantBlockEntity(BlockPos pos, BlockState state) {
        this(TCBlockEntityTypes.ALLOY_PLANT.get(), pos, state);
    }

    public void tick(BlockState state) {

        if (level != null && !level.isClientSide) {
            if (processTime > 0) {
                if (getFluid() > 0) {
                    decrFluid(1);
                    processTime--;
                    if (processTime == 0) {
                        processTimeTotal = 0;
                        inventory.insertItem(3, inventory.getStackInSlot(4).copy(), false);
                        inventory.setStackInSlot(4, ItemStack.EMPTY);
                        level.setBlockAndUpdate(getBlockPos(), state.setValue(AlloyPlant.LIT, false));
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
                        processTimeTotal = processTime = 200;

                        if (!recipe.getIngredients().get(0).isEmpty())
                            inventory.decrStackSize(0, recipe.getIngredients().get(0).getItems()[0].getCount());

                        if (!recipe.getIngredients().get(1).isEmpty())
                            inventory.decrStackSize(1, recipe.getIngredients().get(1).getItems()[0].getCount());

                        if (!recipe.getIngredients().get(2).isEmpty())
                            inventory.decrStackSize(2, recipe.getIngredients().get(2).getItems()[0].getCount());

                        inventory.setStackInSlot(4, recipe.getResultItem().copy());
                        level.setBlockAndUpdate(getBlockPos(), state.setValue(AlloyPlant.LIT, true));
                    }
                }
            }
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateAlloyPlant(getBlockPos(), tank.getFluidAmount(), processTime, processTimeTotal));
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

    public void decrFluid(int count) {
        int curr = this.tank.getFluid().getAmount();
        if (curr - count < 0) {
            setFluid(0);
            return;
        }
        setFluid(curr - count);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("CustomName")) {
            this.customName = TextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }

        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);

        processTime = compound.getInt("CurrBurnTime");
        processTimeTotal = compound.getInt("SumBurnTime");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        if (this.customName != null) {
            compound.putString("CustomName", TextComponent.Serializer.toJson(this.customName));
        }

        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());

        compound.putInt("CurrBurnTime", processTime);
        compound.putInt("SumBurnTime", processTimeTotal);

        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.load(nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)

            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
        return super.getCapability(cap, side);
    }

    public AlloyPlantRecipe getRecipe() {
        Set<Recipe<?>> recipes = findRecipesByType(TCRecipeSerializerInit.ALLOY_PLANT_TYPE, this.level);
        for (Recipe<?> iRecipe : recipes) {
            AlloyPlantRecipe recipe = (AlloyPlantRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(this.inventory), this.level)) {
                return recipe;
            }
        }

        return null;
    }

    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn, Level world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn) {
        ClientLevel world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

}

