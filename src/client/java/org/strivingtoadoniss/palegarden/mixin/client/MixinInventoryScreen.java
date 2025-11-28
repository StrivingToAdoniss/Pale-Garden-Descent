package org.strivingtoadoniss.palegarden.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.strivingtoadoniss.palegarden.ModEffects;

import org.strivingtoadoniss.palegarden.mixin.SlotAccessor;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen extends HandledScreen<PlayerScreenHandler> {

    @Unique
    private static final Identifier SLOT_TEXTURE = Identifier.of("container/slot");
    @Unique
    private static final Identifier INVENTORY_TEXTURE = Identifier.ofVanilla("textures/gui/container/inventory.png");

    protected MixinInventoryScreen(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void adjustBackgroundHeight(CallbackInfo ci) {
        if (this.client != null && this.client.player != null
                && this.client.player.hasStatusEffect(ModEffects.THIRD_HAND)) {
            this.backgroundHeight = 184;
        } else {
            this.backgroundHeight = 166;
        }
    }

    @Inject(method = "getRecipeBookButtonPos", at = @At("RETURN"), cancellable = true)
    private void adjustRecipeBookButtonPos(CallbackInfoReturnable<ScreenPos> cir) {
        if (this.client != null && this.client.player != null
            && this.client.player.hasStatusEffect(ModEffects.THIRD_HAND)) {
            cir.setReturnValue(new ScreenPos(this.x + 104, this.height / 2 - 31));
        }
    }

    @Inject(method = "drawBackground", at = @At("TAIL"), cancellable = true)
    private void drawExtendedBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        ci.cancel();

        handleThirdHandEffect(context);
        handleGiantRibcageEffect(context);
    }

    @Unique
    private void handleThirdHandEffect(DrawContext context) {
        boolean hasThirdHand = this.client != null && this.client.player != null
                && this.client.player.hasStatusEffect(ModEffects.THIRD_HAND);

        if (hasThirdHand) {
            addNewSlots();
            drawNewRow(context);
        } else {
            hideNewSlots();
        }
    }

    @Unique
    private void handleGiantRibcageEffect(DrawContext context) {
        boolean hasGiantRibcage = this.client != null && this.client.player != null
                && this.client.player.hasStatusEffect(ModEffects.GIANT_RIBCAGE);

        if (hasGiantRibcage) {
            showElytraSlot();
            drawNewElytraSlot(context);
        } else {
            hideElytraSlot();
        }
    }

    @Unique
    private void drawNewElytraSlot(DrawContext context) {
        context.drawGuiTexture(RenderLayer::getGuiTextured, SLOT_TEXTURE, this.x + 4 + 4 * 18,
            this.y + 25, 18, 18);
    }

    @Unique
    private void hideNewSlots() {
        resetStandardSlotsToOriginalPositions();
        hideExtraSlotsOffScreen();
    }

    @Unique
    private void resetStandardSlotsToOriginalPositions() {
        for (Slot slot : this.handler.slots) {
            if (slot.inventory instanceof PlayerInventory) {
                if (slot.id >= 9 && slot.id <= 35) {
                    resetMainInventorySlot(slot);
                } else if (slot.id >= 36 && slot.id <= 44) {
                    resetHotbarSlot(slot);
                }
            }
        }
    }

    @Unique
    private void resetMainInventorySlot(Slot slot) {
        int row = (slot.id - 9) / 9;
        int col = (slot.id - 9) % 9;
        ((SlotAccessor) slot).setX(8 + col * 18);
        ((SlotAccessor) slot).setY(84 + row * 18);
    }

    @Unique
    private void resetHotbarSlot(Slot slot) {
        int col = slot.id - 36;
        ((SlotAccessor) slot).setX(8 + col * 18);
        ((SlotAccessor) slot).setY(142);
    }

    @Unique
    private void hideExtraSlotsOffScreen() {
        int startIndex = 46;
        if (startIndex < this.handler.slots.size()) {
            for (int i = 0; i < 9; i++) {
                if (startIndex + i < this.handler.slots.size()) {
                    Slot slot = this.handler.slots.get(startIndex + i);
                    ((SlotAccessor) slot).setX(-1000);
                    ((SlotAccessor) slot).setY(-1000);
                }
            }
        }
    }

    @Unique
    private void showElytraSlot() {
        int elytraSlotIndex = 55;
        if (elytraSlotIndex < this.handler.slots.size()) {
            Slot slot = this.handler.slots.get(elytraSlotIndex);
            ((SlotAccessor) slot).setX(5 + 4 * 18);
            ((SlotAccessor) slot).setY(26);
        }
    }

    @Unique
    private void hideElytraSlot() {
        int elytraSlotIndex = 55;
        if (elytraSlotIndex < this.handler.slots.size()) {
            Slot slot = this.handler.slots.get(elytraSlotIndex);
            ((SlotAccessor) slot).setX(-1000);
            ((SlotAccessor) slot).setY(-1000);
        }
    }

    @Unique
    private void addNewSlots() {
        shiftStandardSlots();
        positionNewSlots();
    }

    @Unique
    private void shiftStandardSlots() {
        for (Slot slot : this.handler.slots) {
            if (slot.inventory instanceof PlayerInventory) {
                if (slot.id >= 9 && slot.id <= 35) {
                    shiftMainInventorySlot(slot);
                } else if (slot.id >= 36 && slot.id <= 44) {
                    shiftHotbarSlot(slot);
                }
            }
        }
    }

    @Unique
    private void shiftMainInventorySlot(Slot slot) {
        int row = (slot.id - 9) / 9;
        int col = (slot.id - 9) % 9;
        ((SlotAccessor) slot).setX(8 + col * 18);
        ((SlotAccessor) slot).setY(102 + row * 18);
    }

    @Unique
    private void shiftHotbarSlot(Slot slot) {
        int col = slot.id - 36;
        ((SlotAccessor) slot).setX(8 + col * 18);
        ((SlotAccessor) slot).setY(160);
    }

    @Unique
    private void positionNewSlots() {
        int startIndex = 46;
        if (startIndex < this.handler.slots.size()) {
            for (int i = 0; i < 9; i++) {
                if (startIndex + i < this.handler.slots.size()) {
                    Slot slot = this.handler.slots.get(startIndex + i);
                    ((SlotAccessor) slot).setX(8 + i * 18);
                    ((SlotAccessor) slot).setY(84);
                }
            }
        }
    }

    @Unique
    private void drawNewRow(DrawContext context) {
        drawTopSection(context);
        drawNewInventoryRow(context);
        drawMainInventoryArea(context);
        drawBottomSection(context);
    }

    @Unique
    private void drawTopSection(DrawContext context) {
        context.drawTexture(RenderLayer::getGuiTextured, INVENTORY_TEXTURE,
            this.x, this.y,
            0, 0,
            176, 7,
            256, 256
        );
    }

    @Unique
    private void drawNewInventoryRow(DrawContext context) {
        context.drawTexture(RenderLayer::getGuiTextured, INVENTORY_TEXTURE,
                this.x, this.y + 83,
                0, 83,
                176, 18,
                256, 256
        );
    }

    @Unique
    private void drawMainInventoryArea(DrawContext context) {
        context.drawTexture(RenderLayer::getGuiTextured, INVENTORY_TEXTURE,
                this.x, this.y + 101,
                0, 83,
                176, 76,
                256, 256
        );
    }

    @Unique
    private void drawBottomSection(DrawContext context) {
        context.drawTexture(RenderLayer::getGuiTextured, INVENTORY_TEXTURE,
                this.x, this.y + 159,
                0, 141,
                176, 25,
                256, 256
        );
    }
}
