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
import net.minecraft.util.math.random.Random;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.blocks.PaleMushroomBlock;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;
import org.strivingtoadoniss.palegarden.worldgen.PaleOakTreeGenerator;

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
            trySpawnMushroom(pos);
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

        return state.isSolid()
            && !state.isOf(ModBlocks.PALE_MUSHROOM)
            && !state.isOf(ModBlocks.PALE_STONE)
            && !state.isOf(ModBlocks.PALE_ROOTED_STONE)
            && !state.isOf(Blocks.PALE_OAK_LOG)
            && !state.isOf(Blocks.PALE_OAK_LEAVES)
            && !state.isOf(Blocks.PALE_HANGING_MOSS)
            && !state.isOf(Blocks.MOSS_CARPET)
            && !state.isOf(Blocks.HANGING_ROOTS)
            && !state.isOf(Blocks.AIR)
            && !state.isOf(Blocks.CAVE_AIR)
            && !state.isOf(Blocks.VOID_AIR);
    }

    private void infectBlock(BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockState aboveState = world.getBlockState(pos.up());

        if (shouldConvertToGrass(state, aboveState)) {
            convertToGrassBlock(pos);
        } else {
            convertToPaleStone(pos);
        }
    }

    private boolean shouldConvertToGrass(BlockState state, BlockState aboveState) {
        return !aboveState.isSolid() && state.isSolid();
    }

    private void convertToGrassBlock(BlockPos pos) {
        world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
    }

    private void convertToPaleStone(BlockPos pos) {
        world.setBlockState(pos,
            world.getRandom().nextBoolean() ? ModBlocks.PALE_STONE.getDefaultState()
                : ModBlocks.PALE_ROOTED_STONE.getDefaultState());
    }

    private void placeLabyrinthWall(BlockPos pos) {
        if (shouldSkipLabyrinthWall(pos)) {
            return;
        }

        ensureLabyrinthGenerated(pos);

        if (LabyrinthGenerator.shouldPlaceWallAt(pos)) {
            buildTreeWall(pos);
        }
    }

    private boolean shouldSkipLabyrinthWall(BlockPos pos) {
        return world.getBlockState(pos.up()).isSolid();
    }

    private void ensureLabyrinthGenerated(BlockPos pos) {
        ChunkPos chunkPos = world.getChunk(pos).getPos();
        LabyrinthGenerator.generateLabyrinthChunkIfNotInfected(chunkPos);
    }

    private void buildTreeWall(BlockPos pos) {
        if (!PaleOakTreeGenerator.generateTreeAt(world, pos.up())) {
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

    private void trySpawnMushroom(BlockPos pos) {
        BlockPos mushroomPos = pos.up();

        if (canPlaceMushroom(mushroomPos) && shouldSpawnMushroom()) {
            placeMushroom(mushroomPos);
        }
    }

    private boolean canPlaceMushroom(BlockPos mushroomPos) {
        return world.getBlockState(mushroomPos).isAir();
    }

    private boolean shouldSpawnMushroom() {
        return world.getRandom().nextFloat() <= 0.05f;
    }

    private void placeMushroom(BlockPos mushroomPos) {
        Random random = world.getRandom();
        int amount = getWeightedMushroomAmount(random);

        BlockState mushroomState = ModBlocks.PALE_MUSHROOM.getDefaultState()
            .with(PaleMushroomBlock.AMOUNT, amount);
        world.setBlockState(mushroomPos, mushroomState);
    }

    private int getWeightedMushroomAmount(Random random) {
        float roll = random.nextFloat();

        if (roll < 0.5f) {
            return 1;
        } else if (roll < 0.78f) {
            return 2;
        } else if (roll < 0.88f) {
            return 3;
        } else if (roll < 0.95f) {
            return 4;
        } else {
            return 5;
        }
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
