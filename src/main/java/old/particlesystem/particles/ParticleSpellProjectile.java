package com.fi0x.deepmagic.particlesystem.particles;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleSpellProjectile extends Particle
{
    public ParticleSpellProjectile(String textureName, World worldIn, double x, double y, double z, double scale, double speedX, double speedY, double speedZ)
    {
        super(worldIn, x, y, z, speedX, speedY, speedZ);

        this.particleScale *= 0.5F * scale;

        this.particleRed = (float) Math.random();
        this.particleGreen = (float) Math.random();
        this.particleBlue = (float) Math.random();

        this.motionX = speedX;
        this.motionY = speedY;
        this.motionZ = speedZ;

        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "particle/" + textureName);
        this.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
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