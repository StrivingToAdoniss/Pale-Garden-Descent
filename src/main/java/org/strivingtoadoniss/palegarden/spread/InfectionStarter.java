package org.strivingtoadoniss.palegarden.spread;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;

public class InfectionStarter {

    private InfectionStarter() {}

    public static void startInfection(ServerWorld serverWorld, BlockPos center, int radius) {
        Random random = new Random();
        int x = center.getX() + random.nextInt(radius * 2) - radius;
        int z = center.getZ() + random.nextInt(radius * 2) - radius;

        BlockPos spawnPos = findSurfaceBelow(serverWorld, new BlockPos(x, center.getY(), z));

        if (spawnPos != null) {
            serverWorld.setBlockState(spawnPos,
                random.nextBoolean() ? ModBlocks.PALE_STONE.getDefaultState()
                    : ModBlocks.PALE_ROOTED_STONE.getDefaultState());
            InfectionCursor cursor = new InfectionCursor(serverWorld, spawnPos);
            LabyrinthGenerator.setWorld(serverWorld);
            InfectionSystem.getInstance().addCursor(cursor);
        }
    }

    private static BlockPos findSurfaceBelow(ServerWorld world, BlockPos start) {
        BlockPos.Mutable pos = start.mutableCopy();

        for (int i = 0; i < 385; i++) {
            pos.move(0, -1, 0);

            if (world.getBlockState(pos).isSolid() && !world.getBlockState(pos.up()).isSolid()) {
                return pos;
            }
        }

        return null;
    }
}
