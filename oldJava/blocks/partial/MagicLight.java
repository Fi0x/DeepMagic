package com.fi0x.deepmagic.blocks.partial;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MagicLight extends BlockBase
{
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.4, 0.4, 0.4, 0.6, 0.6, 0.6);

    public MagicLight(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.SNOW);
        setHardness(1.0F);
        setResistance(1);
        setLightLevel(1);
        this.setTickRandomly(true);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos)
    {
        return AABB;
    }
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return null;
    }
    @Override
    public boolean isPassable(@Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return true;
    }
    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state)
    {
        return false;
    }
    @Override
    public boolean isFullCube(@Nonnull IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        int particles = 0;
        switch(Minecraft.getMinecraft().gameSettings.particleSetting)
        {
            case 0:
                particles = ConfigHandler.magicLightParticles;
                break;
            case 1:
                particles = ConfigHandler.magicLightParticles / 2;
        }
        for(int i = 0; i < particles; i++)
        {
            double x = pos.getX() + (i % 6 == 2 ? 0.4 : (i % 6 == 3 ? 0.6 : (0.4 + (Math.random() * 0.2))));
            double y = pos.getY() + (i % 6 == 0 ? 0.4 : (i % 6 == 1 ? 0.6 : (0.4 + (Math.random() * 0.2))));
            double z = pos.getZ() + (i % 6 == 4 ? 0.4 : (i % 6 == 5 ? 0.6 : (0.4 + (Math.random() * 0.2))));

            ParticleSpawner.spawnParticle(ParticleEnum.MAGIC_LIGHT, x, y, z, 0, 0, 0, rand.nextDouble() * 2, true, 32);
        }
    }
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    @Nonnull
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}