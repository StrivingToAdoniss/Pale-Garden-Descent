package org.strivingtoadoniss.palegarden.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.strivingtoadoniss.palegarden.ModBlocks;

@Mixin(AbstractBlock.class)
public class MixinAbstractBlock {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    private void onCalcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world,
        BlockPos pos,
        CallbackInfoReturnable<Float> cir) {
        if (state.isOf(Blocks.PALE_OAK_LOG) && state.get(PillarBlock.AXIS) == Direction.Axis.Y) {
            BlockPos belowPos = pos.down();
            BlockState belowState = world.getBlockState(belowPos);

            boolean isStump = !belowState.isAir() &&
                !belowState.isOf(Blocks.PALE_OAK_LOG) &&
                !belowState.isOf(ModBlocks.REGENERATING_PALE_LOG) &&
                belowState.isSolidBlock(world, belowPos);

            if (isStump) {
                cir.setReturnValue(0.0f);
            }
        }
    }

    @Inject(method = "calcBlockBreakingDelta", at = @At("RETURN"), cancellable = true)
    private void modifyBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos,
            CallbackInfoReturnable<Float> cir) {
        if (state.isOf(Blocks.PALE_OAK_LOG)) {
            float originalDelta = cir.getReturnValue();
            if (originalDelta > 0) {
                cir.setReturnValue(originalDelta / 50.0f);
            }
        }
    }
}
