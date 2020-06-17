package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityDepthMage;
import com.fi0x.deepmagic.entities.model.ModelMage;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderDepthMage extends RenderLiving<EntityDepthMage>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/depth_mage.png");

    public RenderDepthMage(RenderManager manager)
    {
        super(manager, new ModelMage(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityDepthMage entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityDepthMage entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}