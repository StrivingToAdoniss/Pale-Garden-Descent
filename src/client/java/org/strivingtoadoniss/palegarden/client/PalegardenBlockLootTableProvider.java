package org.strivingtoadoniss.palegarden.client;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import org.strivingtoadoniss.palegarden.ModBlocks;

public class PalegardenBlockLootTableProvider extends FabricBlockLootTableProvider {

    protected PalegardenBlockLootTableProvider(FabricDataOutput dataOutput,
        CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PALE_STONE);
        addDrop(ModBlocks.PALE_ROOTED_STONE);
    }
}
