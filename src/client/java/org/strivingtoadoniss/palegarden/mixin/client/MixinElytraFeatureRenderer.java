package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.strivingtoadoniss.palegarden.client.CustomElytraStateDuck;

@Mixin(ElytraFeatureRenderer.class)
public abstract class MixinElytraFeatureRenderer<S extends BipedEntityRenderState, M extends EntityModel<S>> extends
    FeatureRenderer<S, M> {

    protected MixinElytraFeatureRenderer(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @ModifyVariable(method = "render*", at = @At("STORE"), ordinal = 0)
    private ItemStack useStackFromCustomSlot(ItemStack original, MatrixStack matrixStack,
        VertexConsumerProvider vertexConsumerProvider, int light, BipedEntityRenderState state,
        float limbAngle, float limbDistance) {

        if (state instanceof CustomElytraStateDuck duck) {
            ItemStack customElytra = duck.palegarden$getCustomElytra();
            if (!customElytra.isEmpty()) {
                return customElytra;
            }
        }

        return original;
    }
}
