package com.fi0x.deepmagic.world.dimensions.depth;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.init.DimensionInit;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;

public class DimensionDepth extends WorldProvider
{
    public DimensionDepth()
    {
        //TODO: Adjust BiomeProvider
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.INSANITY);
    }

    @Nonnull
    @Override
    public DimensionType getDimensionType()
    {
        return DimensionInit.DEPTH;
    }
    @Nonnull
    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorDepth(world, world.getSeed());
    }
    @Override
    public boolean canRespawnHere()
    {
        return true;
    }
    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }
    @Override
    public double getMovementFactor()
    {
        return 20;
    }
    @Override
    public boolean shouldMapSpin(@Nonnull String entity, double x, double z, double rotation)
    {
        return true;
    }
    @Nonnull
    @Override
    public WorldSleepResult canSleepAt(@Nonnull EntityPlayer player, @Nonnull BlockPos pos)
    {
        return WorldSleepResult.DENY;
    }
    @Override
    public MusicType getMusicType()
    {
        //TODO: Use custom music
        return MusicType.NETHER;
    }
    @Override
    public boolean hasSkyLight()
    {
        return false;
    }
    @Override
    public float getCloudHeight()
    {
        //TODO: Verify if necessary
        return 96F;
    }
    @Override
    public float getSunBrightnessFactor(float par1)
    {
        //TODO: Verify if necessary
        return 0F;
    }
    @Override
    public int getMoonPhase(long worldTime)
    {
        //TODO: Verify if necessary
        return 4;
    }
    @Nonnull
    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
    {
        float f = MathHelper.cos(p_76562_1_ * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float f1 = 0.359375F;
        float f2 = 0.359375F;
        float f3 = 0.3671875F;

        f1 = f1 * (f * 0.94F + 0.06F);
        f2 = f2 * (f * 0.94F + 0.06F);
        f3 = f3 * (f * 0.91F + 0.09F);
        return new Vec3d(f1, f2, f3);
    }
    @Override
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }
    @Override
    public boolean doesWaterVaporize()
    {
        return false;
    }
    @Override
    public boolean isDaytime()
    {
        //TODO: Add slow time
        return false;
    }
    @Nonnull
    @Override
    public Vec3d getCloudColor(float partialTicks)
    {
        //TODO: Ajust cloud color
        float f = world.getCelestialAngle(partialTicks);
        float f1 = MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        float f2 = 0.45490196078431372549019607843137F;
        float f3 = 0.98823529411764705882352941176471F;
        float f4 = 0.55294117647058823529411764705882F;

        f2 = f2 * (f1 * 0.9F + 0.1F);
        f3 = f3 * (f1 * 0.9F + 0.1F);
        f4 = f4 * (f1 * 0.85F + 0.15F);

        return new Vec3d(f2, f3, f4);
    }
    @Override
    public void onPlayerAdded(@Nonnull EntityPlayerMP player)
    {
    }
    @Override
    public void onPlayerRemoved(@Nonnull EntityPlayerMP player)
    {
    }
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        //TODO: re-add time
        int i = 12800;
        float f = (float) i / 24000.0F - 0.25F;

        if(f < 0.0F)
        {
            ++f;
        }

        if(f > 1.0F)
        {
            --f;
        }

        float f1 = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
        f = f + (f1 - f) / 3.0F;
        return f;
    }
}