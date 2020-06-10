package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.entities.EntityHoveringOrb;
import com.fi0x.deepmagic.entities.EntityInsanityCow;
import com.fi0x.deepmagic.entities.EntitySpikySlime;
import com.fi0x.deepmagic.entities.EntityNetherWorm;
import com.fi0x.deepmagic.entities.render.RenderHoveringOrb;
import com.fi0x.deepmagic.entities.render.RenderInsanityCow;
import com.fi0x.deepmagic.entities.render.RenderNetherWorm;
import com.fi0x.deepmagic.entities.render.RenderSpikySlime;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInsanityCow.class, RenderInsanityCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHoveringOrb.class, RenderHoveringOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpikySlime.class, RenderSpikySlime::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNetherWorm.class, RenderNetherWorm::new);
    }
}