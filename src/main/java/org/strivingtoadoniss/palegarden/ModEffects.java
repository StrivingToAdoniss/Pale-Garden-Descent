package org.strivingtoadoniss.palegarden;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.effect.GiantRibcageEffect;
import org.strivingtoadoniss.palegarden.effect.GoldenMetabolismEffect;
import org.strivingtoadoniss.palegarden.effect.ThirdHandEffect;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> THIRD_HAND = register("third_hand",
        new ThirdHandEffect());
    public static final RegistryEntry<StatusEffect> GIANT_RIBCAGE = register("giant_ribcage",
        new GiantRibcageEffect());
    public static final RegistryEntry<StatusEffect> GOLDEN_METABOLISM = register("golden_metabolism",
        new GoldenMetabolismEffect());

    private static RegistryEntry<StatusEffect> register(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Palegarden.MOD_ID, name),
                statusEffect);
    }

    public static void register() {
    }
}
