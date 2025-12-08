package org.strivingtoadoniss.palegarden.spread;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import org.strivingtoadoniss.palegarden.worldgen.CometGorgeGenerator;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;

public class InfectionStarter {

    private static BlockPos cometImpactSite = null;
    private static final int IMPACT_EXCLUSION_RADIUS = 6;

    private InfectionStarter() {}

    public static void startInfection(ServerWorld serverWorld, BlockPos center, int radius) {
        Random random = new Random();
        int x = center.getX() + random.nextInt(radius * 2) - radius;
        int z = center.getZ() + random.nextInt(radius * 2) - radius;

        BlockPos surfacePos = findSurfaceBelow(serverWorld, new BlockPos(x, center.getY(), z));

        if (surfacePos != null) {
            cometImpactSite = surfacePos;

            BlockPos chamberCenter = CometGorgeGenerator.generateGorge(serverWorld, surfacePos, serverWorld.getRandom());

            InfectionCursor chamberCursor = new InfectionCursor(serverWorld, chamberCenter, true);
            InfectionCursor surfaceCursor = new InfectionCursor(serverWorld, surfacePos);
            LabyrinthGenerator.setWorld(serverWorld);
            InfectionSystem.getInstance().addInfectionCursor(chamberCursor);
            InfectionSystem.getInstance().addInfectionCursor(surfaceCursor);
        }
    }

    public static boolean isInImpactZone(BlockPos pos) {
        if (cometImpactSite == null) {
            return false;
        }

        double distance = Math.sqrt(
            Math.pow(pos.getX() - cometImpactSite.getX(), 2) +
                Math.pow(pos.getZ() - cometImpactSite.getZ(), 2)
        );

        return distance <= IMPACT_EXCLUSION_RADIUS;
    }

    private static BlockPos findSurfaceBelow(ServerWorld world, BlockPos start) {
        BlockPos.Mutable pos = start.mutableCopy();

        for (int i = 0; i < 385; i++) {
            pos.move(0, -1, 0);

            if (isSurfaceBlock(world, pos)) {
                return pos;
            }
        }

        return null;
    }

    private static boolean isSurfaceBlock(ServerWorld world, BlockPos pos) {
        return world.getBlockState(pos).isSolidBlock(world, pos) &&
               !world.getBlockState(pos.up()).isSolidBlock(world, pos.up());
    }
}
