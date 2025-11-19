package org.strivingtoadoniss.palegarden.spread;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;

import java.util.*;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;

public class InfectionCursor {

    private final ServerWorld world;
    private final BlockPos origin;

    private int currentTransformations = 0;
    private int tickCounter = 0;

    private final Queue<BlockPos> searchQueue = new LinkedList<>();
    private final Set<BlockPos> visited = new HashSet<>();

    @Getter
    private boolean finished = false;

    public InfectionCursor(ServerWorld world, BlockPos origin) {
        this.world = world;
        this.origin = origin;
        this.searchQueue.add(origin);
    }

    public void tick() {
        tickCounter++;
        if (tickCounter < InfectionConfig.TICK_DELAY) {
            return;
        }
        tickCounter = 0;

        for (int i = 0; i < InfectionConfig.BLOCKS_PER_TICK; i++) {
            BlockPos pos = searchQueue.poll();

            infectBlock(pos);
            placeLabyrinthWall(pos);
            spawnInfectionParticles(pos);
            addNeighborsToQueue(pos);

            if (++currentTransformations >= InfectionConfig.MAX_TRANSFORMATIONS
                    || searchQueue.isEmpty()) {
                finished = true;
                return;
            }
        }
    }

    private boolean canInfect(BlockPos pos) {
        if (pos.getManhattanDistance(origin) > InfectionConfig.MAX_INFECTION_RADIUS) {
            return false;
        }

        BlockState state = world.getBlockState(pos);

        return !state.isOf(ModBlocks.PALE_STONE) && !state.isOf(ModBlocks.PALE_ROOTED_STONE)
            && !state.isOf(Blocks.AIR) && !state.isOf(Blocks.PALE_OAK_LOG)
            && !state.isOf(Blocks.CAVE_AIR) && !state.isOf(Blocks.VOID_AIR);
    }

    private void infectBlock(BlockPos pos) {
        world.setBlockState(pos,
            world.getRandom().nextBoolean() ? ModBlocks.PALE_STONE.getDefaultState()
                : ModBlocks.PALE_ROOTED_STONE.getDefaultState());
    }

    private void placeLabyrinthWall(BlockPos pos) {
        ChunkPos chunkPos = world.getChunk(pos).getPos();

        LabyrinthGenerator.generateLabyrinthChunkIfNotInfected(chunkPos);

        if (LabyrinthGenerator.shouldPlaceWallAt(pos)) {
            buildWallColumn(pos);
        }
    }

    private void buildWallColumn(BlockPos pos) {
        int wallHeight = LabyrinthGenerator.getWallHeight();
        for (int dy = 1; dy <= wallHeight; dy++) {
            world.setBlockState(pos.up(dy), Blocks.PALE_OAK_LOG.getDefaultState());
        }
    }

    private void spawnInfectionParticles(BlockPos pos) {
        world.spawnParticles(ParticleTypes.SCULK_CHARGE_POP,
            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
            5, 0.2, 0.2, 0.2, 0.01);
    }

    private void addNeighborsToQueue(BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.offset(direction);

            if (!visited.contains(neighbor) && world.isInBuildLimit(neighbor) && canInfect(neighbor)) {
                searchQueue.add(neighbor);
                visited.add(neighbor);
            }
        }
    }

    public void finishImmediately() {
        finished = true;
    }
}
