package org.strivingtoadoniss.palegarden.client;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.EffectsChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.ModEffects;
import org.strivingtoadoniss.palegarden.Palegarden;

public class PalegardenAdvancementProvider extends FabricAdvancementProvider {

    public PalegardenAdvancementProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {

        Advancement.Builder.create()
            .display(
                Items.POTION,
                Text.literal("Третя рука"),
                Text.literal("Ви мутували! Тепер це назавжди."),
                null,
                AdvancementFrame.CHALLENGE,
                true,
                true,
                false
            )
            .criterion("got_third_hand_effect", EffectsChangedCriterion.Conditions.create(
                EntityEffectPredicate.Builder.create().addEffect(ModEffects.THIRD_HAND)
            ))
            .build(consumer, Identifier.of(Palegarden.MOD_ID, "obtained_third_hand").toString());
    }
}
