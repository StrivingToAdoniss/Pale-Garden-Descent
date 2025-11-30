package org.strivingtoadoniss.palegarden.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;

public class RootedEffect extends StatusEffect {
    public RootedEffect() {
        super(StatusEffectCategory.HARMFUL, 0x4A5D3F);

        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_SPEED,
                Identifier.of(Palegarden.MOD_ID, "rooted_speed_reduction"),
                -0.99,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );

        this.addAttributeModifier(
            EntityAttributes.JUMP_STRENGTH,
            Identifier.of(Palegarden.MOD_ID, "rooted_jump_strength_reduction"),
            -0.99,
            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
    }
}
