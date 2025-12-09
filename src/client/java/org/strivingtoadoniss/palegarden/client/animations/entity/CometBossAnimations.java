package org.strivingtoadoniss.palegarden.client.animations.entity;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class CometBossAnimations {

    public static final Animation COMET_BOSS_IDLE = Animation.Builder.create(3.1667F)
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.9583F, AnimationHelper.createScalingVector(0.9F, 1.0F, 0.9F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.9167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.2917F, AnimationHelper.createScalingVector(1.1F, 1.2F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.2917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.0F, AnimationHelper.createScalingVector(1.0F, 1.2F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.2917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone5", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.9583F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.375F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.875F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.25F, AnimationHelper.createScalingVector(1.2F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone7", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(2.2083F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.5F, AnimationHelper.createScalingVector(1.2F, 1.0F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.7083F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone8", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.625F, AnimationHelper.createScalingVector(1.0F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.9167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone9", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(2.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.75F, AnimationHelper.createScalingVector(1.3F, 1.0F, 1.3F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone11", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createScalingVector(1.1F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.2917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone12", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.5417F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.8333F, AnimationHelper.createScalingVector(1.2F, 1.2F, 0.9F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.125F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone13", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.1F, 1.2F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone14", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.4583F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.7083F, AnimationHelper.createScalingVector(1.1F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.0417F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone15", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.7917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.0417F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone16", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(2.125F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.3333F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.5417F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone17", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(2.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.5F, AnimationHelper.createScalingVector(1.2F, 1.0F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.7917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone18", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.7917F, AnimationHelper.createScalingVector(1.1F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone19", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.9167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.2917F, AnimationHelper.createScalingVector(1.1F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.5417F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone20", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(2.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.2083F, AnimationHelper.createScalingVector(1.4F, 1.0F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.4167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone21", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.8333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.125F, AnimationHelper.createScalingVector(1.4F, 1.0F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.4167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone22", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.9167F, AnimationHelper.createScalingVector(1.2F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.2083F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone23", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.9583F, AnimationHelper.createScalingVector(1.2F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone24", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createScalingVector(1.1F, 1.2F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.7917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone25", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.75F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0417F, AnimationHelper.createScalingVector(1.2F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone26", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone26", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0833F, AnimationHelper.createScalingVector(1.1F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.5417F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone27", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.4167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.9167F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.2083F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone28", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.2917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.7917F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone29", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.1667F, AnimationHelper.createScalingVector(1.1F, 1.1F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.625F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone30", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.7083F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone31", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.4167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.625F, AnimationHelper.createScalingVector(1.2F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.9167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone32", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.1667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.4583F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone33", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.125F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createScalingVector(1.1F, 1.0F, 1.1F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone34", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.2083F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.4583F, AnimationHelper.createScalingVector(0.9F, 1.0F, 0.8F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone36", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(1.2917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(1.7083F, AnimationHelper.createScalingVector(1.2F, 1.1F, 1.2F),
                Transformation.Interpolations.LINEAR),
            new Keyframe(2.125F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F),
                Transformation.Interpolations.LINEAR)
        ))
        .build();
}
