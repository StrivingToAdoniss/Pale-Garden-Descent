package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Blocks;

public class ModRegistries {

    public static void register() {
        registerFlammableBlocks();
    }

    private static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(Blocks.PALE_OAK_LOG, 0, 0);
        registry.add(Blocks.PALE_OAK_WOOD, 0, 0);
        registry.add(Blocks.STRIPPED_PALE_OAK_LOG, 0, 0);
        registry.add(Blocks.STRIPPED_PALE_OAK_WOOD, 0, 0);
        registry.add(Blocks.PALE_OAK_LEAVES, 0, 0);
        registry.add(Blocks.PALE_HANGING_MOSS, 0, 0);
        registry.add(Blocks.PALE_MOSS_BLOCK, 0, 0);
        registry.add(Blocks.PALE_MOSS_CARPET, 0, 0);
    }
}
