package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.entities.render.RenderInsanityCow;
import com.fi0x.deepmagic.entities.render.RenderSpikySlime;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInsanityCow.class, RenderInsanityCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpikySlime.class, RenderSpikySlime::new);
    }
}