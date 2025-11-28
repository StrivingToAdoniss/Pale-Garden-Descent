package org.strivingtoadoniss.palegarden.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.util.Formatting;

public class PaleMushroomItem extends BlockItem {

    private static final FoodComponent FOOD_COMPONENT = new FoodComponent.Builder()
        .nutrition(3)
        .saturationModifier(0.3f)
        .alwaysEdible()
        .build();

    public PaleMushroomItem(Block block, Settings settings) {
        super(block, settings.food(FOOD_COMPONENT));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.palegarden.pale_mushroom.tooltip")
            .formatted(Formatting.GOLD));
    }
}
