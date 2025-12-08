package org.strivingtoadoniss.palegarden.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import org.strivingtoadoniss.palegarden.ModEntities;
import org.strivingtoadoniss.palegarden.entities.CometBossEntity;

public class CometGorgeGenerator {

    private static final int CRATER_RADIUS = 10;
    private static final int CREVICE_WIDTH = 3;
    private static final int CHAMBER_RADIUS = 17;
    private static final int CHAMBER_DEPTH_MAX = 0;
    public static final int BEDROCK_LEVEL = -60;

    private CometGorgeGenerator() {}

    public static BlockPos generateGorge(ServerWorld world, BlockPos surfacePos, Random random) {
        BlockPos adjustedCenter = adjustChamberPosition(world, surfacePos, random);

        generateChamber(world, adjustedCenter, random);
        generateCrevice(world, surfacePos, adjustedCenter.up(1), random);
        generateCrater(world, surfacePos, random);

        return adjustedCenter.down(CHAMBER_RADIUS);
    }

    private static void generateCrater(ServerWorld world, BlockPos center, Random random) {
        PerlinNoiseSampler noiseSampler = new PerlinNoiseSampler(random);

        for (int x = -CRATER_RADIUS; x <= CRATER_RADIUS; x++) {
            for (int z = -CRATER_RADIUS; z <= CRATER_RADIUS; z++) {
                double distance = Math.sqrt(x * x + z * z);

                if (distance <= CRATER_RADIUS) {
                    double combinedNoise = calculateCombinedNoise(noiseSampler, x, z);
                    double noisyDistance = distance + combinedNoise;

                    BlockPos surfacePos = center.add(x, 0, z);

                    if (noisyDistance <= CRATER_RADIUS + 2) {
                        excavateCraterDepression(world, surfacePos, distance, combinedNoise, random);
                    }
                }
            }
        }
    }

    private static double calculateCombinedNoise(PerlinNoiseSampler noiseSampler, int x, int z) {
        double noise1 = noiseSampler.sample(x * 0.3, 0, z * 0.3) * 3.0;
        double noise2 = noiseSampler.sample(x * 0.6, 0, z * 0.6) * 1.5;
        return noise1 + noise2;
    }

    private static void excavateCraterDepression(ServerWorld world, BlockPos surfacePos,
        double distance, double combinedNoise, Random random) {
        double depthFactor = 1.0 - (distance / CRATER_RADIUS);
        int maxDepth = (int) (8 * depthFactor * (1.0 + Math.abs(combinedNoise) * 0.3));

        for (int dy = -maxDepth; dy <= maxDepth; dy++) {
            BlockPos clearPos = dy < 0 ? surfacePos.down(-dy) : surfacePos.up(dy);

            if (random.nextFloat() < 0.85f || Math.abs(dy) < maxDepth * 0.6) {
                replaceWithAir(world, clearPos);
            }
        }
    }

    private static void generateCrevice(ServerWorld world, BlockPos top, BlockPos bottom,
        Random random) {
        int height = top.getY() - bottom.getY();

        if (height <= 0) {
            return;
        }

        PerlinNoiseSampler noiseSampler = new PerlinNoiseSampler(random);

        for (int y = 0; y <= height; y++) {
            int currentY = bottom.getY() + y;
            double progress = (double) y / height;
            int currentWidth = calculateCreviceWidth(progress);

            carveCreviceLayer(world, top, currentY, currentWidth, noiseSampler, random);
        }
    }

    private static int calculateCreviceWidth(double progress) {
        double widthMultiplier;
        if (progress < 0.7) {
            widthMultiplier = 1.2;
        } else {
            widthMultiplier = 1.2 - (progress - 0.7) * 2.0;
        }

        return Math.max(1, (int) (CREVICE_WIDTH * widthMultiplier));
    }

    private static void carveCreviceLayer(ServerWorld world, BlockPos top, int currentY,
        int currentWidth, PerlinNoiseSampler noiseSampler, Random random) {
        for (int x = -currentWidth; x <= currentWidth; x++) {
            for (int z = -currentWidth; z <= currentWidth; z++) {
                double noise1 = noiseSampler.sample(x * 0.3, currentY * 0.15, z * 0.3);
                double noise2 = noiseSampler.sample(x * 0.8, currentY * 0.4, z * 0.8) * 0.3;
                double combinedNoise = noise1 + noise2;

                double distFromCenter = Math.sqrt(x * x + z * z);
                double maxDist = currentWidth * 1.2;

                BlockPos pos = new BlockPos(top.getX() + x, currentY, top.getZ() + z);

                if (distFromCenter < maxDist && Math.abs(combinedNoise) < 0.4) {
                    carveCreviceBlock(world, pos);
                } else if (distFromCenter >= maxDist && distFromCenter < maxDist + 2.5) {
                    processWallBlock(world, pos, random);
                }
            }
        }
    }

    private static void carveCreviceBlock(ServerWorld world, BlockPos pos) {
        replaceWithAir(world, pos);
    }

    private static void processWallBlock(ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.4) {
            replaceWithAir(world, pos);
        }
    }

    private static void replaceWithAir(ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
    }

    private static void generateChamber(ServerWorld world, BlockPos center, Random random) {
        carveChamber(world, center, random);
        spawnCometBoss(world, center);
    }

    private static void spawnCometBoss(ServerWorld world, BlockPos pos) {
        var bossEntity = new CometBossEntity(ModEntities.COMET_BOSS, world);
        bossEntity.refreshPositionAndAngles(
            pos.getX() + 0.5,
            pos.getY() + 1.0,
            pos.getZ() + 0.5,
            0.0F,
            0.0F
        );
        bossEntity.setPersistent();
        world.spawnEntity(bossEntity);
    }

    private static BlockPos adjustChamberPosition(ServerWorld world, BlockPos surfacePos, Random random) {
        int chamberDepthMin = BEDROCK_LEVEL + CHAMBER_RADIUS;
        int chamberY = chamberDepthMin + random.nextInt(CHAMBER_DEPTH_MAX - chamberDepthMin);
        BlockPos chamberCenter = new BlockPos(surfacePos.getX(), chamberY, surfacePos.getZ());

        BlockPos lowestPoint = chamberCenter.down(CHAMBER_RADIUS);

        while (world.getBlockState(lowestPoint).isAir() && lowestPoint.getY() > BEDROCK_LEVEL) {
            chamberCenter = chamberCenter.down(1);
            lowestPoint = lowestPoint.down(1);
        }

        return chamberCenter.down(8);
    }

    private static void carveChamber(ServerWorld world, BlockPos center, Random random) {
        PerlinNoiseSampler noiseSampler = new PerlinNoiseSampler(random);

        for (int x = -CHAMBER_RADIUS; x <= CHAMBER_RADIUS; x++) {
            for (int y = -CHAMBER_RADIUS; y <= CHAMBER_RADIUS; y++) {
                for (int z = -CHAMBER_RADIUS; z <= CHAMBER_RADIUS; z++) {
                    if (shouldCarveBlock(x, y, z, noiseSampler)) {
                        BlockPos pos = center.add(x, y, z);
                        if (!world.getBlockState(pos).isOf(Blocks.BEDROCK)) {
                            replaceWithAir(world, pos);
                        }
                    }
                }
            }
        }
    }

    private static boolean shouldCarveBlock(int x, int y, int z, PerlinNoiseSampler noiseSampler) {
        double distance = Math.sqrt(x * x + y * y + z * z);

        if (distance > CHAMBER_RADIUS) {
            return false;
        }

        double noise = noiseSampler.sample(x * 0.3, y * 0.3, z * 0.3);
        double noisyDistance = distance + noise * 1.5;

        return noisyDistance < CHAMBER_RADIUS - 1;
    }
}
