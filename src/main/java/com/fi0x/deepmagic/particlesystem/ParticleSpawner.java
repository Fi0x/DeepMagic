package com.fi0x.deepmagic.particlesystem;

import com.fi0x.deepmagic.particlesystem.particles.ParticleMagicLight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public class ParticleSpawner
{
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void spawnParticle(ParticleEnum type, double x, double y, double z, double speedX, double speedY, double speedZ)
    {
        spawnParticle(type, x, y, z, speedX, speedY, speedZ, false);
    }
    public static void spawnParticle(ParticleEnum type, double x, double y, double z, double speedX, double speedY, double speedZ, boolean always)
    {
        spawnParticle(type, x, y, z, speedX, speedY, speedZ, always, 16);
    }
    public static void spawnParticle(ParticleEnum type, double x, double y, double z, double speedX, double speedY, double speedZ, boolean always, int range)
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

            if(var14 <= 1 && var15 * var15 + var17 * var17 + var19 * var19 <= range * range)
            {
                switch(type)
                {
                    case MAGIC_LIGHT:
                        particle = new ParticleMagicLight(mc.world, x, y, z, Math.random() * 2, speedX, speedY, speedZ);
                        break;
                }

                assert particle != null;
                mc.effectRenderer.addEffect(particle);
            }
        }
    }
}