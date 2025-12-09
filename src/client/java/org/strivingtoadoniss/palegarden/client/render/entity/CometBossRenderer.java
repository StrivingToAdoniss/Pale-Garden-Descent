package org.strivingtoadoniss.palegarden.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;
import org.strivingtoadoniss.palegarden.client.model.entity.CometBossModel;
import org.strivingtoadoniss.palegarden.entities.CometBossEntity;

public class CometBossRenderer extends
    MobEntityRenderer<CometBossEntity, CometBossEntityRenderState, CometBossModel> {

    private static final Identifier TEXTURE = Identifier.of(Palegarden.MOD_ID,
        "textures/entity/comet_boss.png");

    public CometBossRenderer(EntityRendererFactory.Context context) {
        super(context, new CometBossModel(context.getPart(CometBossModel.COMET_BOSS)), 1.0f);
    }

    @Override
    public CometBossEntityRenderState createRenderState() {
        return new CometBossEntityRenderState();
    }

    @Override
    public Identifier getTexture(CometBossEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(CometBossEntity entity, CometBossEntityRenderState state, float f) {
        super.updateRenderState(entity, state, f);
        state.idlingAnimationState.copyFrom(entity.idleAnimationState);
    }

    @Override
    protected void scale(CometBossEntityRenderState state, MatrixStack matrices) {
        float scale = 4.0f;
        matrices.scale(scale, scale, scale);

        matrices.scale(0.999f, 0.999f, 0.999f);
    }
}
