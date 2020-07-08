package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityCyclopes;
import com.fi0x.deepmagic.entities.model.ModelCyclopes;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderCyclopes extends RenderLiving<EntityCyclopes>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/cyclopes.png");

    public RenderCyclopes(RenderManager manager)
    {
        super(manager, new ModelCyclopes(), 0.4F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCyclopes entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityCyclopes entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}