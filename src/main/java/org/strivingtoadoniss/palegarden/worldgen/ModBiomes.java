package org.strivingtoadoniss.palegarden.worldgen;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import org.strivingtoadoniss.palegarden.Palegarden;
import org.strivingtoadoniss.palegarden.ModEntities;

public class ModBiomes {

    public static final RegistryKey<Biome> INFECTED_PALE_GARDEN = RegistryKey.of(RegistryKeys.BIOME,
        Identifier.of(Palegarden.MOD_ID, "infected_pale_garden"));

    public static void bootstrap(Registerable<Biome> context) {
        context.register(INFECTED_PALE_GARDEN, createSilentPaleGarden());
    }

    private static Biome createSilentPaleGarden() {
        return new Biome.Builder()
            .temperature(0.7f)
            .downfall(0.8f)
            .effects(new BiomeEffects.Builder()
                .skyColor(12171705)
                .fogColor(8484720)
                .waterColor(7768221)
                .waterFogColor(5597568)
                .grassColor(7832178)
                .foliageColor(8883574)
                .moodSound(BiomeMoodSound.CAVE)
                .build())
            .spawnSettings(new SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER,
                    new SpawnSettings.SpawnEntry(EntityType.CREAKING, 100, 1, 3))
                .spawn(SpawnGroup.MONSTER,
                    new SpawnSettings.SpawnEntry(ModEntities.SPLITTER_SKELETON, 100, 4, 4))
                .build())
            .generationSettings(new GenerationSettings.Builder().build())
            .build();
    }
}
