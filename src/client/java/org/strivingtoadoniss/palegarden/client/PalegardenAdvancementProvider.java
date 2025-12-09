package org.strivingtoadoniss.palegarden.client;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.EffectsChangedCriterion;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.ModEffects;
import org.strivingtoadoniss.palegarden.ModItems;
import org.strivingtoadoniss.palegarden.ModPotions;
import org.strivingtoadoniss.palegarden.Palegarden;

public class PalegardenAdvancementProvider extends FabricAdvancementProvider {

    public static final String BACKGROUNDS_ADVENTURE_PNG = "textures/gui/advancements/backgrounds/adventure.png";

    public PalegardenAdvancementProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        buildThirdHandAdvancement(consumer);
        buildGiantRibcageAdvancement(consumer);
        buildGoldenMetabolismAdvancement(consumer);
    }

    private void buildGoldenMetabolismAdvancement(Consumer<AdvancementEntry> consumer) {
        Advancement.Builder.create()
            .display(
                PotionContentsComponent.createStack(Items.POTION,
                    Registries.POTION.getEntry(ModPotions.POTION_OF_GOLDEN_METABOLISM)),
                Text.translatable("advancement.palegarden.obtained_golden_metabolism.title"),
                Text.translatable("advancement.palegarden.obtained_golden_metabolism.description"),
                Identifier.ofVanilla(BACKGROUNDS_ADVENTURE_PNG),
                AdvancementFrame.CHALLENGE,
                true,
                true,
                false
            )
            .criterion("got_golden_metabolism_effect", EffectsChangedCriterion.Conditions.create(
                EntityEffectPredicate.Builder.create().addEffect(ModEffects.GOLDEN_METABOLISM)
            ))
            .build(consumer, Identifier.of(Palegarden.MOD_ID, "obtained_golden_metabolism").toString());
    }

    private static void buildGiantRibcageAdvancement(Consumer<AdvancementEntry> consumer) {
        Advancement.Builder.create()
            .display(
                PotionContentsComponent.createStack(Items.POTION,
                    Registries.POTION.getEntry(ModPotions.POTION_OF_GIANT_RIBCAGE)),
                Text.translatable("advancement.palegarden.obtained_giant_ribcage.title"),
                Text.translatable("advancement.palegarden.obtained_giant_ribcage.description"),
                Identifier.ofVanilla(BACKGROUNDS_ADVENTURE_PNG),
                AdvancementFrame.CHALLENGE,
                true,
                true,
                false
            )
            .criterion("got_giant_ribcage_effect", EffectsChangedCriterion.Conditions.create(
                EntityEffectPredicate.Builder.create().addEffect(ModEffects.GIANT_RIBCAGE)
            ))
            .build(consumer, Identifier.of(Palegarden.MOD_ID, "obtained_giant_ribcage").toString());
    }

    private static void buildThirdHandAdvancement(Consumer<AdvancementEntry> consumer) {
        Advancement.Builder.create()
            .display(
                PotionContentsComponent.createStack(Items.POTION,
                    Registries.POTION.getEntry(ModPotions.POTION_OF_THIRD_HAND)),
                Text.translatable("advancement.palegarden.obtained_third_hand.title"),
                Text.translatable("advancement.palegarden.obtained_third_hand.description"),
                Identifier.ofVanilla(BACKGROUNDS_ADVENTURE_PNG),
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
