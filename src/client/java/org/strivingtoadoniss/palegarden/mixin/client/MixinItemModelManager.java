package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.strivingtoadoniss.palegarden.ModPotions;
import org.strivingtoadoniss.palegarden.client.ModModelLoader;

@Mixin(ItemModelManager.class)
public class MixinItemModelManager {

    @Inject(
        method = "update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V",
        at = @At("TAIL")
    )
    private void swapPotionModel(
        ItemRenderState state,
        ItemStack stack,
        ModelTransformationMode transformationMode,
        boolean leftHanded,
        World world,
        LivingEntity entity,
        int seed,
        CallbackInfo ci
    ) {
        if (!isPotionItem(stack)) {
            return;
        }

        PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (contents == null || contents.potion().isEmpty()) {
            return;
        }

        BakedModel customModel = getCustomPotionModel(contents);
        if (customModel != null) {
            applyCustomModel(state, customModel);
        }
    }

    @Unique
    private boolean isPotionItem(ItemStack stack) {
        return stack.isOf(Items.POTION) || stack.isOf(Items.SPLASH_POTION) || stack.isOf(Items.LINGERING_POTION);
    }

    @Unique
    private BakedModel getCustomPotionModel(PotionContentsComponent contents) {
        if (contents.potion().orElseThrow()
            .matchesKey(Registries.POTION.getKey(ModPotions.POTION_OF_THIRD_HAND).orElse(null))) {
            return MinecraftClient.getInstance().getBakedModelManager()
                .getModel(ModModelLoader.THIRD_HAND_MODEL);
        } else if (contents.potion().orElseThrow().matchesKey(
            Registries.POTION.getKey(ModPotions.POTION_OF_GIANT_RIBCAGE).orElse(null))) {
            return MinecraftClient.getInstance().getBakedModelManager()
                .getModel(ModModelLoader.GIANT_RIBCAGE_MODEL);
        } else if (contents.potion().orElseThrow().matchesKey(
            Registries.POTION.getKey(ModPotions.POTION_OF_GOLDEN_METABOLISM).orElse(null))) {
            return MinecraftClient.getInstance().getBakedModelManager()
                .getModel(ModModelLoader.METABOLISM_MODEL);
        }

        return null;
    }

    @Unique
    private void applyCustomModel(ItemRenderState state, BakedModel customModel) {
        MixinItemRenderStateAccessor stateAccessor = (MixinItemRenderStateAccessor) state;
        stateAccessor.setLayerCount(1);

        ItemRenderState.LayerRenderState firstLayer = stateAccessor.getLayers()[0];
        ((MixinLayerRenderStateAccessor) firstLayer).setModel(customModel);

        int[] tints = firstLayer.initTints(1);
        tints[0] = -1;
    }
}
