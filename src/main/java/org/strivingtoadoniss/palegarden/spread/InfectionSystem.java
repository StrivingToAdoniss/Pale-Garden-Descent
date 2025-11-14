package org.strivingtoadoniss.palegarden.spread;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.List;

public class InfectionSystem {

    private static InfectionSystem instance;

    private final List<InfectionCursor> cursors = new ArrayList<>();

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

    public void addCursor(InfectionCursor cursor) {
        cursors.add(cursor);
    }

    private void tick() {
        cursors.removeIf(InfectionCursor::isFinished);
        cursors.forEach(InfectionCursor::tick);
    }

    public void stopAllInfections() {
        cursors.forEach(InfectionCursor::finishImmediately);
        cursors.clear();
    }
}
