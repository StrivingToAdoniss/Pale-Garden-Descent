package org.strivingtoadoniss.palegarden;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.blocks.CometCoreBlock;
import org.strivingtoadoniss.palegarden.blocks.PaleMushroomBlock;
import org.strivingtoadoniss.palegarden.blocks.RegeneratingPaleLogBlock;
import org.strivingtoadoniss.palegarden.items.PaleMushroomItem;

public class ModBlocks {

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.PALE_STONE.asItem());
            itemGroup.add(ModBlocks.PALE_ROOTED_STONE.asItem());
            itemGroup.add(ModBlocks.PALE_MUSHROOM.asItem());
            itemGroup.add(ModBlocks.COMET_CORE.asItem());
        });
    }

    private static Block register(String name, Function<Settings, Block> blockFactory, AbstractBlock.Settings settings,
            boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));

            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Block registerWithCustomItem(String name, Function<Settings, Block> blockFactory,
            AbstractBlock.Settings settings, BiFunction<Block, Item.Settings, BlockItem> itemFactory) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        RegistryKey<Item> itemKey = keyOfItem(name);
        BlockItem blockItem = itemFactory.apply(block, new Item.Settings().registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, blockItem);
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Palegarden.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Palegarden.MOD_ID, name));
    }

    public static final Block PALE_STONE = register(
        "pale_stone",
        Block::new,
        AbstractBlock.Settings.copy(Blocks.STONE)
            .strength(-1.0f, 3600000.0f)
            .dropsNothing(),
        true
    );

    public static final Block PALE_ROOTED_STONE = register(
        "pale_rooted_stone",
        Block::new,
        AbstractBlock.Settings.copy(Blocks.STONE)
            .strength(-1.0f, 3600000.0f)
            .dropsNothing(),
        true
    );

    public static final Block PALE_MUSHROOM = registerWithCustomItem(
        "pale_mushroom",
        PaleMushroomBlock::new,
        AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM),
        PaleMushroomItem::new);

    public static final Block REGENERATING_PALE_LOG = register(
        "regenerating_pale_log",
        RegeneratingPaleLogBlock::new,
        AbstractBlock.Settings.copy(Blocks.PALE_OAK_LOG)
            .mapColor(MapColor.CLEAR)
            .noCollision()
            .strength(-1.0f, 3600000.0f)
            .air()
            .dropsNothing()
            .nonOpaque(),
        false
    );

    public static final Block COMET_CORE = register(
        "comet_core",
        CometCoreBlock::new,
        AbstractBlock.Settings.copy(Blocks.SCULK)
            .luminance(state -> 15)
            .strength(50.0f, 1200.0f),
        true
    );
}
