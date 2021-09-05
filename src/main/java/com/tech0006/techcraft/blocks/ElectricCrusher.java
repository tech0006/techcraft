package com.tech0006.techcraft.blocks;

import com.tech0006.techcraft.blocks.TileEntity.ElectricCrusherTileEntity;
import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.util.Tooltip;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ElectricCrusher extends FacedBlock {

    private static final ResourceLocation WRENCH = new ResourceLocation("forge", "wrench");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public ElectricCrusher(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide) {
            if (player.isCrouching()) {
                if (player.getMainHandItem().getItem().getTags().contains(WRENCH)) {
                    dismantleBlock(worldIn, pos);
                    return ActionResultType.SUCCESS;
                }
            }

            TileEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    private void dismantleBlock(World worldIn, BlockPos pos) {
        ItemStack itemStack = new ItemStack(this);

        ElectricCrusherTileEntity localTileEntity = (ElectricCrusherTileEntity) worldIn.getBlockEntity(pos);
        int internalEnergy = localTileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
        if (internalEnergy > 0) {
            CompoundNBT energyValue = new CompoundNBT();
            energyValue.putInt("value", internalEnergy);

            CompoundNBT energy = new CompoundNBT();
            energy.put("energy", energyValue);

            CompoundNBT root = new CompoundNBT();
            root.put("BlockEntityTag", energy);
            itemStack.setTag(root);
        }

        worldIn.removeBlock(pos, false);

        ItemEntity entityItem = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);

        entityItem.setDeltaMovement(0, entityItem.getMyRidingOffset(), 0);
        worldIn.addFreshEntity(entityItem);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        /*ItemEntity en = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ((ElectricCrusherTileEntity)world.getTileEntity(pos)).getCurrRecipeInput() );
        world.addEntity(en);*/
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        /*ItemEntity en = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((ElectricCrusherTileEntity)worldIn.getTileEntity(pos)).getCurrRecipeInput() );
        worldIn.addEntity(en);*/
        super.playerDestroy(worldIn, player, pos, state, te, stack);
        worldIn.removeBlock(pos, false);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ElectricCrusherTileEntity(ModTileEntityTypes.ELECTRIC_CRUSHER.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compoundnbt = stack.getTagElement("BlockEntityTag");
        int energy = 0;
        if (compoundnbt != null) {
            if (compoundnbt.contains("energy"))
                energy = compoundnbt.getInt("energy");
        }
        Tooltip.showInfoCtrlElectricCrusher(energy, tooltip);
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof ElectricCrusherTileEntity && state.getBlock() != newState.getBlock()) {
            ElectricCrusherTileEntity crusher = (ElectricCrusherTileEntity) tile;

            ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), crusher.inventory.getStackInSlot(0));
            worldIn.addFreshEntity(itemEntity);

            itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), crusher.inventory.getStackInSlot(1));
            worldIn.addFreshEntity(itemEntity);

            itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), crusher.inventory.getStackInSlot(3));
            worldIn.addFreshEntity(itemEntity);

            /*((ElectricCrusherItemHandler) crusher.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addEntity(itemEntity);

                ItemEntity en = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((ElectricCrusherTileEntity)worldIn.getTileEntity(pos)).getCurrRecipeInput() );
                worldIn.addEntity(en);
            });*/
        }

        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeBlockEntity(pos);
        }
    }

}
