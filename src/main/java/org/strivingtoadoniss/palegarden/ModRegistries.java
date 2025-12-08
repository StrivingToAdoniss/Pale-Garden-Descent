package org.strivingtoadoniss.palegarden;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Blocks;
import org.strivingtoadoniss.palegarden.mixin.AbstractBlockAccessor;

public class ModRegistries {

    private ModRegistries() {}

    public static void register() {
        registerFlammableBlocks();
        registerResistibleBlocks();
    }

    private static void registerResistibleBlocks() {
        AbstractBlockAccessor paleLog = (AbstractBlockAccessor) Blocks.PALE_OAK_LOG;
        AbstractBlockAccessor paleLeaves = (AbstractBlockAccessor) Blocks.PALE_OAK_LEAVES;

        paleLog.setResistance(3600000.0f);
        paleLeaves.setResistance(3600000.0f);
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
