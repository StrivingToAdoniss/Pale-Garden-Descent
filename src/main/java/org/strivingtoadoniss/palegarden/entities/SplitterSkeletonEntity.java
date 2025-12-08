package org.strivingtoadoniss.palegarden.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.strivingtoadoniss.palegarden.ModEffects;

public class SplitterSkeletonEntity extends SkeletonEntity {

    public SplitterSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        PersistentProjectileEntity projectile = super.createArrowProjectile(arrow, damageModifier, shotFrom);

        if (projectile instanceof ArrowEntity arrowEntity) {
            arrowEntity.addEffect(new StatusEffectInstance(ModEffects.ROOTED, 100, 0));
        }

        return projectile;
    }
}
