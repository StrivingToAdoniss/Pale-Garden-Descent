package org.strivingtoadoniss.palegarden.client;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import org.strivingtoadoniss.palegarden.ModBlocks;

public class PalegardenBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public PalegardenBlockTagProvider(FabricDataOutput output,
        CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
            ModBlocks.PALE_STONE,
            ModBlocks.PALE_ROOTED_STONE
        );
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
            ModBlocks.PALE_STONE,
            ModBlocks.PALE_ROOTED_STONE
        );
    }
}
