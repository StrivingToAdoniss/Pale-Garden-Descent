package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EffectManager {

    private EffectManager() {}

    private static final Identifier THIRD_HAND_ADVANCEMENT_ID = Identifier.of(Palegarden.MOD_ID,
        "obtained_third_hand");
    private static final Identifier GIANT_RIBCAGE_ADVANCEMENT_ID = Identifier.of(Palegarden.MOD_ID,
        "obtained_giant_ribcage");
    private static final Identifier GOLDEN_METABOLISM_ADVANCEMENT_ID = Identifier.of(
        Palegarden.MOD_ID, "obtained_golden_metabolism");

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            checkThirdHandAdvancement(server);
            checkGiantRibcageAdvancement(server);
            checkGoldenMetabolismAdvancement(server);
        });
    }

    private static void checkGoldenMetabolismAdvancement(MinecraftServer server) {
        AdvancementEntry advancement = server.getAdvancementLoader()
            .get(GOLDEN_METABOLISM_ADVANCEMENT_ID);

        if (advancement == null) {
            return;
        }

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

            boolean hasAdvancement = player.getAdvancementTracker().getProgress(advancement)
                .isDone();

            if (hasAdvancement && !player.hasStatusEffect(ModEffects.GOLDEN_METABOLISM)) {
                player.addStatusEffect(
                    ModPotions.POTION_OF_GOLDEN_METABOLISM.getEffects().getFirst());
            }
        }
    }

    private static void checkGiantRibcageAdvancement(MinecraftServer server) {
        AdvancementEntry advancement = server.getAdvancementLoader()
            .get(GIANT_RIBCAGE_ADVANCEMENT_ID);

        if (advancement == null) {
            return;
        }

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

            boolean hasAdvancement = player.getAdvancementTracker().getProgress(advancement)
                .isDone();

            if (hasAdvancement && !player.hasStatusEffect(ModEffects.GIANT_RIBCAGE)) {
                player.addStatusEffect(ModPotions.POTION_OF_GIANT_RIBCAGE.getEffects().getFirst());
            }
        }
    }

    private static void checkThirdHandAdvancement(MinecraftServer server) {
        AdvancementEntry advancement = server.getAdvancementLoader().get(
            THIRD_HAND_ADVANCEMENT_ID);

        if (advancement == null) {
            return;
        }

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

            boolean hasAdvancement = player.getAdvancementTracker().getProgress(advancement)
                .isDone();

            if (hasAdvancement && !player.hasStatusEffect(ModEffects.THIRD_HAND)) {
                player.addStatusEffect(ModPotions.POTION_OF_THIRD_HAND.getEffects().getFirst());
            }
        }
    }
}
