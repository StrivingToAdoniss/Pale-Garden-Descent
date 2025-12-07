package org.strivingtoadoniss.palegarden.spread;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class BiomeBackupData extends PersistentState {

    private static final String DATA_KEY = "palegarden_biome_backup";

    // Structure: ChunkPos -> (SectionY -> (LocalQuartPos -> BiomeKey))
    private final Map<ChunkPos, Map<Integer, Map<Integer, RegistryKey<Biome>>>> biomeBackups = new HashMap<>();

    public BiomeBackupData() {
        super();
    }

    public static BiomeBackupData getOrCreate(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(
            new Type<>(BiomeBackupData::new, BiomeBackupData::createFromNbt, null),
            DATA_KEY
        );
    }

    public void saveOriginalBiome(BlockPos pos, RegistryKey<Biome> biome) {
        ChunkPos chunkPos = new ChunkPos(pos);
        int sectionY = pos.getY() >> 4;
        int localQuartPos = getLocalQuartPosIndex(pos);

        biomeBackups
            .computeIfAbsent(chunkPos, k -> new HashMap<>())
            .computeIfAbsent(sectionY, k -> new HashMap<>())
            .putIfAbsent(localQuartPos, biome);

        markDirty();
    }

    public RegistryKey<Biome> getOriginalBiome(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        int sectionY = pos.getY() >> 4;
        int localQuartPos = getLocalQuartPosIndex(pos);

        Map<Integer, Map<Integer, RegistryKey<Biome>>> chunkData = biomeBackups.get(chunkPos);
        if (chunkData == null)
            return null;

        Map<Integer, RegistryKey<Biome>> sectionData = chunkData.get(sectionY);
        if (sectionData == null)
            return null;

        return sectionData.get(localQuartPos);
    }

    public void clearBackupData() {
        biomeBackups.clear();
        markDirty();
    }

    private int getLocalQuartPosIndex(BlockPos pos) {
        int quartX = (pos.getX() & 15) >> 2;
        int quartY = (pos.getY() & 15) >> 2;
        int quartZ = (pos.getZ() & 15) >> 2;

        return (quartY * 16) + (quartZ * 4) + quartX;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, net.minecraft.registry.RegistryWrapper.WrapperLookup registryLookup) {
        NbtList chunkList = new NbtList();

        for (Map.Entry<ChunkPos, Map<Integer, Map<Integer, RegistryKey<Biome>>>> chunkEntry : biomeBackups.entrySet()) {
            chunkList.add(writeChunkNbt(chunkEntry));
        }

        nbt.put("chunks", chunkList);

        return nbt;
    }

    private NbtCompound writeChunkNbt(Map.Entry<ChunkPos, Map<Integer, Map<Integer, RegistryKey<Biome>>>> chunkEntry) {
        NbtCompound chunkNbt = new NbtCompound();
        chunkNbt.putInt("x", chunkEntry.getKey().x);
        chunkNbt.putInt("z", chunkEntry.getKey().z);

        NbtList sectionList = new NbtList();
        for (Map.Entry<Integer, Map<Integer, RegistryKey<Biome>>> sectionEntry : chunkEntry.getValue().entrySet()) {
            sectionList.add(writeSectionNbt(sectionEntry));
        }

        chunkNbt.put("sections", sectionList);

        return chunkNbt;
    }

    private NbtCompound writeSectionNbt(Map.Entry<Integer, Map<Integer, RegistryKey<Biome>>> sectionEntry) {
        NbtCompound sectionNbt = new NbtCompound();
        sectionNbt.putInt("y", sectionEntry.getKey());

        NbtList biomeList = new NbtList();
        for (Map.Entry<Integer, RegistryKey<Biome>> biomeEntry : sectionEntry.getValue().entrySet()) {
            biomeList.add(writeBiomeNbt(biomeEntry));
        }

        sectionNbt.put("biomes", biomeList);

        return sectionNbt;
    }

    private NbtCompound writeBiomeNbt(Map.Entry<Integer, RegistryKey<Biome>> biomeEntry) {
        NbtCompound biomeNbt = new NbtCompound();
        biomeNbt.putInt("pos", biomeEntry.getKey());
        biomeNbt.putString("biome", biomeEntry.getValue().getValue().toString());

        return biomeNbt;
    }

    public static BiomeBackupData createFromNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        BiomeBackupData data = new BiomeBackupData();

        NbtList chunkList = nbt.getList("chunks", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < chunkList.size(); i++) {
            NbtCompound chunkNbt = chunkList.getCompound(i);
            readChunkNbt(data, chunkNbt);
        }

        return data;
    }

    private static void readChunkNbt(BiomeBackupData data, NbtCompound chunkNbt) {
        ChunkPos chunkPos = new ChunkPos(chunkNbt.getInt("x"), chunkNbt.getInt("z"));
        Map<Integer, Map<Integer, RegistryKey<Biome>>> chunkData = new HashMap<>();

        NbtList sectionList = chunkNbt.getList("sections", NbtElement.COMPOUND_TYPE);
        for (int j = 0; j < sectionList.size(); j++) {
            NbtCompound sectionNbt = sectionList.getCompound(j);
            readSectionNbt(chunkData, sectionNbt);
        }

        data.biomeBackups.put(chunkPos, chunkData);
    }

    private static void readSectionNbt(Map<Integer, Map<Integer, RegistryKey<Biome>>> chunkData,
        NbtCompound sectionNbt) {
        int sectionY = sectionNbt.getInt("y");
        Map<Integer, RegistryKey<Biome>> sectionData = new HashMap<>();

        NbtList biomeList = sectionNbt.getList("biomes", NbtElement.COMPOUND_TYPE);
        for (int k = 0; k < biomeList.size(); k++) {
            NbtCompound biomeNbt = biomeList.getCompound(k);
            readBiomeNbt(sectionData, biomeNbt);
        }

        chunkData.put(sectionY, sectionData);
    }

    private static void readBiomeNbt(Map<Integer, RegistryKey<Biome>> sectionData,
        NbtCompound biomeNbt) {
        int pos = biomeNbt.getInt("pos");
        RegistryKey<Biome> biomeKey = RegistryKey.of(
            RegistryKeys.BIOME,
            Identifier.of(biomeNbt.getString("biome"))
        );
        sectionData.put(pos, biomeKey);
    }
}
