package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityNetherWorm;
import com.fi0x.deepmagic.entities.model.ModelNetherWorm;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderNetherWorm extends RenderLiving<EntityNetherWorm>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/nether_worm.png");

    public RenderNetherWorm(RenderManager manager)
    {
        super(manager, new ModelNetherWorm(), 0.8F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityNetherWorm entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityNetherWorm entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}