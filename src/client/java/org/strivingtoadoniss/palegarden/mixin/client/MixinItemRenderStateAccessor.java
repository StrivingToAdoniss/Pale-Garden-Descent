package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.render.item.ItemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderState.class)
public interface MixinItemRenderStateAccessor {
    @Accessor("layers")
    ItemRenderState.LayerRenderState[] getLayers();

    @Accessor("layerCount")
    void setLayerCount(int count);
}
