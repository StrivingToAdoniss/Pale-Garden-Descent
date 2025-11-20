package org.strivingtoadoniss.palegarden.client;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import org.strivingtoadoniss.palegarden.ModBlocks;
import org.strivingtoadoniss.palegarden.blocks.PaleMushroomBlock;

public class PalegardenBlockLootTableProvider extends FabricBlockLootTableProvider {

    protected PalegardenBlockLootTableProvider(FabricDataOutput dataOutput,
        CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PALE_STONE);
        addDrop(ModBlocks.PALE_ROOTED_STONE);
        addPaleMushroomDrop();
    }

    private void addPaleMushroomDrop() {
        Block block = ModBlocks.PALE_MUSHROOM;
        LootTable.Builder builder = LootTable.builder();
        
        for (int i = 1; i <= 4; i++) {
            builder.pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(block)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(i)))
                    )
                    .conditionally(
                        BlockStatePropertyLootCondition.builder(block)
                            .properties(StatePredicate.Builder.create()
                                .exactMatch(PaleMushroomBlock.AMOUNT, i)
                            )
                    )
            );
        }
        
        addDrop(block, builder);
    }
}
