package org.strivingtoadoniss.palegarden.mixin;

import static net.minecraft.entity.LivingEntity.canGlideWith;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.strivingtoadoniss.palegarden.ModEffects;
import org.strivingtoadoniss.palegarden.inventory.ElytraPlayerDuck;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    protected MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);
    @Shadow
    protected int glidingTicks;
    @Shadow
    protected abstract boolean canGlide();
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "canGlide", at = @At("RETURN"), cancellable = true)
    private void customCanGlide(CallbackInfoReturnable<Boolean> cir) {
        if (shouldCheckCustomGliding() && hasValidCustomElytra()) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private boolean shouldCheckCustomGliding() {
        return !this.isOnGround() && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION);
    }

    @Unique
    private boolean hasValidCustomElytra() {
        if (!(getPlayerWithRibcage() instanceof ElytraPlayerDuck duck)) {
            return false;
        }
        ItemStack elytraStack = duck.getElytraInventoryAddon().getStack(0);

        return !elytraStack.isEmpty()
            && elytraStack.isOf(Items.ELYTRA)
            && !elytraStack.willBreakNextUse();
    }

    @Inject(method = "tickGliding", at = @At("HEAD"), cancellable = true)
    private void customTickGliding(CallbackInfo ci) {
        PlayerEntity player;
        if (shouldSkipCustomGliding() || (player = getPlayerWithRibcage()) == null) {
            return;
        }

        processCustomElytraGliding(player, ci);
    }

    @Unique
    private PlayerEntity getPlayerWithRibcage() {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof PlayerEntity player && this.hasStatusEffect(ModEffects.GIANT_RIBCAGE)
            && player instanceof ElytraPlayerDuck)) {
            return null;
        }
        return player;
    }

    @Unique
    private void processCustomElytraGliding(PlayerEntity player, CallbackInfo ci) {
        ElytraPlayerDuck duck = (ElytraPlayerDuck) player;
        ItemStack customStack = duck.getElytraInventoryAddon().getStack(0);

        if (!isValidCustomElytra(customStack)) {
            return;
        }

        handleCustomGliding(player, customStack);
        ci.cancel();
    }

    @Unique
    private boolean shouldSkipCustomGliding() {
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            if (canGlideWith(this.getEquippedStack(slot), slot)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private boolean isValidCustomElytra(ItemStack stack) {
        return !stack.isEmpty() && stack.contains(DataComponentTypes.GLIDER);
    }

    @Unique
    private void handleCustomGliding(PlayerEntity player, ItemStack customStack) {
        this.limitFallDistance();
        if (!this.getWorld().isClient) {
            if (!this.canGlide()) {
                this.setFlag(Entity.GLIDING_FLAG_INDEX, false);
                return;
            }

            int i = this.glidingTicks + 1;
            if (i % 10 == 0 && (i / 10) % 2 == 0) {
                customStack.damage(1, player, EquipmentSlot.CHEST);
                this.emitGameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
    }
}
