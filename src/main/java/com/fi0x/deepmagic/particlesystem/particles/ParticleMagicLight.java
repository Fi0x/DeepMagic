package com.fi0x.deepmagic.particlesystem.particles;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ParticleMagicLight extends Particle
{
    public ParticleMagicLight(String textureName, World worldIn, double x, double y, double z, double scale, double speedX, double speedY, double speedZ)
    {
        super(worldIn, x, y, z, speedX, speedY, speedZ);

        this.particleScale *= 0.5F * scale;
        this.particleMaxAge = (int) (Math.random() * 40 + 20);
        this.particleAlpha = 0;

        this.particleRed = 1;
        this.particleGreen = (float) (0.6 + Math.random() * 0.4);
        this.particleBlue = 1;


        this.motionX = speedX + (Math.random() * 2 - 1) * 0.1;
        this.motionY = speedY + (Math.random() * 2 - 1) * 0.1;
        this.motionZ = speedZ + (Math.random() * 2 - 1) * 0.1;
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / f1 * 0.003;
        this.motionY = this.motionY / f1 * 0.003;
        this.motionZ = this.motionZ / f1 * 0.003;

        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "particle/" + textureName);
        this.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
    }

    @Override
    public void renderParticle(@Nonnull BufferBuilder buffer, @Nonnull Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        if(particleAge <= 0.2 * particleMaxAge)
        {
            this.particleAlpha = (float) (particleAge / (0.2 * particleMaxAge));
        } else if(particleAge >= 0.8 * particleMaxAge)
        {
            this.particleAlpha = (float) -((particleAge - particleMaxAge) / (0.2 * particleMaxAge));
        } else this.particleAlpha = 1;
    }
    @Override
    public int getFXLayer()
    {
        return 1;
    }
    @Override
    public void setParticleTextureIndex(int particleTextureIndex)
    {
        this.particleTextureIndexX = particleTextureIndex % 16;
        this.particleTextureIndexY = particleTextureIndex / 16;
    }
}