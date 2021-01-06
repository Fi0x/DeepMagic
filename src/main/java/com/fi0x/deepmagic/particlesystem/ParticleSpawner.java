package com.fi0x.deepmagic.particlesystem;

import com.fi0x.deepmagic.particlesystem.particles.ParticlePetal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public class ParticleSpawner
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final double DIST = 16;

    public static Particle spawnParticle(ParticleEnum type, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ)
    {
        if(mc.getRenderViewEntity() != null && mc.effectRenderer != null)
        {
            if(mc.gameSettings.particleSetting != 1 || mc.world.rand.nextInt(3) != 0) return null;

            double x = mc.getRenderViewEntity().posX - posX;
            double y = mc.getRenderViewEntity().posY - posY;
            double z = mc.getRenderViewEntity().posZ - posZ;
            Particle particle = null;

            if(x * x + y * y + z * z <= DIST * DIST)
            {
                switch(type)
                {
                    case FLOWER:
                        particle = new ParticlePetal(mc.world, posX, posY, posZ, velocityX, velocityY, velocityZ);
                        break;
                }

                assert particle != null;
                mc.effectRenderer.addEffect(particle);
                return particle;
            }
        }
        return null;
    }
}