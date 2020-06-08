package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.entities.render.RenderInsanityCow;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityInsanityCow.class, new IRenderFactory<EntityInsanityCow>()
		{
			@Override
			public Render<? super EntityInsanityCow> createRenderFor(RenderManager manager)
			{
				return new RenderInsanityCow(manager);
			}
		});
	}
}