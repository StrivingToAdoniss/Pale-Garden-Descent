package org.strivingtoadoniss.palegarden;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.strivingtoadoniss.palegarden.config.InfectionConfig;
import org.strivingtoadoniss.palegarden.spread.InfectionStarter;

public class ModCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerStartSpreadCommand(dispatcher);
            registerSetSpeedCommand(dispatcher);
            registerSetDelayCommand(dispatcher);
            registerSetMaxTransformations(dispatcher);
            registerSetMaxInfectionRadius(dispatcher);
        });
    }

    private static void registerStartSpreadCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("start_spread")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("radius", integer(5))
                .executes(ModCommands::executeStartSpread))
        );
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

    private static void registerSetMaxTransformations(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("set_max_transformations")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("transforms", integer(1))
                .executes(ModCommands::executeSetMaxTransformations))
        );
    }

    private static void registerSetMaxInfectionRadius(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("set_max_infection_radius")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("radius", integer(1))
                .executes(ModCommands::executeSetMaxInfectionRadius))
        );
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
}
