package org.strivingtoadoniss.palegarden.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;


public class SplitterSkeletonRenderer extends SkeletonEntityRenderer {

    private static final Identifier TEXTURE = Identifier.of(Palegarden.MOD_ID,
        "textures/entity/splitter_skeleton.png");

    public SplitterSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SkeletonEntityRenderState skeletonEntityRenderState) {
        return TEXTURE;
    }

}
