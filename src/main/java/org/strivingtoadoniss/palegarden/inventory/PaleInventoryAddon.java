package org.strivingtoadoniss.palegarden.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;

public class PaleInventoryAddon implements Inventory {
    public static final int SLOT_COUNT = 9;
    private final PlayerEntity player;
    private final DefaultedList<ItemStack> stacks;

    public PaleInventoryAddon(PlayerEntity player) {
        this.player = player;
        this.stacks = DefaultedList.ofSize(SLOT_COUNT, ItemStack.EMPTY);
    }

    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, this.stacks, registryLookup);
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, this.stacks, registryLookup);
    }

    public void dropAll() {
        for (int i = 0; i < this.stacks.size(); ++i) {
            ItemStack stack = this.stacks.get(i);
            if (!stack.isEmpty()) {
                this.player.dropItem(stack, true, false);
                this.stacks.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public int size() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.stacks, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
    }

    @Override
    public void markDirty() {
        // no implementation
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }
}
