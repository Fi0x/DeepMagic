package com.fi0x.deepmagic.world.generators.underground;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import javax.annotation.Nonnull;
import java.util.Random;

public class CustomRavineGenerator extends MapGenBase
{
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState LIGHT = ModBlocks.DEPTH_GLOWSTONE.getDefaultState();
    private final float[] rs = new float[1024];

    public CustomRavineGenerator()
    {
        this.range = 32;
    }

    protected void addTunnel(long p_180707_1_, int chunkX, int chunkZ, ChunkPrimer primer, double p_180707_6_, double p_180707_8_, double p_180707_10_, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double p_180707_17_)
    {
        Random random = new Random(p_180707_1_);
        double d0 = chunkX * 16 + 8;
        double d1 = chunkZ * 16 + 8;
        float f = 0.0F;
        float f1 = 0.0F;

        if(p_180707_16_ <= 0)
        {
            int i = this.range * 16 - 16;
            p_180707_16_ = i - random.nextInt(i / 4);
        }

        boolean flag1 = false;

        if(p_180707_15_ == -1)
        {
            p_180707_15_ = p_180707_16_ / 2;
            flag1 = true;
        }

        float f2 = 1.0F;

        for(int j = 0; j < 256; ++j)
        {
            if(j == 0 || random.nextInt(3) == 0)
            {
                f2 = 1.0F + random.nextFloat() * random.nextFloat();
            }
            this.rs[j] = f2 * f2;
        }

        for(; p_180707_15_ < p_180707_16_; ++p_180707_15_)
        {
            double d9 = 1.5D + (double) (MathHelper.sin((float) p_180707_15_ * (float) Math.PI / (float) p_180707_16_) * p_180707_12_);
            double d2 = d9 * p_180707_17_;
            d9 = d9 * ((double) random.nextFloat() * 0.25D + 0.75D);
            d2 = d2 * ((double) random.nextFloat() * 0.25D + 0.75D);
            float f3 = MathHelper.cos(p_180707_14_);
            float f4 = MathHelper.sin(p_180707_14_);
            p_180707_6_ += MathHelper.cos(p_180707_13_) * f3;
            p_180707_8_ += f4;
            p_180707_10_ += MathHelper.sin(p_180707_13_) * f3;
            p_180707_14_ = p_180707_14_ * 0.7F;
            p_180707_14_ = p_180707_14_ + f1 * 0.05F;
            p_180707_13_ += f * 0.05F;
            f1 = f1 * 0.8F;
            f = f * 0.5F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if(flag1 || random.nextInt(4) != 0)
            {
                double d3 = p_180707_6_ - d0;
                double d4 = p_180707_10_ - d1;
                double d5 = p_180707_16_ - p_180707_15_;
                double d6 = p_180707_12_ + 2.0F + 16.0F;

                if(d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6) return;

                if(p_180707_6_ >= d0 - 16.0D - d9 * 2.0D && p_180707_10_ >= d1 - 16.0D - d9 * 2.0D && p_180707_6_ <= d0 + 16.0D + d9 * 2.0D && p_180707_10_ <= d1 + 16.0D + d9 * 2.0D)
                {
                    int k2 = MathHelper.floor(p_180707_6_ - d9) - chunkX * 16 - 1;
                    int k = MathHelper.floor(p_180707_6_ + d9) - chunkX * 16 + 1;
                    int l2 = MathHelper.floor(p_180707_8_ - d2) - 1;
                    int l = MathHelper.floor(p_180707_8_ + d2) + 1;
                    int i3 = MathHelper.floor(p_180707_10_ - d9) - chunkZ * 16 - 1;
                    int i1 = MathHelper.floor(p_180707_10_ + d9) - chunkZ * 16 + 1;

                    if(k2 < 0) k2 = 0;
                    if(k > 16) k = 16;
                    if(l2 < 1) l2 = 1;
                    if(l > 248) l = 248;
                    if(i3 < 0) i3 = 0;
                    if(i1 > 16) i1 = 16;

                    for(int j1 = k2; j1 < k; ++j1)
                    {
                        for(int k1 = i3; k1 < i1; ++k1)
                        {
                            for(int l1 = l + 1; l1 >= l2 - 1; --l1)
                            {
                                if(l1 >= 0 && l1 < 256)
                                {
                                    if(l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
                                    {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    for(int blockX = k2; blockX < k; ++blockX)
                    {
                        double d10 = ((double) (blockX + chunkX * 16) + 0.5D - p_180707_6_) / d9;

                        for(int blockZ = i3; blockZ < i1; ++blockZ)
                        {
                            double d7 = ((double) (blockZ + chunkZ * 16) + 0.5D - p_180707_10_) / d9;

                            if(d10 * d10 + d7 * d7 < 1.0D)
                            {
                                for(int blockY = l; blockY > l2; --blockY)
                                {
                                    double d8 = ((double) (blockY - 1) + 0.5D - p_180707_8_) / d2;

                                    if((d10 * d10 + d7 * d7) * (double) this.rs[blockY - 1] + d8 * d8 / 6.0D < 1.0D)
                                    {
                                        digBlock(primer, blockX, blockY, blockZ);
                                    }
                                }
                            }
                        }
                    }
                    if(flag1)
                    {
                        break;
                    }
                }
            }
        }
    }

    protected void recursiveGenerate(@Nonnull World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, @Nonnull ChunkPrimer chunkPrimerIn)
    {
        if(this.rand.nextInt(50) == 0)
        {
            double d0 = chunkX * 16 + this.rand.nextInt(16);
            double d1 = this.rand.nextInt(this.rand.nextInt(130) + 100) + 20;
            double d2 = chunkZ * 16 + this.rand.nextInt(16);

            for(int j = 0; j < 1; ++j)
            {
                float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
                this.addTunnel(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
            }
        }
    }

    protected void digBlock(ChunkPrimer data, int x, int y, int z)
    {
        IBlockState state = data.getBlockState(x, y, z);

        if(state.getBlock() == Blocks.STONE || state.getBlock() == ModBlocks.DEPTH_STONE || state.getBlock() == ModBlocks.DEPTH_DIRT || state.getBlock() == ModBlocks.INSANITY_STONE || state.getBlock() == ModBlocks.INSANITY_DIRT)
        {
            data.setBlockState(x, y, z, AIR);
        }
    }
}
