package com.fi0x.deepmagic.particlesystem.particles;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
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

    protected ParticleMagicLight(World worldIn, double x, double y, double z, float scale, double speedX, double speedY, double speedZ)
    {
        super(worldIn, x, y, z, speedX, speedY, speedZ);
        this.motionX *= 0.100000001;
        this.motionY *= 0.150000001;
        this.motionZ *= 0.100000001;

        this.particleScale *= 0.75F * scale;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D) * scale);

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