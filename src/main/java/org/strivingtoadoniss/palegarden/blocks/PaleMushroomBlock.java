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
import net.minecraft.world.World;

public class PaleMushroomBlock extends PlantBlock {
    public static final IntProperty AMOUNT = Properties.PICKLES;

    protected static final VoxelShape ONE_MUSHROOM_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    protected static final VoxelShape TWO_MUSHROOMS_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    protected static final VoxelShape THREE_MUSHROOMS_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
    protected static final VoxelShape FOUR_MUSHROOMS_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);

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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}

