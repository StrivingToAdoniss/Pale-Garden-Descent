package org.strivingtoadoniss.palegarden.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.entities.SplitterSkeletonEntity;

public class SplitterSkeletonRenderer extends SkeletonEntityRenderer {
    public SplitterSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
