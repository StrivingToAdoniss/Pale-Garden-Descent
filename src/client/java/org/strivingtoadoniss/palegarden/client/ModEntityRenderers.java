package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.strivingtoadoniss.palegarden.ModEntities;
import org.strivingtoadoniss.palegarden.client.render.entity.CometBossRenderer;
import org.strivingtoadoniss.palegarden.client.render.entity.SplitterSkeletonRenderer;

public class ModEntityRenderers {

    private ModEntityRenderers() {}

    public static void initialize() {
        EntityRendererRegistry.register(ModEntities.COMET_BOSS, CometBossRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPLITTER_SKELETON, SplitterSkeletonRenderer::new);
    }
}
