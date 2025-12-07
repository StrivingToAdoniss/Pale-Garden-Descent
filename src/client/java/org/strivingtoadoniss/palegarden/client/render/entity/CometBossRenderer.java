package org.strivingtoadoniss.palegarden.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;
import org.strivingtoadoniss.palegarden.entities.CometBossEntity;

public class CometBossRenderer extends MobEntityRenderer<CometBossEntity, LivingEntityRenderState, SlimeEntityModel> {

    private static final Identifier TEXTURE = Identifier.of(Palegarden.MOD_ID, "textures/entity/comet_boss.png");

    public CometBossRenderer(EntityRendererFactory.Context context) {
        super(context, new SlimeEntityModel(context.getPart(EntityModelLayers.SLIME)), 1.0f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(LivingEntityRenderState state, net.minecraft.client.util.math.MatrixStack matrices) {
        float scale = 4.0f;
        matrices.scale(scale, scale, scale);

        matrices.scale(0.999f, 0.999f, 0.999f);
    }

    @Override
    public void updateRenderState(CometBossEntity entity, LivingEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
    }
}
