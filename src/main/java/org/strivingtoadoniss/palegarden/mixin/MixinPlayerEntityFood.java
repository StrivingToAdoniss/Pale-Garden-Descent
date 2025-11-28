package org.strivingtoadoniss.palegarden.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.ModEffects;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntityFood {

    @Inject(method = "canConsume", at = @At("HEAD"), cancellable = true)
    private void restrictDiet(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.GOLDEN_METABOLISM)) {
            boolean holdingMushroomMain = player.getMainHandStack().isOf(ModBlocks.PALE_MUSHROOM.asItem());
            boolean holdingMushroomOff = player.getOffHandStack().isOf(ModBlocks.PALE_MUSHROOM.asItem());

            if (!holdingMushroomMain && !holdingMushroomOff) {
                cir.setReturnValue(false);
            }
        }
    }
}
