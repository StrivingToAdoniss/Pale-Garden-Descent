package org.strivingtoadoniss.palegarden.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class GoldenMetabolismEffect extends StatusEffect {
    public GoldenMetabolismEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFD700);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (isEntityAtFullHealth(entity)) {
            return true;
        }

        if (entity instanceof PlayerEntity player) {
            applyPlayerHealing(player);
        } else {
            entity.heal(1.0F);
        }

        return true;
    }

    private boolean isEntityAtFullHealth(LivingEntity entity) {
        return entity.getHealth() >= entity.getMaxHealth();
    }

    private void applyPlayerHealing(PlayerEntity player) {
        HungerManager hungerManager = player.getHungerManager();
        boolean canHealNatural = hungerManager.getFoodLevel() >= 16 || player.isCreative();

        if (canHealNatural) {
            player.heal(1.0F);

            if (!player.isCreative()) {
                player.addExhaustion(0.4F);
            }
        }
    }
}
