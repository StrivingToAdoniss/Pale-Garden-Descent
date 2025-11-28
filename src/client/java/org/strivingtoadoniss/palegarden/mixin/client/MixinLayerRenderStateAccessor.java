package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderState.LayerRenderState.class)
public interface MixinLayerRenderStateAccessor {
    @Accessor("model")
    void setModel(BakedModel model);
}
