package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.mobs.EntityHoveringOrb;
import com.fi0x.deepmagic.entities.model.ModelHoveringOrb;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderHoveringOrb extends RenderLiving<EntityHoveringOrb>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/hovering_orb.png");

    public RenderHoveringOrb(RenderManager manager)
    {
        super(manager, new ModelHoveringOrb(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityHoveringOrb entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityHoveringOrb entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}