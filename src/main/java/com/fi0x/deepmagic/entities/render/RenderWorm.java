package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityWorm;
import com.fi0x.deepmagic.entities.model.ModelWorm;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderWorm extends RenderLiving<EntityWorm>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/worm.png");

    public RenderWorm(RenderManager manager)
    {
        super(manager, new ModelWorm(), 0.1F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityWorm entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityWorm entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}