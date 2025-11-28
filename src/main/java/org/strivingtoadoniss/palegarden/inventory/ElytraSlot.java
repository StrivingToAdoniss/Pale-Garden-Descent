package org.strivingtoadoniss.palegarden.inventory;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class ElytraSlot extends Slot {
    private final PlayerEntity player;

    public ElytraSlot(Inventory inventory, int index, int x, int y, PlayerEntity player) {
        super(inventory, index, x, y);
        this.player = player;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (!stack.isOf(Items.ELYTRA)) {
            return false;
        }

        ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);
        return chestStack.isEmpty() || !chestStack.isOf(Items.ELYTRA);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }
}
