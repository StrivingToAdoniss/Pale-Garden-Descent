package org.strivingtoadoniss.palegarden.spread;

import lombok.Getter;
import net.minecraft.network.packet.s2c.play.ChunkBiomeDataS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.WorldChunk;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;

import java.util.*;

public abstract class SpreadCursor {

    public static final int NEIGHBORS_SEARCH_RADIUS = 5;

    protected final ServerWorld world;
    protected final BlockPos origin;
    protected final BiomeBackupData biomeBackup;

    protected int currentTransformations = 0;
    protected int tickCounter = 0;

    protected final Queue<BlockPos> searchQueue = new LinkedList<>();
    protected final Set<BlockPos> visited = new HashSet<>();
    protected final Set<WorldChunk> modifiedChunks = new HashSet<>();

    @Getter
    protected boolean finished = false;

    @Getter
    protected boolean active = false;

    protected SpreadCursor(ServerWorld world, BlockPos origin) {
        this.world = world;
        this.origin = origin;
        this.biomeBackup = BiomeBackupData.getOrCreate(world);
    }

    public void activate() {
        if (!active) {
            active = true;
            addNeighborsToQueue(origin);
        }
    }

    public void tick() {
        if (!active) {
            return;
        }

        tickCounter++;
        if (tickCounter < InfectionConfig.TICK_DELAY) {
            return;
        }
        tickCounter = 0;

        for (int i = 0; i < InfectionConfig.BLOCKS_PER_TICK; i++) {
            if (searchQueue.isEmpty()) {
                finishImmediately();
                return;
            }

            BlockPos pos = searchQueue.poll();

            processBlock(pos);
            spawnParticles(pos);
            addNeighborsToQueue(pos);

            if (++currentTransformations >= InfectionConfig.MAX_TRANSFORMATIONS) {
                finishImmediately();
                return;
            }
        }

        sendChunkUpdates();
    }

    protected abstract void processBlock(BlockPos pos);

    protected abstract void spawnParticles(BlockPos pos);

    protected abstract boolean canProcess(BlockPos pos);

    protected boolean replaceBiomeInColumn(Chunk chunk, int quartX, int quartZ, RegistryEntry<Biome> newBiome) {
        boolean anyChanged = false;

        for (ChunkSection section : chunk.getSectionArray()) {
            if (replaceBiomeInSection(section, quartX, quartZ, newBiome)) {
                anyChanged = true;
            }
        }

        return anyChanged;
    }

    protected boolean replaceBiomeInSection(ChunkSection section, int quartX, int quartZ,
            RegistryEntry<Biome> newBiome) {
        var container = (PalettedContainer<RegistryEntry<Biome>>) section.getBiomeContainer();
        boolean changed = false;

        container.lock();
        try {
            for (int quartY = 0; quartY < 4; quartY++) {
                if (!container.get(quartX, quartY, quartZ).equals(newBiome)) {
                    container.swapUnsafe(quartX, quartY, quartZ, newBiome);
                    changed = true;
                }
            }
        } finally {
            container.unlock();
        }

        return changed;
    }

    protected void markChunkAsModified(Chunk chunk) {
        if (chunk instanceof WorldChunk worldChunk) {
            modifiedChunks.add(worldChunk);
            worldChunk.markNeedsSaving();
        }
    }

    protected void addNeighborsToQueue(BlockPos pos) {
        for (int dx = -NEIGHBORS_SEARCH_RADIUS; dx <= NEIGHBORS_SEARCH_RADIUS; dx++) {
            for (int dy = -NEIGHBORS_SEARCH_RADIUS; dy <= NEIGHBORS_SEARCH_RADIUS; dy++) {
                for (int dz = -NEIGHBORS_SEARCH_RADIUS; dz <= NEIGHBORS_SEARCH_RADIUS; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }

                    tryAddNeighbor(pos, dx, dy, dz);
                }
            }
        }
    }

    protected void tryAddNeighbor(BlockPos pos, int dx, int dy, int dz) {
        if (!isWithinSearchRadius(dx, dy, dz)) {
            return;
        }

        BlockPos neighbor = pos.add(dx, dy, dz);

        if (shouldAddNeighbor(neighbor)) {
            searchQueue.add(neighbor);
            visited.add(neighbor);
        }
    }

    protected boolean isWithinSearchRadius(int dx, int dy, int dz) {
        return dx * dx + dy * dy + dz * dz <= NEIGHBORS_SEARCH_RADIUS * NEIGHBORS_SEARCH_RADIUS;
    }

    protected boolean shouldAddNeighbor(BlockPos neighbor) {
        return !visited.contains(neighbor)
                && world.isInBuildLimit(neighbor)
                && canProcess(neighbor);
    }

    protected void sendChunkUpdates() {
        if (modifiedChunks.isEmpty()) {
            return;
        }

        List<ChunkBiomeDataS2CPacket.Serialized> serializedChunks = new ArrayList<>();
        for (WorldChunk chunk : modifiedChunks) {
            serializedChunks.add(new ChunkBiomeDataS2CPacket.Serialized(chunk));
        }

        ChunkBiomeDataS2CPacket packet = new ChunkBiomeDataS2CPacket(serializedChunks);
        world.getPlayers().forEach(player -> player.networkHandler.sendPacket(packet));

        modifiedChunks.clear();
    }

    public void finishImmediately() {
        finished = true;
        active = false;
    }
}
