package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityDemon;
import com.fi0x.deepmagic.entities.model.ModelDemon;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderDemon extends RenderLiving<EntityDemon>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/demon.png");

    public RenderDemon(RenderManager manager)
    {
        super(manager, new ModelDemon(), 0.4F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityDemon entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityDemon entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}