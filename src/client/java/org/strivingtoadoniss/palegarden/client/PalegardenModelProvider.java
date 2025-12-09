package org.strivingtoadoniss.palegarden.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariant;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.VariantSettings;
import net.minecraft.client.data.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.ModBlocks;

public class PalegardenModelProvider extends FabricModelProvider {

    public PalegardenModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PALE_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PALE_ROOTED_STONE);

        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(
                ModBlocks.REGENERATING_PALE_LOG,
                BlockStateVariant.create().put(
                    VariantSettings.MODEL, Identifier.of("minecraft", "block/air")
                )
            )
        );


        blockStateModelGenerator.registerParentedItemModel(
            ModBlocks.PALE_STONE,
            ModelIds.getBlockModelId(ModBlocks.PALE_STONE)
        );
        blockStateModelGenerator.registerParentedItemModel(
            ModBlocks.PALE_ROOTED_STONE,
            ModelIds.getBlockModelId(ModBlocks.PALE_ROOTED_STONE)
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModBlocks.PALE_MUSHROOM.asItem(), Models.GENERATED);
    }
}
