package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.model.ModelSpellProjectile;
import com.fi0x.deepmagic.entities.projectiles.EntitySpellProjectile;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderSpellProjectile extends Render<EntitySpellProjectile>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation("deepmagic:textures/entity/spell_projectile.png");
    private final ModelBase model;

    public RenderSpellProjectile(RenderManager manager)
    {
        super(manager);
        model = new ModelSpellProjectile();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySpellProjectile entity)
    {
        return TEXTURES;
    }

    @Override
    public void doRender(@Nonnull EntitySpellProjectile entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GL11.glPushMatrix();
        bindTexture(TEXTURES);
        GL11.glTranslated(x, y + 0.13, z);
        model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.03F);
        GL11.glPopMatrix();
    }
}