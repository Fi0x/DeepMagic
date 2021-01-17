package com.fi0x.deepmagic.world.dimensions.depth;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.init.DimensionInit;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
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
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.DEPTH);
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
        return MusicType.NETHER;
    }
    @Nonnull
    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
    {
        return new Vec3d(0.1, 0.1, 0.1);
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
    @Nonnull
    @Override
    public Vec3d getCloudColor(float partialTicks)
    {
        return new Vec3d(0.2, 0.2, 0.2);
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
        return 0.5F;
    }
}