package org.strivingtoadoniss.palegarden.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.strivingtoadoniss.palegarden.inventory.ElytraInventoryAddon;
import org.strivingtoadoniss.palegarden.inventory.ElytraPlayerDuck;
import org.strivingtoadoniss.palegarden.inventory.PaleInventoryAddon;
import org.strivingtoadoniss.palegarden.inventory.PalePlayerDuck;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity implements PalePlayerDuck, ElytraPlayerDuck {

    @Unique
    private static final String PALE_INVENTORY_ADDON = "PaleInventoryAddon";
    @Unique
    private static final String ELYTRA_INVENTORY_ADDON = "ElytraInventoryAddon";
    @Unique
    private final PaleInventoryAddon paleInventoryAddon = new PaleInventoryAddon((PlayerEntity) (Object) this);
    @Unique
    private final ElytraInventoryAddon elytraInventoryAddon = new ElytraInventoryAddon((PlayerEntity) (Object) this);

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public PaleInventoryAddon getPaleInventoryAddon() {
        return this.paleInventoryAddon;
    }

    @Override
    public ElytraInventoryAddon getElytraInventoryAddon() {
        return this.elytraInventoryAddon;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeAddonNbt(NbtCompound nbt, CallbackInfo ci) {
        writePaleAddonNbt(nbt);
        writeElytraAddonNbt(nbt);
    }

    @Unique
    private void writeElytraAddonNbt(NbtCompound nbt) {
        NbtCompound elytraAddonNbt = new NbtCompound();
        this.elytraInventoryAddon.writeNbt(elytraAddonNbt, this.getRegistryManager());
        nbt.put(ELYTRA_INVENTORY_ADDON, elytraAddonNbt);
    }

    @Unique
    private void writePaleAddonNbt(NbtCompound nbt) {
        NbtCompound addonNbt = new NbtCompound();
        this.paleInventoryAddon.writeNbt(addonNbt, this.getRegistryManager());
        nbt.put(PALE_INVENTORY_ADDON, addonNbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readAddonNbt(NbtCompound nbt, CallbackInfo ci) {
        readPaleAddonNbt(nbt);
        readElytraAddonNbt(nbt);
    }

    @Unique
    private void readPaleAddonNbt(NbtCompound nbt) {
        if (nbt.contains(PALE_INVENTORY_ADDON)) {
            this.paleInventoryAddon.readNbt(nbt.getCompound(PALE_INVENTORY_ADDON), this.getRegistryManager());
        }
    }

    @Unique
    private void readElytraAddonNbt(NbtCompound nbt) {
        if (nbt.contains(ELYTRA_INVENTORY_ADDON)) {
            this.elytraInventoryAddon.readNbt(nbt.getCompound(ELYTRA_INVENTORY_ADDON), this.getRegistryManager());
        }
    }

    @Inject(method = "dropInventory", at = @At("TAIL"))
    private void dropAddonItems(CallbackInfo ci) {
        this.paleInventoryAddon.dropAll();
        this.elytraInventoryAddon.dropAll();
    }
}
