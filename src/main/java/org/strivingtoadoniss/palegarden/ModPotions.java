package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(PotionContentsComponent.createStack(Items.POTION,
                Registries.POTION.getEntry(POTION_OF_THIRD_HAND)));
            itemGroup.add(PotionContentsComponent.createStack(Items.POTION,
                Registries.POTION.getEntry(POTION_OF_GIANT_RIBCAGE)));
            itemGroup.add(PotionContentsComponent.createStack(Items.POTION,
                Registries.POTION.getEntry(PALE_POTION_3)));
        });

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
            builder.registerPotionRecipe(
                Potions.WATER,
                ModBlocks.PALE_MUSHROOM.asItem(),
                Registries.POTION.getEntry(POTION_OF_THIRD_HAND)
            )
        );

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
            builder.registerPotionRecipe(
                Registries.POTION.getEntry(POTION_OF_THIRD_HAND),
                ModBlocks.PALE_MUSHROOM.asItem(),
                Registries.POTION.getEntry(POTION_OF_GIANT_RIBCAGE)
            )
        );

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
            builder.registerPotionRecipe(
                Registries.POTION.getEntry(POTION_OF_GIANT_RIBCAGE),
                ModBlocks.PALE_MUSHROOM.asItem(),
                Registries.POTION.getEntry(PALE_POTION_3)
            )
        );
    }

    public static final Potion POTION_OF_THIRD_HAND = Registry.register(
        Registries.POTION,
        Identifier.of(Palegarden.MOD_ID, "potion_of_third_hand"),
        new Potion("potion_of_third_hand",
            new StatusEffectInstance(
                ModEffects.THIRD_HAND,
                -1,
                0,
                false,
                false,
                true
            )
        )
    );

    public static final Potion POTION_OF_GIANT_RIBCAGE = Registry.register(
        Registries.POTION,
        Identifier.of(Palegarden.MOD_ID, "potion_of_giant_ribcage"),
        new Potion("potion_of_giant_ribcage",
            new StatusEffectInstance(
                ModEffects.GIANT_RIBCAGE,
                -1,
                0,
                false,
                false,
                true
            )
        )
    );

    public static final Potion PALE_POTION_3 = Registry.register(
        Registries.POTION,
        Identifier.of(Palegarden.MOD_ID, "pale_potion_3"),
        new Potion("pale_potion_3",
            new StatusEffectInstance(
                StatusEffects.SLOWNESS,
                3600,
                0
            )
        )
    );
}