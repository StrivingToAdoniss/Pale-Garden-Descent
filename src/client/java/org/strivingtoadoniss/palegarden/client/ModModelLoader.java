package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

public class ModModelLoader {

    private ModModelLoader() {}

    public static final Identifier THIRD_HAND_MODEL = Identifier.of("palegarden", "item/potion_of_third_hand");
    public static final Identifier GIANT_RIBCAGE_MODEL = Identifier.of("palegarden", "item/potion_of_giant_ribcage");
    public static final Identifier METABOLISM_MODEL = Identifier.of("palegarden", "item/potion_of_golden_metabolism");

    public static void register() {
        ModelLoadingPlugin.register(ctx -> {
            ctx.addModels(THIRD_HAND_MODEL, GIANT_RIBCAGE_MODEL, METABOLISM_MODEL);
        });
    }
}
