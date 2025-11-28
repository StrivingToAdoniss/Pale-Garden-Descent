package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.strivingtoadoniss.palegarden.client.CustomElytraStateDuck;

@Mixin(LivingEntityRenderState.class)
public class MixinLivingEntityRenderState implements CustomElytraStateDuck {
    @Unique
    private ItemStack customElytra = ItemStack.EMPTY;

    @Override
    public void palegarden$setCustomElytra(ItemStack stack) {
        this.customElytra = stack;
    }

    @Override
    public ItemStack palegarden$getCustomElytra() {
        return this.customElytra;
    }
}
