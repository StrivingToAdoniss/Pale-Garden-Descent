package org.strivingtoadoniss.palegarden.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;

public class ThirdHandEffect extends StatusEffect {
    public ThirdHandEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x5A6C81);

        this.addAttributeModifier(
                EntityAttributes.MAX_HEALTH,
                Identifier.of(Palegarden.MOD_ID, "third_hand_health_reduction"),
                -0.35,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
    }
}
