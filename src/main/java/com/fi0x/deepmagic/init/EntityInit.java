package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.mobs.*;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
    public static void registerEntities()
    {
        registerEntity("insanity_cow", EntityInsanityCow.class, ConfigHandler.entityInsanityCowID, 50, 12925456, 12960970);
        registerEntity("hovering_orb", EntityHoveringOrb.class, ConfigHandler.entityHoveringOrbID, 50, 3132202, 6371343);
        registerEntity("nether_worm", EntityNetherWorm.class, ConfigHandler.entityNetherWormID, 100, 10158080, 4980736);
        registerEntity("giant", EntityGiant.class, ConfigHandler.entityGiantID, 100, 6470009, 6436985);
        registerEntity("rock_troll", EntityRockTroll.class, ConfigHandler.entityRockTrollID, 100, 12566463, 7697781);
        registerEntity("depth_mage", EntityDepthMage.class, ConfigHandler.entityDepthMageID, 50, 0, 3093042);
        registerEntity("dwarf", EntityDwarf.class, ConfigHandler.entityDwarfID, 50, 2555904, 8794643);
        registerEntity("demon", EntityDemon.class, ConfigHandler.entityDemonID, 50, 2162688, 9109504);
        registerEntity("cockroach", EntityCockroach.class, ConfigHandler.entityCockroachID, 50, 5439488, 2299392);
        registerEntity("worm", EntityWorm.class, ConfigHandler.entityWormID, 50, 3093045, 8750988);
        registerEntity("cyclopes", EntityCyclopes.class, ConfigHandler.entityCyclopesID, 50, 16230511, 8735521);
    }
    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation("deepmagic:" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
    }
}