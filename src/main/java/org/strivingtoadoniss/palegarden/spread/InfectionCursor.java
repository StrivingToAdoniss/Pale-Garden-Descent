package org.strivingtoadoniss.palegarden.spread;

import java.util.Set;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.PalettedContainer;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.blocks.PaleMushroomBlock;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;
import org.strivingtoadoniss.palegarden.worldgen.LabyrinthGenerator;
import org.strivingtoadoniss.palegarden.worldgen.ModBiomes;
import org.strivingtoadoniss.palegarden.worldgen.PaleOakTreeGenerator;

import java.util.Optional;

public class InfectionCursor extends SpreadCursor {

    @Getter
    private final CleansingCursor pairedCleansingCursor;

    public static final Set<Block> EXCLUDED_BLOCKS = Set.of(
        ModBlocks.PALE_MUSHROOM,
        ModBlocks.PALE_STONE,
        ModBlocks.PALE_ROOTED_STONE,
        Blocks.PALE_OAK_LOG,
        Blocks.PALE_OAK_LEAVES,
        Blocks.PALE_HANGING_MOSS,
        Blocks.MOSS_CARPET,
        Blocks.HANGING_ROOTS,
        Blocks.BEDROCK
    );

    public InfectionCursor(ServerWorld world, BlockPos origin) {
        super(world, origin);
        this.pairedCleansingCursor = new CleansingCursor(world, origin);
        activate();
    }

    @Override
    protected void processBlock(BlockPos pos) {
        infectBlock(pos);
        placeLabyrinthWall(pos);
        trySpawnMushroom(pos);
    }

    @Override
    protected void spawnParticles(BlockPos pos) {
        world.spawnParticles(ParticleTypes.SCULK_CHARGE_POP,
            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
            5, 0.2, 0.2, 0.2, 0.01
        );
    }

    @Override
    protected boolean canProcess(BlockPos pos) {
        if (pos.getManhattanDistance(origin) > InfectionConfig.MAX_INFECTION_RADIUS) {
            return false;
        }

        BlockState state = world.getBlockState(pos);

        return !state.isAir()
            && state.isSolidBlock(world, pos)
            && !EXCLUDED_BLOCKS.contains(state.getBlock());
    }

    private void infectBlock(BlockPos pos) {
        if (shouldConvertToGrass(pos)) {
            convertToGrassBlock(pos);
        } else {
            convertToPaleStone(pos);
        }

        setPaleGardenBiome(pos);
    }

    private void setPaleGardenBiome(BlockPos pos) {
        var biomeEntry = getInfectedBiomeEntry();
        if (biomeEntry.isEmpty()) return;

        int quartX = (pos.getX() & 15) >> 2;
        int quartZ = (pos.getZ() & 15) >> 2;
        Chunk chunk = world.getChunk(pos);

        backupOriginalBiomes(chunk, quartX, quartZ, pos);

        boolean changed = replaceBiomeInColumn(chunk, quartX, quartZ, biomeEntry.get());

        if (changed) {
            markChunkAsModified(chunk);
        }
    }

    private void backupOriginalBiomes(Chunk chunk, int quartX, int quartZ, BlockPos pos) {
        ChunkSection[] sections = chunk.getSectionArray();
        int bottomSectionY = chunk.getBottomSectionCoord();

        for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
            backupBiomesInSection(sections[sectionIndex], sectionIndex, bottomSectionY, quartX,
                quartZ, pos);
        }
    }

    private void backupBiomesInSection(ChunkSection section, int sectionIndex, int bottomSectionY,
        int quartX, int quartZ, BlockPos pos) {
        var container = (PalettedContainer<RegistryEntry<Biome>>) section.getBiomeContainer();
        container.lock();
        try {
            backupBiomesInQuarters(container, sectionIndex, bottomSectionY, quartX, quartZ, pos);
        } finally {
            container.unlock();
        }
    }

    private void backupBiomesInQuarters(PalettedContainer<RegistryEntry<Biome>> container,
        int sectionIndex, int bottomSectionY, int quartX, int quartZ, BlockPos pos) {
        for (int quartY = 0; quartY < 4; quartY++) {
            var currentBiome = container.get(quartX, quartY, quartZ);
            if (shouldBackupBiome(currentBiome)) {
                int yPos = calculateYPosition(bottomSectionY, sectionIndex, quartY);
                biomeBackup.saveOriginalBiome(
                    new BlockPos(pos.getX(), yPos, pos.getZ()),
                    currentBiome.getKey().orElseThrow()
                );
            }
        }
    }

    private boolean shouldBackupBiome(RegistryEntry<Biome> biome) {
        return biome.getKey().isPresent() &&
            !biome.getKey().orElseThrow().equals(ModBiomes.INFECTED_PALE_GARDEN);
    }

    private int calculateYPosition(int bottomSectionY, int sectionIndex, int quartY) {
        return (bottomSectionY + sectionIndex) * 16 + (quartY * 4);
    }

    private Optional<RegistryEntry.Reference<Biome>> getInfectedBiomeEntry() {
        return world.getRegistryManager()
            .getOrThrow(RegistryKeys.BIOME)
            .getOptional(ModBiomes.INFECTED_PALE_GARDEN);
    }

    private boolean shouldConvertToGrass(BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockState aboveState = world.getBlockState(pos.up());

        return !aboveState.isSolidBlock(world, pos.up()) && state.isSolidBlock(world, pos);
    }

    private void convertToGrassBlock(BlockPos pos) {
        world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
    }

    private void convertToPaleStone(BlockPos pos) {
        world.setBlockState(pos,
            world.getRandom().nextBoolean() ? ModBlocks.PALE_STONE.getDefaultState()
                : ModBlocks.PALE_ROOTED_STONE.getDefaultState());
    }

    private void placeLabyrinthWall(BlockPos pos) {
        if (shouldSkipLabyrinthWall(pos)) {
            return;
        }

        ensureLabyrinthGenerated(pos);

        if (LabyrinthGenerator.shouldPlaceWallAt(pos)) {
            buildTreeWall(pos);
        }
    }

    private boolean shouldSkipLabyrinthWall(BlockPos pos) {
        return world.getBlockState(pos.up()).isSolidBlock(world, pos.up())
            || InfectionStarter.isInImpactZone(pos);
    }

    private void ensureLabyrinthGenerated(BlockPos pos) {
        ChunkPos chunkPos = world.getChunk(pos).getPos();
        LabyrinthGenerator.generateLabyrinthChunkIfNotInfected(chunkPos);
    }

    private void buildTreeWall(BlockPos pos) {
        if (!PaleOakTreeGenerator.generateTreeAt(world, pos.up())) {
            convertToPaleStone(pos);
            buildWallColumn(pos);
        }
    }

    private void buildWallColumn(BlockPos pos) {
        int wallHeight = LabyrinthGenerator.getWallHeight();
        for (int dy = 1; dy <= wallHeight; dy++) {
            world.setBlockState(pos.up(dy), Blocks.PALE_OAK_LOG.getDefaultState());
        }
    }

    private void trySpawnMushroom(BlockPos pos) {
        BlockPos mushroomPos = pos.up();

        if (canPlaceMushroom(mushroomPos) && shouldSpawnMushroom()) {
            placeMushroom(mushroomPos);
        }
    }

    private boolean canPlaceMushroom(BlockPos mushroomPos) {
        return world.getBlockState(mushroomPos).isAir();
    }

    private boolean shouldSpawnMushroom() {
        return world.getRandom().nextFloat() <= 0.05f;
    }

    private void placeMushroom(BlockPos mushroomPos) {
        Random random = world.getRandom();
        int amount = getWeightedMushroomAmount(random);

        BlockState mushroomState = ModBlocks.PALE_MUSHROOM.getDefaultState()
            .with(PaleMushroomBlock.AMOUNT, amount);
        world.setBlockState(mushroomPos, mushroomState);
    }

    private int getWeightedMushroomAmount(Random random) {
        float roll = random.nextFloat();

        if (roll < 0.5f) {
            return 1;
        } else if (roll < 0.78f) {
            return 2;
        } else if (roll < 0.88f) {
            return 3;
        } else if (roll < 0.95f) {
            return 4;
        } else {
            return 5;
        }
    }
}
