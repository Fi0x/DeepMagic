package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.mobs.EntityCockroach;
import com.fi0x.deepmagic.entities.model.ModelCockroach;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderCockroach extends RenderLiving<EntityCockroach>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/cockroach.png");

    public RenderCockroach(RenderManager manager)
    {
        super(manager, new ModelCockroach(), 0.3F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCockroach entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityCockroach entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}