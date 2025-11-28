package org.strivingtoadoniss.palegarden.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.strivingtoadoniss.palegarden.inventory.ElytraPlayerDuck;

@Mixin(ScreenHandler.class)
public abstract class MixinScreenHandler {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void interceptChestplateInteraction(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (!((ScreenHandler) (Object) this instanceof PlayerScreenHandler) || slotIndex < 0) {
            return;
        }

        if (slotIndex == 6) { //chest slot index
            ItemStack itemAttemptingToInsert = getItemBeingInserted(actionType, button, player);

            if (shouldCancelElytraPlacement(itemAttemptingToInsert, player)) {
                ci.cancel();
            }
        }
    }

    @Unique
    private ItemStack getItemBeingInserted(SlotActionType actionType, int button, PlayerEntity player) {
        if (actionType == SlotActionType.PICKUP) {
            return this.getCursorStack();
        } else if (actionType == SlotActionType.SWAP) {
            PlayerInventory inv = player.getInventory();
            if (button >= 0 && button < 9) {
                return inv.getStack(button);
            }
        }
        return ItemStack.EMPTY;
    }

    @Unique
    private boolean shouldCancelElytraPlacement(ItemStack itemStack, PlayerEntity player) {
        if (itemStack.isOf(Items.ELYTRA) && player instanceof ElytraPlayerDuck duck) {
            ItemStack customElytra = duck.getElytraInventoryAddon().getStack(0);
            return !customElytra.isEmpty();
        }
        return false;
    }

    @Unique
    private ItemStack getCursorStack() {
        return ((ScreenHandler) (Object) this).getCursorStack();
    }
}
