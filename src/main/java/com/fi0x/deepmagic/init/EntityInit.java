package com.fi0x.deepmagic.init;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.*;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
    public static void registerEntities()
    {
        registerEntity("insanity_cow", EntityInsanityCow.class, Reference.ENTITY_INSANITY_COW, 50, 12925456, 12960970);
//        registerEntity("spiky_slime", EntitySpikySlime.class, Reference.ENTITY_SPIKY_SLIME, 50, 7864320, 2818048);
        registerEntity("hovering_orb", EntityHoveringOrb.class, Reference.ENTITY_HOVERING_ORB, 50, 3132202, 6371343);
        registerEntity("nether_worm", EntityNetherWorm.class, Reference.ENTITY_NETHER_WORM, 100, 10158080, 4980736);
        registerEntity("giant", EntityGiant.class, Reference.ENTITY_GIANT, 100, 6470009, 6436985);
        registerEntity("rock_troll", EntityRockTroll.class, Reference.ENTITY_ROCK_TROLL, 100, 12566463, 7697781);
        registerEntity("depth_mage", EntityDepthMage.class, Reference.ENTITY_DEPTH_MAGE, 50, 0, 255);
    }
    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation("deepmagic:" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
    }
}