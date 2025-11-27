package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EffectManager {

    private EffectManager() {}

    private static final Identifier THIRD_HAND_ADVANCEMENT_ID = Identifier.of(Palegarden.MOD_ID, "obtained_third_hand");

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            checkThirdHandAdvancement(server);
        });
    }

    private static void checkThirdHandAdvancement(MinecraftServer server) {
        AdvancementEntry advancement = server.getAdvancementLoader().get(
            THIRD_HAND_ADVANCEMENT_ID);

        if (advancement == null) return;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

            boolean hasAdvancement = player.getAdvancementTracker().getProgress(advancement).isDone();

            if (hasAdvancement && !player.hasStatusEffect(ModEffects.THIRD_HAND)) {
                player.addStatusEffect(ModPotions.PALE_POTION_1.getEffects().getFirst());
            }
        }
    }
}
