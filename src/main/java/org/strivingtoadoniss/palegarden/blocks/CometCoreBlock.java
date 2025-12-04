package org.strivingtoadoniss.palegarden.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CometCoreBlock extends Block {
    public static final MapCodec<CometCoreBlock> CODEC = createCodec(CometCoreBlock::new);

    public CometCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        for (int i = 0; i < 3; i++) {
            double offsetX = random.nextDouble() - 0.5;
            double offsetY = random.nextDouble() - 0.5;
            double offsetZ = random.nextDouble() - 0.5;

            world.addParticle(
                ParticleTypes.SCULK_SOUL,
                pos.getX() + 0.5 + offsetX,
                pos.getY() + 0.5 + offsetY,
                pos.getZ() + 0.5 + offsetZ,
                0.0, 0.02, 0.0);
        }

        if (random.nextFloat() < 0.3f) {
            world.addParticle(
                ParticleTypes.GLOW,
                pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.8,
                pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 0.8,
                pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.8,
                0.0, 0.0, 0.0);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);

        world.spawnParticles(
            ParticleTypes.SCULK_CHARGE_POP,
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            2,
            0.3, 0.3, 0.3,
            0.01);
    }
}
