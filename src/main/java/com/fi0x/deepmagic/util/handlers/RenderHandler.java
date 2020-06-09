package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.entities.render.RenderInsanityCow;
import com.fi0x.deepmagic.entities.render.RenderSpikySlime;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInsanityCow.class, RenderInsanityCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpikySlime.class, new IRenderFactory<EntitySpikySlime>()
        {
            @Override
            public Render<? super EntitySpikySlime> createRenderFor(RenderManager manager)
            {
                return new RenderSpikySlime(manager);
            }
        });
    }
}