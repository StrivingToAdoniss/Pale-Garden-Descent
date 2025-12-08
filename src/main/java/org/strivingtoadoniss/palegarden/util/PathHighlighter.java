package org.strivingtoadoniss.palegarden.util;

import java.util.Collections;
import java.util.List;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public final class PathHighlighter {

    private static final int TICKS_BETWEEN_PULSES = 10;
    private static final int PARTICLE_COUNT = 8;
    private static final double PARTICLE_SPREAD = 0.05;

    private static List<BlockPos> currentPath = Collections.emptyList();
    private static ServerWorld currentWorld;
    private static int cooldownTicks = 0;

    private PathHighlighter() {}

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(PathHighlighter::tick);
    }

    public static void setPath(ServerWorld world, List<BlockPos> path) {
        currentWorld = world;
        currentPath = List.copyOf(path);
        cooldownTicks = 0;
    }

    public static void clear() {
        currentPath = Collections.emptyList();
        currentWorld = null;
    }

    private static void tick(MinecraftServer server) {
        if (!shouldTick(server) || !isCooldownReady()) {
            return;
        }

        cooldownTicks = TICKS_BETWEEN_PULSES;

        highlightPath();
    }

    private static boolean shouldTick(MinecraftServer server) {
        if (currentWorld == null || currentPath.isEmpty()) {
            return false;
        }
        if (currentWorld.getServer() != server) {
            clear();
            return false;
        }
        return true;
    }

    private static boolean isCooldownReady() {
        return cooldownTicks-- <= 0;
    }

    private static void highlightPath() {
        for (BlockPos pos : currentPath) {
            if (currentWorld.isChunkLoaded(pos)) {
                spawnParticlesAt(pos);
            }
        }
    }

    private static void spawnParticlesAt(BlockPos pos) {
        double px = pos.getX() + 0.5;
        double py = pos.getY() + 1.5;
        double pz = pos.getZ() + 0.5;

        currentWorld.spawnParticles(
            ParticleTypes.END_ROD,
            px, py, pz,
            PARTICLE_COUNT,
            PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD,
            0.0
        );
    }
}

