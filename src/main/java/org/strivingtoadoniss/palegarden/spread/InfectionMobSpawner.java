package org.strivingtoadoniss.palegarden.spread;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import org.strivingtoadoniss.palegarden.ModEntities;

import java.util.HashSet;
import java.util.Set;

public class InfectionMobSpawner {

    private final ServerWorld world;
    private final Set<ChunkPos> chunksWithMobs = new HashSet<>();

    public InfectionMobSpawner(ServerWorld world) {
        this.world = world;
    }

    public void trySpawnMobs(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);

        if (chunksWithMobs.contains(chunkPos)) {
            return;
        }

        chunksWithMobs.add(chunkPos);

        for (int i = 0; i < 2; i++) {
            spawnRandomMob(chunkPos);
        }
    }

    private void spawnRandomMob(ChunkPos chunkPos) {
        Random random = world.getRandom();

        int x = (chunkPos.x << 4) + random.nextInt(16);
        int z = (chunkPos.z << 4) + random.nextInt(16);

        BlockPos spawnPos = findSuitableSpawnPosition(x, z);
        if (spawnPos == null) {
            return;
        }

        EntityType<?> entityType = selectRandomEntityType(random);
        spawnEntity(entityType, spawnPos, random);
    }

    private void spawnEntity(EntityType<?> entityType, BlockPos spawnPos, Random random) {
        var entity = entityType.create(world, SpawnReason.MOB_SUMMONED);

        if (entity != null) {
            entity.refreshPositionAndAngles(
                spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5,
                random.nextFloat() * 360.0f, 0.0f
            );

            if (entity instanceof MobEntity mob) {
                mob.initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.MOB_SUMMONED, null);
            }
            world.spawnEntity(entity);
        }
    }

    private EntityType<?> selectRandomEntityType(Random random) {
        EntityType<?> entityType;

        if (random.nextBoolean()) {
            entityType = EntityType.CREAKING;
        } else {
            entityType = ModEntities.SPLITTER_SKELETON;
        }

        return entityType;
    }

    private BlockPos findSuitableSpawnPosition(int x, int z) {
        int startY = world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);

        for (int y = startY; y >= world.getBottomY(); y--) {
            BlockPos pos = new BlockPos(x, y, z);

            if (isValidSpawnLocation(pos)) {
                return pos.up();
            }
        }

        return null;
    }

    private boolean isValidSpawnLocation(BlockPos pos) {
        return hasSolidGround(pos) && hasSpaceAbove(pos);
    }

    private boolean hasSolidGround(BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        return state.isSolidBlock(world, pos);
    }

    private boolean hasSpaceAbove(BlockPos pos) {
        BlockPos posAbove = pos.up();

        return world.getBlockState(posAbove).isAir() && world.getBlockState(posAbove.up()).isAir();
    }
}
