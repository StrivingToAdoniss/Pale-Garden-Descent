package org.strivingtoadoniss.palegarden.spread;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;

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

        return !state.isOf(Blocks.PALE_MOSS_BLOCK) && !state.isOf(Blocks.AIR);
    }

    private void infectBlock(BlockPos pos) {
        world.setBlockState(pos, Blocks.PALE_MOSS_BLOCK.getDefaultState());

        world.spawnParticles(ParticleTypes.SCULK_CHARGE_POP,
            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
            5, 0.2, 0.2, 0.2, 0.01);
    }

    private void addNeighborsToQueue(BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.offset(direction);

            if (!visited.contains(neighbor) && world.isInBuildLimit(neighbor) && canInfect(neighbor)) {
                searchQueue.add(neighbor);
                visited.add(pos);
            }
        }
    }
}
