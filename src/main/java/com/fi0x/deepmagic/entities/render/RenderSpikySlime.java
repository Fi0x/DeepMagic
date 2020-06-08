package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.entities.model.ModelSpikySlime;
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
}