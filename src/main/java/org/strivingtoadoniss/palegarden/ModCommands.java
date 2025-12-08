package org.strivingtoadoniss.palegarden;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;
import org.strivingtoadoniss.palegarden.spread.InfectionStarter;
import org.strivingtoadoniss.palegarden.spread.InfectionSystem;
import org.strivingtoadoniss.palegarden.util.PathHighlighter;
import org.strivingtoadoniss.palegarden.util.LabyrinthPathfinder;
import org.strivingtoadoniss.palegarden.worldgen.CometGorgeGenerator;

public class ModCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerStartSpreadCommand(dispatcher);
            registerSetSpeedCommand(dispatcher);
            registerSetDelayCommand(dispatcher);
            registerSetMaxTransformations(dispatcher);
            registerSetMaxInfectionRadius(dispatcher);
            registerStopAllInfections(dispatcher);
            registerEffectClearCommand(dispatcher);
            registerGenerateGorge(dispatcher);
            registerPathToCenterCommand(dispatcher);
        });
    }

    private static final SuggestionProvider<ServerCommandSource> EFFECT_SUGGESTIONS = (context,
        builder) -> CommandSource
        .suggestMatching(new String[]{"third_hand", "giant_ribcage", "golden_metabolism"}, builder);

    private static void registerEffectClearCommand(
        CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("effect_clear")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("effect_name", word())
                .suggests(EFFECT_SUGGESTIONS)
                .executes(ModCommands::executeEffectClear)));
    }

    private static void registerStartSpreadCommand(
        CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("start_spread")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("radius", integer(5))
                .executes(ModCommands::executeStartSpread)));
    }

    private static void registerSetSpeedCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("set_infection_speed")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("speed", integer(1))
                .executes(ModCommands::executeSetSpeed))
        );
    }

    private static void registerSetDelayCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("set_infection_delay")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("delay", integer(0))
                .executes(ModCommands::executeSetDelay))
        );
    }

    private static void registerSetMaxTransformations(
        CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("set_max_transformations")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("transforms", integer(1))
                .executes(ModCommands::executeSetMaxTransformations))
        );
    }

    private static void registerSetMaxInfectionRadius(
        CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("set_max_infection_radius")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("radius", integer(1))
                .executes(ModCommands::executeSetMaxInfectionRadius))
        );
    }

    private static void registerStopAllInfections(
        CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("stop_all_infections")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(ModCommands::executeStopAllInfections)

        );
    }

    private static int executeStopAllInfections(CommandContext<ServerCommandSource> context) {
        InfectionSystem.getInstance().stopAllInfectionCursors();

        return 1;
    }

    private static int executeStartSpread(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            ServerWorld serverWorld = player.getServerWorld();
            BlockPos playerPos = player.getBlockPos();
            int radius = getInteger(context, "radius");

            InfectionStarter.startInfection(serverWorld, playerPos, radius);

            source.sendFeedback(
                () -> Text.literal("§aInfection started in " + radius + " blocks radius."),
                true
            );

            return 1;

        } catch (Exception e) {
            source.sendError(Text.literal("§Error: " + e.getMessage()));
            return 0;
        }
    }

    private static int executeSetSpeed(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int speed = getInteger(context, "speed");

        InfectionConfig.BLOCKS_PER_TICK = speed;

        source.sendFeedback(
            () -> Text.literal("§aInfection speed set to " + speed + " blocks per tick."),
            true
        );

        return 1;
    }

    private static int executeSetDelay(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int delay = getInteger(context, "delay");

        InfectionConfig.TICK_DELAY = delay;

        source.sendFeedback(
            () -> Text.literal("§aInfection tick delay set to " + delay + " ticks."),
            true
        );

        return 1;
    }

    private static int executeSetMaxTransformations(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int transforms = getInteger(context, "transforms");
        InfectionConfig.MAX_TRANSFORMATIONS = transforms;

        source.sendFeedback(
            () -> Text.literal("§aMax infection transformations set to " + transforms + "."),
            true
        );

        return 1;
    }

    private static int executeSetMaxInfectionRadius(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int radius = getInteger(context, "radius");
        InfectionConfig.MAX_INFECTION_RADIUS = radius;

        source.sendFeedback(
            () -> Text.literal("§aMax infection radius set to " + radius + "."),
            true
        );

        return 1;
    }

    private static int executeEffectClear(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        String effectName = getString(context, "effect_name");

        Optional<ClearableEffect> effectOpt = ClearableEffect.get(effectName);

        if (effectOpt.isEmpty()) {
            source.sendError(Text.literal("§cInvalid effect name."));
            return 0;
        }

        ClearableEffect effectData = effectOpt.get();

        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            revokeEffectAdvancement(player, effectData);
            clearEffectFromPlayer(player, effectData, source);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }

    private static void revokeEffectAdvancement(ServerPlayerEntity player,
        ClearableEffect effectData) {
        Identifier advancementIdentifier = Identifier.of(effectData.advancementId);
        AdvancementEntry advancementEntry = Objects.requireNonNull(player.getServer())
            .getAdvancementLoader()
            .get(advancementIdentifier);

        if (advancementEntry != null) {
            AdvancementProgress progress = player.getAdvancementTracker()
                .getProgress(advancementEntry);
            if (progress.isAnyObtained()) {
                for (String criterion : progress.getObtainedCriteria()) {
                    player.getAdvancementTracker().revokeCriterion(advancementEntry, criterion);
                }
            }
        }
    }

    private static void clearEffectFromPlayer(ServerPlayerEntity player, ClearableEffect effectData,
        ServerCommandSource source) {
        boolean hadEffect = player.removeStatusEffect(effectData.effectEntry);

        if (hadEffect) {
            source.sendFeedback(
                () -> Text.literal("§aCleared effect '" + effectData.commandName + "'"),
                true
            );
        } else {
            source.sendFeedback(() -> Text.literal(
                    "§ePlayer did not have the '" + effectData.commandName + "' effect."),
                true);
        }
    }

    private static void registerGenerateGorge(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("palegarden")
            .then(literal("generate_gorge")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(ModCommands::executeGenerateGorge)));
    }

    private static int executeGenerateGorge(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            ServerWorld world = player.getServerWorld();
            BlockPos playerPos = player.getBlockPos();

            BlockPos surfacePos = world.getTopPosition(net.minecraft.world.Heightmap.Type.WORLD_SURFACE, playerPos);
            CometGorgeGenerator.generateGorge(world, surfacePos, world.getRandom());

            source.sendFeedback(
                    () -> Text.literal("§aComet gorge generated at: " +
                            surfacePos.getX() + ", " + surfacePos.getY() + ", " + surfacePos.getZ()),
                    true
            );

            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }

    private static void registerPathToCenterCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("path_to_center")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(ModCommands::executePathToCenter));
    }

    private static int executePathToCenter(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        try {
            ServerPlayerEntity player = source.getPlayerOrThrow();
            BlockPos start = player.getBlockPos();
            BlockPos end = InfectionStarter.getCometImpactSite();

            if (end == null) {
                source.sendError(Text.literal("§cNo active infection center found."));
                return 0;
            }

            ServerWorld world = source.getWorld();
            List<BlockPos> path = LabyrinthPathfinder.findPath(world, start, end);

            if (path.isEmpty()) {
                source.sendError(Text.literal("§cNo path found to the center."));
                return 0;
            }

            PathHighlighter.setPath(world, path);
            source.sendFeedback(() -> Text.literal("§aPath highlighted with " + path.size() + " steps."), true);

            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));

            return 0;
        }
    }

    private enum ClearableEffect {
        THIRD_HAND("third_hand", ModEffects.THIRD_HAND, "palegarden:obtained_third_hand"),
        GIANT_RIBCAGE("giant_ribcage", ModEffects.GIANT_RIBCAGE, "palegarden:obtained_giant_ribcage"),
        GOLDEN_METABOLISM("golden_metabolism", ModEffects.GOLDEN_METABOLISM, "palegarden:obtained_golden_metabolism");

        private static final Map<String, ClearableEffect> BY_NAME = Arrays.stream(values())
            .collect(Collectors.toMap(e -> e.commandName, Function.identity()));

        final String commandName;
        final RegistryEntry<StatusEffect> effectEntry;
        final String advancementId;

        ClearableEffect(String commandName, RegistryEntry<StatusEffect> effectEntry, String advancementId) {
            this.commandName = commandName;
            this.effectEntry = effectEntry;
            this.advancementId = advancementId;
        }

        public static Optional<ClearableEffect> get(String name) {
            return Optional.ofNullable(BY_NAME.get(name));
        }
    }
}
