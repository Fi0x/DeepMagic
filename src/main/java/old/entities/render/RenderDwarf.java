package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.entities.model.ModelDwarf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderDwarf extends RenderLiving<EntityDwarf>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/dwarf.png");

    public RenderDwarf(RenderManager manager)
    {
        super(manager, new ModelDwarf(), 0.4F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityDwarf entity)
    {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityDwarf entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}