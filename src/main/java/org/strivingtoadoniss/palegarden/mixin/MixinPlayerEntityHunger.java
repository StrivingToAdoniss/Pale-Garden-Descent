package org.strivingtoadoniss.palegarden.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.strivingtoadoniss.palegarden.ModEffects;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntityHunger extends LivingEntity {

    protected MixinPlayerEntityHunger(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "addExhaustion", at = @At("HEAD"), argsOnly = true)
    private float increaseExhaustion(float exhaustion) {
        if (this.hasStatusEffect(ModEffects.GOLDEN_METABOLISM)) {
            return exhaustion * 8F;
        }

        return exhaustion;
    }
}
