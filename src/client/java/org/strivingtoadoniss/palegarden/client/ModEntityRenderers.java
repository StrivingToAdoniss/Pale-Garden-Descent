package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.strivingtoadoniss.palegarden.ModEntities;
import org.strivingtoadoniss.palegarden.client.render.entity.CometBossRenderer;

public class ModEntityRenderers {

    private ModEntityRenderers() {}

    public static void initialize() {
        EntityRendererRegistry.register(ModEntities.COMET_BOSS, CometBossRenderer::new);
    }
}
