package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PaleGardenEvents {

    private PaleGardenEvents() {}

    public static void initialize() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (state.isOf(Blocks.PALE_OAK_LOG)) {
                handlePaleLogBreak(world, pos, state);
            }
        });
    }

    private static void handlePaleLogBreak(World world, BlockPos pos, BlockState state) {
        if (world.isClient) return;

        Direction.Axis axis = state.get(PillarBlock.AXIS);
        world.setBlockState(pos, ModBlocks.REGENERATING_PALE_LOG.getDefaultState().with(PillarBlock.AXIS, axis));
    }
}
