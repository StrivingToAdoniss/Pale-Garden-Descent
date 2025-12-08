package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import org.strivingtoadoniss.palegarden.entities.CometBossEntity;
import org.strivingtoadoniss.palegarden.entities.SplitterSkeletonEntity;

public class ModEntities {

    private ModEntities() {}

    public static final RegistryKey<EntityType<?>> COMET_BOSS_KEY = RegistryKey.of(
        RegistryKeys.ENTITY_TYPE,
        Identifier.of(Palegarden.MOD_ID, "comet_boss")
    );

    public static final EntityType<CometBossEntity> COMET_BOSS = Registry.register(
        Registries.ENTITY_TYPE,
        COMET_BOSS_KEY,
        EntityType.Builder.create(CometBossEntity::new, SpawnGroup.MONSTER)
            .dimensions(2.0f, 2.0f)
            .maxTrackingRange(10)
            .build(COMET_BOSS_KEY)
    );

    public static final RegistryKey<EntityType<?>> SPLITTER_SKELETON_KEY = RegistryKey.of(
        RegistryKeys.ENTITY_TYPE,
        Identifier.of(Palegarden.MOD_ID, "splitter_skeleton")
    );

    public static final EntityType<SplitterSkeletonEntity> SPLITTER_SKELETON = Registry.register(
        Registries.ENTITY_TYPE,
        SPLITTER_SKELETON_KEY,
        EntityType.Builder.create(SplitterSkeletonEntity::new, SpawnGroup.MONSTER)
            .dimensions(0.6f, 1.99f)
            .maxTrackingRange(8)
            .build(SPLITTER_SKELETON_KEY)
    );

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(COMET_BOSS,
            CometBossEntity.createCometBossAttributes());
        FabricDefaultAttributeRegistry.register(SPLITTER_SKELETON,
            AbstractSkeletonEntity.createAbstractSkeletonAttributes());
    }
}
