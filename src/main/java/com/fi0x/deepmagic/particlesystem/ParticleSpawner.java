package com.fi0x.deepmagic.particlesystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ParticleSpawner
{
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void spawnParticle(ParticleEnum type, BlockPos pos)
    {
        spawnParticle(type, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0, 1, false, 16);
    }
    public static void spawnParticle(ParticleEnum type, double x, double y, double z, double speedX, double speedY, double speedZ)
    {
        spawnParticle(type, x, y, z, speedX, speedY, speedZ, 1, false, 16);
    }
    public static void spawnParticle(ParticleEnum type, double x, double y, double z, double speedX, double speedY, double speedZ, double size, boolean always, int range)
    {
        if(mc.getRenderViewEntity() != null && mc.effectRenderer != null)
        {
            int var14 = mc.gameSettings.particleSetting;
            if(var14 == 1 && mc.world.rand.nextInt(3) == 0)
            {
                var14 = 2;
            }
            if(always) var14 = 0;

            double var15 = mc.getRenderViewEntity().posX - x;
            double var17 = mc.getRenderViewEntity().posY - y;
            double var19 = mc.getRenderViewEntity().posZ - z;
            Particle particle = null;

            if(type.getShouldIgnoreRange() || (var14 <= 1 && var15 * var15 + var17 * var17 + var19 * var19 <= range * range))
            {
                try
                {
                    Constructor con = type.getParticleClass().getConstructor(String.class, World.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class);
                    particle = (Particle) con.newInstance(type.getTextureName(), mc.world, x, y, z, size, speedX, speedY, speedZ);
                } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored)
                {
                }

                assert particle != null;
                mc.effectRenderer.addEffect(particle);
            }
        }
    }
}