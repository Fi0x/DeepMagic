package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.mobs.EntityInsanityCow;
import com.fi0x.deepmagic.entities.model.ModelInsanityCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderInsanityCow extends RenderLiving<EntityInsanityCow>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/insanity_cow.png");

    public RenderInsanityCow(RenderManager manager)
    {
        super(manager, new ModelInsanityCow(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityInsanityCow entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(@Nonnull EntityInsanityCow entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}