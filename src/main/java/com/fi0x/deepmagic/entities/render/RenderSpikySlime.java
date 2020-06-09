package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.entities.model.ModelSpikySlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderSpikySlime extends RenderLiving<EntitySpikySlime>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/spiky_slime");

    public RenderSpikySlime(RenderManager manager)
    {
        super(manager, new ModelSpikySlime(), 0.5F);
        this.addLayer(new SlimeGelLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySpikySlime entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntitySpikySlime entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }

    @Override
    public void doRender(EntitySpikySlime entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = 0.25F * (float)entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntitySpikySlime entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSlimeSize();
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
}