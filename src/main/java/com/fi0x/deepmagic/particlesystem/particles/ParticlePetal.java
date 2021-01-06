package com.fi0x.deepmagic.particlesystem.particles;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ParticlePetal extends Particle
{
    public ParticlePetal(World worldIn, double x, double y, double z, double speedX, double speedY, double speedZ)
    {
        this(worldIn, x, y, z, 1.0F, speedX, speedY, speedZ);
    }

    protected ParticlePetal(World worldIn, double x, double y, double z, float scale, double speedX, double speedY, double speedZ)
    {
        super(worldIn, x, y, z, speedX, speedY, speedZ);
        this.motionX *= 0.100000001;
        this.motionY *= 0.150000001;
        this.motionZ *= 0.100000001;

        this.particleGreen = 0;
        this.particleRed = 1;
        this.particleBlue = 0;
        this.particleScale *= 0.75F * scale;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D) * scale);
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(this.particleAge++ >= this.particleMaxAge) this.setExpired();

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.move(this.motionX, this.motionY, this.motionZ);

        if(this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.96;
        this.motionY *= 0.96;
        this.motionZ *= 0.96;

        if(this.onGround)
        {
            this.motionX *= 0.7;
            this.motionZ *= 0.7;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, @Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn, @Nonnull int... ignored)
        {
            return new ParticlePetal(worldIn, x, y, z, (float) xSpeedIn, (float) ySpeedIn, (float) zSpeedIn);
        }
    }

    @Override
    public int getFXLayer()
    {
        if(particleTexture == null) return 1;
        else return 0;
    }
}