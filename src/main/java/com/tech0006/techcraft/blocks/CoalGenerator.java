package com.tech0006.techcraft.blocks;

import com.tech0006.techcraft.blocks.TileEntity.CoalGeneratorTileEntity;
import com.tech0006.techcraft.blocks.base.FacedBlock;
import com.tech0006.techcraft.init.ModTileEntityTypes;
import com.tech0006.techcraft.util.Tooltip;
import com.tech0006.techcraft.util.handler.CoalGeneratorItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
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

public class CoalGenerator extends FacedBlock {

    private static final ResourceLocation WRENCH = new ResourceLocation("forge", "wrench");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public CoalGenerator(Properties properties) {
        super(properties);

        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(LIT);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            if (player.isCrouching()) {
                if (player.getHeldItemMainhand().getItem().getTags().contains(WRENCH)) {
                    dismantleBlock(worldIn, pos);
                    return ActionResultType.SUCCESS;
                }
            }

            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    private void dismantleBlock(World worldIn, BlockPos pos) {
        ItemStack itemStack = new ItemStack(this);

        CoalGeneratorTileEntity localTileEntity = (CoalGeneratorTileEntity) worldIn.getTileEntity(pos);
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

        entityItem.setMotion(0, entityItem.getYOffset(), 0);
        worldIn.addEntity(entityItem);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.removeBlock(pos, false);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CoalGeneratorTileEntity(ModTileEntityTypes.COAL_GENERATOR.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
        int energy = 0;
        int maxEn = 0;
        int gen = 0;

        int curr = 0;
        int sum = 0;
        if (compoundnbt != null) {
            if (compoundnbt.contains("energy"))
                energy = compoundnbt.getCompound("energy").getInt("value");
            if (compoundnbt.contains("maxEn"))
                maxEn = compoundnbt.getCompound("maxEn").getInt("value");
            if (compoundnbt.contains("gen"))
                gen = compoundnbt.getCompound("gen").getInt("value");
            if (compoundnbt.contains("CurrBurnTime"))
                curr = compoundnbt.getCompound("CurrBurnTime").getInt("value");
            if (compoundnbt.contains("SumBurnTime"))
                sum = compoundnbt.getCompound("SumBurnTime").getInt("value");
        }
        Tooltip.showInfoCtrlCoalGenerator(energy, maxEn, gen, curr, sum, tooltip);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof CoalGeneratorTileEntity && state.getBlock() != newState.getBlock()) {
            CoalGeneratorTileEntity furnace = (CoalGeneratorTileEntity) tile;
            ((CoalGeneratorItemHandler) furnace.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addEntity(itemEntity);
            });
        }

        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeTileEntity(pos);
        }
    }

}
