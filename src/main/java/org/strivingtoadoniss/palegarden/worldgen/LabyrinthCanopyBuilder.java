package org.strivingtoadoniss.palegarden.worldgen;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.Heightmap;

public final class LabyrinthCanopyBuilder {

    private static final Set<ChunkPos> generatedCanopies = new HashSet<>();
    private static final int CANOPY_EXTRA_OFFSET = 1;
    private static final int IMPACT_EXTRA_RADIUS = 1;

    private LabyrinthCanopyBuilder() {}

    public static void buildCanopyForChunk(ServerWorld world, ChunkPos chunkPos) {
        if (generatedCanopies.contains(chunkPos)) {
            return;
        }
        generatedCanopies.add(chunkPos);

        BlockState canopyState = Blocks.PALE_OAK_LEAVES.getDefaultState()
            .with(LeavesBlock.PERSISTENT, false)
            .with(LeavesBlock.DISTANCE, 1);

        int wallHeight = LabyrinthGenerator.getWallHeight();
        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                int worldX = startX + dx;
                int worldZ = startZ + dz;
                int surfaceY = world.getTopY(Heightmap.Type.WORLD_SURFACE, worldX, worldZ);
                int canopyY = surfaceY + wallHeight + CANOPY_EXTRA_OFFSET;
                placeLeafIfPossible(world, new BlockPos(worldX, canopyY, worldZ), canopyState);
                placeLeafIfPossible(world, new BlockPos(worldX, canopyY - 1, worldZ), canopyState);
            }
        }
    }

    private static void placeLeafIfPossible(ServerWorld world, BlockPos pos, BlockState canopyState) {
        if (!world.isInBuildLimit(pos)) {
            return;
        }
        BlockState current = world.getBlockState(pos);
        if (current.isAir()
            || current.isIn(BlockTags.LEAVES) || current.isOf(Blocks.PALE_OAK_LEAVES)) {
            world.setBlockState(pos, canopyState, Block.NOTIFY_LISTENERS);
        }
    }

    public static void buildImpactCanopy(ServerWorld world, BlockPos center, int exclusionRadius) {
        int radius = exclusionRadius + IMPACT_EXTRA_RADIUS;
        int canopyY = center.getY() + LabyrinthGenerator.getWallHeight();

        BlockState canopyState = Blocks.PALE_OAK_LEAVES.getDefaultState()
            .with(LeavesBlock.PERSISTENT, true)
            .with(LeavesBlock.DISTANCE, 1);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > radius * radius) {
                    continue;
                }
                int x = center.getX() + dx;
                int z = center.getZ() + dz;
                BlockPos topPos = new BlockPos(x, canopyY, z);
                placeLeafIfPossible(world, topPos, canopyState);
                placeLeafIfPossible(world, topPos.down(), canopyState);
            }
        }
    }

    private static int getMaxSurfaceY(ServerWorld world, BlockPos center, int radius) {
        int maxY = Integer.MIN_VALUE;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > radius * radius) {
                    continue;
                }
                int x = center.getX() + dx;
                int z = center.getZ() + dz;
                int surfaceY = world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);
                if (surfaceY > maxY) {
                    maxY = surfaceY;
                }
            }
        }
        return maxY;
    }
}

