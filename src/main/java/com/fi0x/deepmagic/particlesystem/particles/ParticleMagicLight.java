package com.fi0x.deepmagic.particlesystem.particles;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMagicLight extends Particle
{
    public ParticleMagicLight(World worldIn, double x, double y, double z, double speedX, double speedY, double speedZ)
    {
        this(worldIn, x, y, z, 1.0F, speedX, speedY, speedZ);
    }
    public ParticleMagicLight(World worldIn, double x, double y, double z, double scale, double speedX, double speedY, double speedZ)
    {
        super(worldIn, x, y, z, speedX, speedY, speedZ);

        this.particleScale *= 0.5F * scale;
        this.particleMaxAge = (int) (Math.random() * 40 + 20);

        this.particleRed = 1;
        this.particleGreen = (float) (0.7 + Math.random() * 0.3);
        this.particleBlue = 1;
        this.motionX = speedX + (Math.random() * 2 - 1) * 0.1;
        this.motionY = speedY + (Math.random() * 2 - 1) * 0.1;
        this.motionZ = speedZ + (Math.random() * 2 - 1) * 0.1;
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / f1 * 0.003;
        this.motionY = this.motionY / f1 * 0.003;
        this.motionZ = this.motionZ / f1 * 0.003;

        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "particle/magic_light");
        this.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
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
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
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