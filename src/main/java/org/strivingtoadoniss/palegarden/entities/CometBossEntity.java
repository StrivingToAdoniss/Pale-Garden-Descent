package org.strivingtoadoniss.palegarden.entities;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.strivingtoadoniss.palegarden.spread.InfectionSystem;

public class CometBossEntity extends HostileEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private final ServerBossBar bossBar = new ServerBossBar(
        Text.translatable("entity.palegarden.comet_boss"),
        BossBar.Color.PURPLE,
        BossBar.Style.PROGRESS
    );

    public CometBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar.setDarkenSky(false);
        this.bossBar.setDragonMusic(false);
    }

    public static DefaultAttributeContainer.Builder createCometBossAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 200.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.0)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 1.0)
            .add(EntityAttributes.FOLLOW_RANGE, 0.0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 63;
            this.idleAnimationState.start(this.age);
        } else {
            this.idleAnimationTimeout--;
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (canTakeDamageFrom(world, source)) {
            return super.damage(world, source, amount);
        }

        return false;
    }

    private boolean canTakeDamageFrom(ServerWorld world, DamageSource source) {
        if (!(source.getAttacker() instanceof PlayerEntity player)) {
            return false;
        }

        return hasFireAspectWeapon(world, player);
    }

    private boolean hasFireAspectWeapon(ServerWorld world, PlayerEntity player) {
        ItemStack weapon = player.getMainHandStack();
        int fireAspectLevel = getFireAspectLevel(world, weapon);

        return fireAspectLevel > 0;
    }

    private int getFireAspectLevel(ServerWorld world, ItemStack weapon) {
        return EnchantmentHelper.getLevel(
            world.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(Enchantments.FIRE_ASPECT),
            weapon
        );
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (!this.getWorld().isClient) {
            InfectionSystem.getInstance().stopAllInfectionCursors();
            InfectionSystem.getInstance().activateAllCleansingCursors();
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void mobTick(ServerWorld world) {
        super.mobTick(world);
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void pushAwayFrom(Entity entity) {

    }
}
