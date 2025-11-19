package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PalegardenDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(PalegardenBlockLootTableProvider::new);
        pack.addProvider(PalegardenBlockTagProvider::new);
        pack.addProvider(PalegardenModelProvider::new);
    }
}
