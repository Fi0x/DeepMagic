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
public class ParticleSpellStone extends Particle
{
    public ParticleSpellStone(String textureName, World worldIn, double x, double y, double z, double scale, double speedX, double speedY, double speedZ)
    {
        super(worldIn, x, y, z, speedX, speedY, speedZ);

        this.particleScale *= (rand.nextDouble() + 0.5 * scale) * 0.5;
        this.particleMaxAge = (int) (Math.random() * 20 + 10);
        this.canCollide = false;

        this.particleRed = (float) (0.8 + Math.random() * 0.2);
        this.particleGreen = (float) (0.8 + Math.random() * 0.2);
        this.particleBlue = (float) (0.8 + Math.random() * 0.2);

        this.motionX = speedX + (Math.random() * 0.5 - 0.25) * 0.1;
        this.motionY = speedY + (Math.random() * 0.5) * 0.3;
        this.motionZ = speedZ + (Math.random() * 0.5 - 0.25) * 0.1;
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / f1 * 0.03;
        this.motionY = this.motionY / f1 * 0.03;
        this.motionZ = this.motionZ / f1 * 0.03;

        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "particle/" + textureName);
        this.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
    }

    @Override
    public void renderParticle(@Nonnull BufferBuilder buffer, @Nonnull Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
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