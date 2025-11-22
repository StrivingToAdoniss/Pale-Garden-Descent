package org.strivingtoadoniss.palegarden.worldgen;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class PaleOakTreeGenerator {

    private static final Map<ChunkPos, Set<BlockPos>> generatedTrees = new HashMap<>();

    public static boolean generateTreeAt(ServerWorld world, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);

        ConfiguredFeature<?, ?> feature = world.getRegistryManager()
                .getOrThrow(RegistryKeys.CONFIGURED_FEATURE)
                .get(TreeConfiguredFeatures.PALE_OAK);

        if (feature == null) {
            return false;
        }

        boolean success = feature.generate(
            world,
            world.getChunkManager().getChunkGenerator(),
            world.getRandom(),
            pos
        );

        if (success) {
            generatedTrees.computeIfAbsent(chunkPos, k -> new HashSet<>()).add(pos);
        }

        return success;
    }

}

