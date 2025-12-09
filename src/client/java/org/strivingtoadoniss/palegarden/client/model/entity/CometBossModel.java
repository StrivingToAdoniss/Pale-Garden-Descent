package org.strivingtoadoniss.palegarden.client.model.entity;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.strivingtoadoniss.palegarden.Palegarden;
import org.strivingtoadoniss.palegarden.client.animations.entity.CometBossAnimations;
import org.strivingtoadoniss.palegarden.client.render.entity.CometBossEntityRenderState;

public class CometBossModel extends EntityModel<CometBossEntityRenderState> {

    public static final EntityModelLayer COMET_BOSS = new EntityModelLayer(
        Identifier.of(Palegarden.MOD_ID, "comet_boss"), "main");

    private final ModelPart bone;
    private final ModelPart RootOne;
    private final ModelPart bone2;
    private final ModelPart bone4;
    private final ModelPart bone3;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart bone7;
    private final ModelPart bone10;
    private final ModelPart bone9;
    private final ModelPart bone8;
    private final ModelPart RootTwo;
    private final ModelPart bone11;
    private final ModelPart bone12;
    private final ModelPart bone13;
    private final ModelPart bone14;
    private final ModelPart bone15;
    private final ModelPart bone16;
    private final ModelPart bone17;
    private final ModelPart bone18;
    private final ModelPart RootThree;
    private final ModelPart bone26;
    private final ModelPart bone25;
    private final ModelPart bone24;
    private final ModelPart bone23;
    private final ModelPart bone22;
    private final ModelPart bone21;
    private final ModelPart bone20;
    private final ModelPart bone19;
    private final ModelPart Heart;
    private final ModelPart bone27;
    private final ModelPart bone28;
    private final ModelPart bone29;
    private final ModelPart bone30;
    private final ModelPart bone31;
    private final ModelPart bone32;
    private final ModelPart bone33;
    private final ModelPart bone36;
    private final ModelPart bone35;
    private final ModelPart bone34;

    public CometBossModel(ModelPart root) {
        super(root, RenderLayer::getEntityCutout);
        this.bone = root.getChild("bone");
        this.RootOne = this.bone.getChild("RootOne");
        this.bone2 = this.RootOne.getChild("bone2");
        this.bone4 = this.RootOne.getChild("bone4");
        this.bone3 = this.RootOne.getChild("bone3");
        this.bone5 = this.RootOne.getChild("bone5");
        this.bone6 = this.RootOne.getChild("bone6");
        this.bone7 = this.RootOne.getChild("bone7");
        this.bone10 = this.RootOne.getChild("bone10");
        this.bone9 = this.RootOne.getChild("bone9");
        this.bone8 = this.RootOne.getChild("bone8");
        this.RootTwo = this.bone.getChild("RootTwo");
        this.bone11 = this.RootTwo.getChild("bone11");
        this.bone12 = this.RootTwo.getChild("bone12");
        this.bone13 = this.RootTwo.getChild("bone13");
        this.bone14 = this.RootTwo.getChild("bone14");
        this.bone15 = this.RootTwo.getChild("bone15");
        this.bone16 = this.RootTwo.getChild("bone16");
        this.bone17 = this.RootTwo.getChild("bone17");
        this.bone18 = this.RootTwo.getChild("bone18");
        this.RootThree = this.bone.getChild("RootThree");
        this.bone26 = this.RootThree.getChild("bone26");
        this.bone25 = this.RootThree.getChild("bone25");
        this.bone24 = this.RootThree.getChild("bone24");
        this.bone23 = this.RootThree.getChild("bone23");
        this.bone22 = this.RootThree.getChild("bone22");
        this.bone21 = this.RootThree.getChild("bone21");
        this.bone20 = this.RootThree.getChild("bone20");
        this.bone19 = this.RootThree.getChild("bone19");
        this.Heart = this.bone.getChild("Heart");
        this.bone27 = this.Heart.getChild("bone27");
        this.bone28 = this.Heart.getChild("bone28");
        this.bone29 = this.Heart.getChild("bone29");
        this.bone30 = this.Heart.getChild("bone30");
        this.bone31 = this.Heart.getChild("bone31");
        this.bone32 = this.Heart.getChild("bone32");
        this.bone33 = this.Heart.getChild("bone33");
        this.bone36 = this.Heart.getChild("bone36");
        this.bone35 = this.Heart.getChild("bone35");
        this.bone34 = this.Heart.getChild("bone34");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 24.0F, 0.0F));

        ModelPartData RootOne = bone.addChild("RootOne", ModelPartBuilder.create(), ModelTransform.of(-2.0F, -13.0F, 0.0F, 0.0F, -0.9163F, 0.0F));

        ModelPartData bone2 = RootOne.addChild("bone2", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -3.0F, 2.0F));

        ModelPartData cube_r1 = bone2.addChild("cube_r1", ModelPartBuilder.create().uv(18, 36).cuboid(-0.4327F, -0.31F, -1.4437F, 1.0F, 3.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5106F, 0.3833F, 0.1394F));

        ModelPartData bone4 = RootOne.addChild("bone4", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, 0.0F));

        ModelPartData cube_r2 = bone4.addChild("cube_r2", ModelPartBuilder.create().uv(22, 39).cuboid(-0.0445F, -0.0169F, -1.3164F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 2.0F, 5.0F, -2.062F, 0.4959F, 0.1322F));

        ModelPartData bone3 = RootOne.addChild("bone3", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, 0.0F));

        ModelPartData cube_r3 = bone3.addChild("cube_r3", ModelPartBuilder.create().uv(38, 35).cuboid(-1.2482F, -3.1978F, -0.0388F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.4315F, 0.4664F, 0.1811F));

        ModelPartData bone5 = RootOne.addChild("bone5", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -2.0F, 5.0F));

        ModelPartData cube_r4 = bone5.addChild("cube_r4", ModelPartBuilder.create().uv(30, 36).cuboid(-0.5114F, -2.2778F, -1.7873F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, -1.0F, -2.2494F, 0.418F, 0.1103F));

        ModelPartData bone6 = RootOne.addChild("bone6", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 2.0F, 8.0F));

        ModelPartData cube_r5 = bone6.addChild("cube_r5", ModelPartBuilder.create().uv(34, 26).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.4894F, 0.3804F, 0.1561F));

        ModelPartData bone7 = RootOne.addChild("bone7", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 12.0F, 8.0F));

        ModelPartData cube_r6 = bone7.addChild("cube_r6", ModelPartBuilder.create().uv(26, 26).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -4.0F, 0.0F, 0.0928F, 0.3477F, 0.0317F));

        ModelPartData bone10 = RootOne.addChild("bone10", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, 2.0F));

        ModelPartData cube_r7 = bone10.addChild("cube_r7", ModelPartBuilder.create().uv(4, 40).cuboid(-0.3538F, 0.2725F, -0.5254F, 1.0F, 1.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.9076F, 0.3791F, 0.1987F));

        ModelPartData bone9 = RootOne.addChild("bone9", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 12.0F, 8.0F));

        ModelPartData cube_r8 = bone9.addChild("cube_r8", ModelPartBuilder.create().uv(34, 15).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0381F, 0.3477F, 0.0317F));

        ModelPartData bone8 = RootOne.addChild("bone8", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, 2.0F));

        ModelPartData cube_r9 = bone8.addChild("cube_r9", ModelPartBuilder.create().uv(30, 39).cuboid(-0.9387F, -0.465F, -1.2146F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, 1.0F, -1.8242F, 0.4664F, 0.1811F));

        ModelPartData RootTwo = bone.addChild("RootTwo", ModelPartBuilder.create(), ModelTransform.of(2.0F, -13.0F, -1.0F, 0.0F, 0.8727F, 0.0F));

        ModelPartData bone11 = RootTwo.addChild("bone11", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, 0.0F));

        ModelPartData cube_r10 = bone11.addChild("cube_r10", ModelPartBuilder.create().uv(4, 37).cuboid(-1.2482F, -3.1978F, -0.0388F, 1.0F, 1.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.4752F, 0.4664F, 0.1811F));

        ModelPartData bone12 = RootTwo.addChild("bone12", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, 0.0F));

        ModelPartData cube_r11 = bone12.addChild("cube_r11", ModelPartBuilder.create().uv(4, 37).cuboid(-0.3489F, -2.5475F, -0.8334F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-1.0F, 2.0F, 1.0F, -0.8057F, 0.4993F, 0.2133F));

        ModelPartData bone13 = RootTwo.addChild("bone13", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -3.0F, 4.0F));

        ModelPartData cube_r12 = bone13.addChild("cube_r12", ModelPartBuilder.create().uv(12, 39).cuboid(-0.0445F, -0.0169F, -1.3164F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 1.0F, -2.062F, 0.4959F, 0.1322F));

        ModelPartData bone14 = RootTwo.addChild("bone14", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -2.0F, 5.0F));

        ModelPartData cube_r13 = bone14.addChild("cube_r13", ModelPartBuilder.create().uv(26, 36).cuboid(-0.5114F, -2.2778F, -1.7873F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(-1.0F, 0.0F, -1.0F, -2.2494F, 0.418F, 0.1103F));

        ModelPartData bone15 = RootTwo.addChild("bone15", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 1.0F, 7.0F));

        ModelPartData cube_r14 = bone15.addChild("cube_r14", ModelPartBuilder.create().uv(34, 21).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 1.0F, 0.4894F, 0.3804F, 0.1561F));

        ModelPartData bone16 = RootTwo.addChild("bone16", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 7.0F, 8.0F));

        ModelPartData cube_r15 = bone16.addChild("cube_r15", ModelPartBuilder.create().uv(18, 26).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 1.0F, 0.0F, 0.0928F, 0.3477F, 0.0317F));

        ModelPartData bone17 = RootTwo.addChild("bone17", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 13.0F, 8.0F));

        ModelPartData cube_r16 = bone17.addChild("cube_r16", ModelPartBuilder.create().uv(10, 33).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.0381F, 0.3477F, 0.0317F));

        ModelPartData bone18 = RootTwo.addChild("bone18", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -4.0F, 4.0F));

        ModelPartData cube_r17 = bone18.addChild("cube_r17", ModelPartBuilder.create().uv(26, 39).cuboid(-0.9387F, -0.465F, -1.2146F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -1.0F, -1.8679F, 0.4664F, 0.1811F));

        ModelPartData RootThree = bone.addChild("RootThree", ModelPartBuilder.create(), ModelTransform.of(2.0F, -13.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData bone26 = RootThree.addChild("bone26", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -3.0F, 2.0F));

        ModelPartData cube_r18 = bone26.addChild("cube_r18", ModelPartBuilder.create().uv(34, 35).cuboid(-0.4327F, -0.31F, -1.4437F, 1.0F, 3.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6851F, 0.3833F, 0.1394F));

        ModelPartData bone25 = RootThree.addChild("bone25", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -3.0F, 2.0F));

        ModelPartData cube_r19 = bone25.addChild("cube_r19", ModelPartBuilder.create().uv(0, 37).cuboid(-1.2482F, -3.1978F, -0.0388F, 1.0F, 2.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -1.0F, -2.0F, -1.4315F, 0.4664F, 0.1811F));

        ModelPartData bone24 = RootThree.addChild("bone24", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -3.0F, 4.0F));

        ModelPartData cube_r20 = bone24.addChild("cube_r20", ModelPartBuilder.create().uv(8, 39).cuboid(-0.0445F, -0.0169F, -1.3164F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 1.0F, -2.062F, 0.4959F, 0.1322F));

        ModelPartData bone23 = RootThree.addChild("bone23", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -2.0F, 6.0F));

        ModelPartData cube_r21 = bone23.addChild("cube_r21", ModelPartBuilder.create().uv(22, 36).cuboid(-0.5114F, -2.2778F, -1.7873F, 1.0F, 2.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(-1.0F, 0.0F, -2.0F, -2.2494F, 0.418F, 0.1103F));

        ModelPartData bone22 = RootThree.addChild("bone22", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 0.0F, 7.0F));

        ModelPartData cube_r22 = bone22.addChild("cube_r22", ModelPartBuilder.create().uv(34, 5).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 1.0F, 0.4894F, 0.3804F, 0.1561F));

        ModelPartData bone21 = RootThree.addChild("bone21", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 8.0F, 8.0F));

        ModelPartData cube_r23 = bone21.addChild("cube_r23", ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0928F, 0.3477F, 0.0317F));

        ModelPartData bone20 = RootThree.addChild("bone20", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 12.0F, 8.0F));

        ModelPartData cube_r24 = bone20.addChild("cube_r24", ModelPartBuilder.create().uv(10, 27).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0381F, 0.3477F, 0.0317F));

        ModelPartData bone19 = RootThree.addChild("bone19", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -3.0F, 2.0F));

        ModelPartData cube_r25 = bone19.addChild("cube_r25", ModelPartBuilder.create().uv(26, 10).cuboid(-0.9387F, -0.465F, -1.2146F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 1.0F, -1.8242F, 0.4664F, 0.1811F));

        ModelPartData Heart = bone.addChild("Heart", ModelPartBuilder.create(), ModelTransform.of(-1.0F, -3.0F, -1.0F, 0.1777F, 0.0376F, 0.1705F));

        ModelPartData bone27 = Heart.addChild("bone27", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r26 = bone27.addChild("cube_r26", ModelPartBuilder.create().uv(0, 19).cuboid(-0.5169F, -8.703F, -8.8119F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 6.0F, 1.0F, -1.1304F, -0.1926F, 0.031F));

        ModelPartData bone28 = Heart.addChild("bone28", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r27 = bone28.addChild("cube_r27", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5169F, -9.703F, -11.8119F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, 0.0F, -1.1166F, -0.1548F, -0.0489F));

        ModelPartData bone29 = Heart.addChild("bone29", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r28 = bone29.addChild("cube_r28", ModelPartBuilder.create().uv(18, 19).cuboid(-0.5169F, -7.703F, -8.8119F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 7.0F, 2.0F, -1.1304F, -0.1926F, 0.031F));

        ModelPartData bone30 = Heart.addChild("bone30", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 3.0F));

        ModelPartData cube_r29 = bone30.addChild("cube_r29", ModelPartBuilder.create().uv(34, 31).cuboid(-1.3165F, 0.1089F, -2.0655F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 2.0F, -1.1332F, 0.1805F, -0.4188F));

        ModelPartData bone31 = Heart.addChild("bone31", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -12.0F, 2.0F));

        ModelPartData cube_r30 = bone31.addChild("cube_r30", ModelPartBuilder.create().uv(42, 10).cuboid(-1.3165F, 0.1089F, -2.0655F, 2.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(1.0F, 1.0F, 1.0F, -1.5776F, 0.2542F, 0.0067F));

        ModelPartData bone32 = Heart.addChild("bone32", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -10.0F, 2.0F));

        ModelPartData cube_r31 = bone32.addChild("cube_r31", ModelPartBuilder.create().uv(32, 10).cuboid(-1.3165F, 0.1089F, -2.0655F, 2.0F, 2.0F, 3.0F, new Dilation(0.2F)), ModelTransform.of(1.0F, 1.0F, 1.0F, -1.6213F, 0.2542F, 0.0067F));

        ModelPartData bone33 = Heart.addChild("bone33", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r32 = bone33.addChild("cube_r32", ModelPartBuilder.create().uv(0, 27).cuboid(-1.3165F, 0.1089F, -2.0655F, 2.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(2.0F, -9.0F, 5.0F, -1.5766F, 0.0634F, -0.4777F));

        ModelPartData bone36 = Heart.addChild("bone36", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -7.0F, -1.0F));

        ModelPartData cube_r33 = bone36.addChild("cube_r33", ModelPartBuilder.create().uv(20, 12).cuboid(-2.1396F, -3.6395F, -1.7515F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 1.0F, -0.373F, -0.0921F, -0.0134F));

        ModelPartData bone35 = Heart.addChild("bone35", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, -10.0F, 5.0F));

        ModelPartData cube_r34 = bone35.addChild("cube_r34", ModelPartBuilder.create().uv(0, 12).cuboid(-1.5689F, -0.8609F, -1.7575F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 2.0F, -2.0F, 0.7103F, 1.3676F, 2.3943F));

        ModelPartData bone34 = Heart.addChild("bone34", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, -10.0F, 5.0F));

        ModelPartData cube_r35 = bone34.addChild("cube_r35", ModelPartBuilder.create().uv(34, 0).cuboid(0.4311F, -0.8609F, -0.7576F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -2.1499F, 0.8901F, -0.5823F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(CometBossEntityRenderState state) {
        super.setAngles(state);

        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.animate(state.idlingAnimationState, CometBossAnimations.COMET_BOSS_IDLE,
            state.age, 1.0F);
    }

    public ModelPart getPart() {
        return this.bone;
    }
}
