package org.strivingtoadoniss.palegarden.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;

public class GiantRibcageEffect extends StatusEffect {
    public GiantRibcageEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x8B4513);

        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_SPEED,
                Identifier.of(Palegarden.MOD_ID, "giant_ribcage_speed_reduction"),
                -0.75,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
    }
}
