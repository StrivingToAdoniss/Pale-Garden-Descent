package org.strivingtoadoniss.palegarden.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.strivingtoadoniss.palegarden.inventory.PaleInventoryAddon;
import org.strivingtoadoniss.palegarden.inventory.PalePlayerDuck;

@Mixin(PlayerScreenHandler.class)
public abstract class MixinPlayerScreenHandler extends AbstractRecipeScreenHandler {

    protected MixinPlayerScreenHandler(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addThirdHandSlots(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        PaleInventoryAddon addon = ((PalePlayerDuck) owner).getPaleInventoryAddon();

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(addon, i, -1000, -1000));
        }
    }
}
