package org.strivingtoadoniss.palegarden.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.strivingtoadoniss.palegarden.ModBlocks;

public class PaleMushroomBlock extends PlantBlock {
    public static final IntProperty AMOUNT = Properties.PICKLES;

    protected static final VoxelShape ONE_MUSHROOM_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 3.9, 9.0);
    protected static final VoxelShape TWO_MUSHROOMS_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 9.5, 5.4, 8.5);
    protected static final VoxelShape THREE_MUSHROOMS_SHAPE = Block.createCuboidShape(5.5, 0.0, 5.5, 11.0, 5.4, 10.5d);
    protected static final VoxelShape FOUR_MUSHROOMS_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.5, 10.5, 5.4, 11.0);

    public static final MapCodec<PaleMushroomBlock> CODEC = createCodec(PaleMushroomBlock::new);

    public PaleMushroomBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AMOUNT, 1));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AMOUNT);

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AMOUNT)) {
            case 2 -> TWO_MUSHROOMS_SHAPE;
            case 3 -> THREE_MUSHROOMS_SHAPE;
            case 4 -> FOUR_MUSHROOMS_SHAPE;
            default -> ONE_MUSHROOM_SHAPE;
        };
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() 
            && context.getStack().getItem() == this.asItem()
            && state.get(AMOUNT) < 4 
            || super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            int currentAmount = blockState.get(AMOUNT);
            if (currentAmount < 4) {
                return blockState.with(AMOUNT, currentAmount + 1);
            }
        }
        return super.getPlacementState(ctx);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.PALE_STONE) 
            || floor.isOf(ModBlocks.PALE_ROOTED_STONE)
            || super.canPlantOnTop(floor, world, pos);
    }
}
