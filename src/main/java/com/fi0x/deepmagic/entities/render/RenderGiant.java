package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityGiant;
import com.fi0x.deepmagic.entities.model.ModelGiant;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderGiant extends RenderLiving<EntityGiant>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/giant.png");

    public RenderGiant(RenderManager manager)
    {
        super(manager, new ModelGiant(), 0.6F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityGiant entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityGiant entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}