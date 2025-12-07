package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.api.ClientModInitializer;

public class PalegardenClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModelLoader.register();
        ModEntityRenderers.initialize();
    }
}
