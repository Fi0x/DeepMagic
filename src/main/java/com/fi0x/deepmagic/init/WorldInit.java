package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.generators.dungeon.large.LargeDungeonComponentPlacer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class WorldInit
{
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        if(event.getWorld().isRemote)
            return;

        int id = event.getWorld().provider.getDimension();
        if(id == ConfigHandler.dimensionIdInsanityID || id == ConfigHandler.dimensionIdDepthID)
            LargeDungeonComponentPlacer.findTemplateVariants(((WorldServer) event.getWorld()).getStructureTemplateManager());
    }
}
