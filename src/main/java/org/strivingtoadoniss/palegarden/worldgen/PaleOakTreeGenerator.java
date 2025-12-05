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
import org.strivingtoadoniss.palegarden.ModBlocks;

public class PaleOakTreeGenerator {

    private PaleOakTreeGenerator() {}

    private static final Map<ChunkPos, Set<BlockPos>> generatedTrees = new HashMap<>();

    public static boolean generateTreeAt(ServerWorld world, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);

        ConfiguredFeature<?, ?> feature = getConfiguredFeature(world);
        if (feature == null) {
            return false;
        }

        boolean success = tryGenerateTree(world, pos, feature);

        if (success) {
            infectBlockBelow(world, pos);
            trackGeneratedTree(chunkPos, pos);
        }

        return success;
    }

    private static ConfiguredFeature<?, ?> getConfiguredFeature(ServerWorld world) {
        return world.getRegistryManager()
                .getOrThrow(RegistryKeys.CONFIGURED_FEATURE)
                .get(TreeConfiguredFeatures.PALE_OAK);
    }

    private static boolean tryGenerateTree(ServerWorld world, BlockPos pos, ConfiguredFeature<?, ?> feature) {
        return feature.generate(
            world,
            world.getChunkManager().getChunkGenerator(),
            world.getRandom(),
            pos
        );
    }

    private static void infectBlockBelow(ServerWorld world, BlockPos pos) {
        world.setBlockState(pos.down(), ModBlocks.PALE_ROOTED_STONE.getDefaultState(), 2);
    }

    private static void trackGeneratedTree(ChunkPos chunkPos, BlockPos pos) {
        generatedTrees.computeIfAbsent(chunkPos, k -> new HashSet<>()).add(pos);
    }

}

