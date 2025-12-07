package org.strivingtoadoniss.palegarden.spread;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.List;

public class InfectionSystem {

    private static InfectionSystem instance;

    private final List<InfectionCursor> infectionCursors = new ArrayList<>();

    private InfectionSystem() {}

    public static InfectionSystem getInstance() {
        if (instance == null) {
            instance = new InfectionSystem();
        }
        return instance;
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> getInstance().tick());
    }

    public void addInfectionCursor(InfectionCursor cursor) {
        infectionCursors.add(cursor);
    }

    public void activateAllCleansingCursors() {
        for (InfectionCursor infectionCursor : infectionCursors) {
            infectionCursor.getPairedCleansingCursor().activate();
        }
    }

    private void tick() {
        infectionCursors.removeIf(cursor -> cursor.isFinished() && cursor.getPairedCleansingCursor().isFinished());
        infectionCursors.forEach(InfectionCursor::tick);

        for (InfectionCursor infectionCursor : infectionCursors) {
            CleansingCursor cleansingCursor = infectionCursor.getPairedCleansingCursor();
            if (cleansingCursor.isActive()) {
                cleansingCursor.tick();
            }
        }
    }

    public void stopAllInfectionCursors() {
        for (InfectionCursor cursor : infectionCursors) {
            cursor.finishImmediately();
        }
    }

    public void stopAllCleansingCursors() {
        for (InfectionCursor cursor : infectionCursors) {
            cursor.getPairedCleansingCursor().finishImmediately();
        }
    }
}
