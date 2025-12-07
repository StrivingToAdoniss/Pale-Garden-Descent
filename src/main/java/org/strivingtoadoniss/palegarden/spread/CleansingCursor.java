package org.strivingtoadoniss.palegarden.spread;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;

public class CleansingCursor extends SpreadCursor {

    public static final Map<Block, Block> REPLACEMENTS = Map.of(
        Blocks.PALE_OAK_LOG, Blocks.AIR,
        ModBlocks.PALE_STONE, Blocks.STONE,
        ModBlocks.PALE_ROOTED_STONE, Blocks.DIRT,
        ModBlocks.PALE_MUSHROOM, Blocks.AIR,
        Blocks.PALE_MOSS_BLOCK, Blocks.GRASS_BLOCK,
        Blocks.PALE_MOSS_CARPET, Blocks.SHORT_GRASS
    );

    public CleansingCursor(ServerWorld world, BlockPos origin) {
        super(world, origin);
    }

    @Override
    protected void processBlock(BlockPos pos) {
        cleanseBlock(pos);
    }

    @Override
    protected void spawnParticles(BlockPos pos) {
        world.spawnParticles(ParticleTypes.END_ROD,
            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
            5, 0.2, 0.2, 0.2, 0.01
        );
    }

    @Override
    protected boolean canProcess(BlockPos pos) {
        if (pos.getManhattanDistance(origin) > InfectionConfig.MAX_INFECTION_RADIUS) {
            return false;
        }

        Block block = world.getBlockState(pos).getBlock();

        return REPLACEMENTS.containsKey(block);
    }

    private void cleanseBlock(BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        Block replacement = REPLACEMENTS.get(state.getBlock());
        if (replacement != null) {
            world.setBlockState(pos, replacement.getDefaultState());
        }

        restoreOriginalBiome(pos);
    }

    private void restoreOriginalBiome(BlockPos pos) {
        RegistryKey<Biome> originalBiome = biomeBackup.getOriginalBiome(pos);
        if (originalBiome == null) {
            return;
        }

        var biomeEntry = world.getRegistryManager()
            .getOrThrow(RegistryKeys.BIOME)
            .getOptional(originalBiome);

        if (biomeEntry.isEmpty()) {
            return;
        }

        int quartX = (pos.getX() & 15) >> 2;
        int quartZ = (pos.getZ() & 15) >> 2;
        Chunk chunk = world.getChunk(pos);

        boolean changed = replaceBiomeInColumn(chunk, quartX, quartZ, biomeEntry.get());

        if (changed) {
            markChunkAsModified(chunk);
        }
    }
}
