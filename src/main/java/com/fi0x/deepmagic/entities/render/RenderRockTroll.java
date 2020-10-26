package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.mobs.EntityRockTroll;
import com.fi0x.deepmagic.entities.model.ModelRockTroll;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderRockTroll extends RenderLiving<EntityRockTroll>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/rock_troll.png");

    public RenderRockTroll(RenderManager manager)
    {
        super(manager, new ModelRockTroll(), 0.7F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityRockTroll entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityRockTroll entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}