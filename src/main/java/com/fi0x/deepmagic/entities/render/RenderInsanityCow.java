package com.fi0x.deepmagic.entities.render;

import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.entities.model.ModelInsanityCow;
import com.fi0x.deepmagic.util.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderInsanityCow extends RenderLiving<EntityInsanityCow>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/insanity_cow.png");
	
	public RenderInsanityCow(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ModelInsanityCow(), 0F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityInsanityCow entity)
	{
		return TEXTURES;
	}
	@Override
	protected void applyRotations(EntityInsanityCow entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
}