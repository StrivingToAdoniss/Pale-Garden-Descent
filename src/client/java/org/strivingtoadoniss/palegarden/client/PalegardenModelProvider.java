package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ModelIds;
import org.strivingtoadoniss.palegarden.ModBlocks;

public class PalegardenModelProvider extends FabricModelProvider {

    public PalegardenModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PALE_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PALE_ROOTED_STONE);

        blockStateModelGenerator.registerParentedItemModel(
            ModBlocks.PALE_STONE,
            ModelIds.getBlockModelId(ModBlocks.PALE_STONE)
        );
        blockStateModelGenerator.registerParentedItemModel(
            ModBlocks.PALE_ROOTED_STONE,
            ModelIds.getBlockModelId(ModBlocks.PALE_ROOTED_STONE)
        );

        blockStateModelGenerator.registerParentedItemModel(
            ModBlocks.PALE_MUSHROOM,
            ModelIds.getBlockModelId(Blocks.BROWN_MUSHROOM)
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
