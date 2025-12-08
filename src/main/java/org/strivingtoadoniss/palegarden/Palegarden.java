package org.strivingtoadoniss.palegarden;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strivingtoadoniss.palegarden.spread.InfectionSystem;
import org.strivingtoadoniss.palegarden.util.PathHighlighter;

public class Palegarden implements ModInitializer {

    public static final String MOD_ID = "palegarden";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModItems.initialize();
        ModEntities.initialize();
        ModPotions.register();
        ModCommands.register();
        ModEffects.register();
        ModRegistries.register();
        EffectManager.register();
        InfectionSystem.register();
        PathHighlighter.register();
        PaleGardenEvents.initialize();
    }
}
