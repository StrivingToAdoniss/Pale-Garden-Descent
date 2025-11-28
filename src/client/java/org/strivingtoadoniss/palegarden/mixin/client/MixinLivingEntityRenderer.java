package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.strivingtoadoniss.palegarden.ModEffects;
import org.strivingtoadoniss.palegarden.client.CustomElytraStateDuck;
import org.strivingtoadoniss.palegarden.inventory.ElytraPlayerDuck;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer {

    @Inject(method = "updateRenderState*", at = @At("TAIL"))
    private void captureCustomElytra(LivingEntity entity, LivingEntityRenderState state, float tickDelta, CallbackInfo ci) {
        resetCustomElytra(state);
        updateCustomElytraIfApplicable(entity, state);
    }

    @Unique
    private void resetCustomElytra(LivingEntityRenderState state) {
        ((CustomElytraStateDuck) state).palegarden$setCustomElytra(ItemStack.EMPTY);
    }

    @Unique
    private void updateCustomElytraIfApplicable(LivingEntity entity, LivingEntityRenderState state) {
        if (!(entity instanceof PlayerEntity player) || !player.hasStatusEffect(ModEffects.GIANT_RIBCAGE)) {
            return;
        }

        if (player instanceof ElytraPlayerDuck duck) {
            setCustomElytraFromInventory(duck, state);
        }
    }

    private void setCustomElytraFromInventory(ElytraPlayerDuck duck, LivingEntityRenderState state) {
        ItemStack stack = duck.getElytraInventoryAddon().getStack(0);

        if (stack.contains(DataComponentTypes.GLIDER)) {
            ((CustomElytraStateDuck) state).palegarden$setCustomElytra(stack);
        }
    }
}
