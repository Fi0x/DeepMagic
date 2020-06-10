package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler
{
    public static SoundEvent ENTITY_HOVERING_ORB_AMBIENT, ENTITY_HOVERING_ORB_HURT, ENTITY_HOVERING_ORB_DEATH, ENTITY_HOVERING_ORB_STEP;
    public static SoundEvent ENTITY_NETHER_WORM_AMBIENT, ENTITY_NETHER_WORM_HURT, ENTITY_NETHER_WORM_DEATH, ENTITY_NETHER_WORM_STEP;

    public static void registerSounds()
    {
        ENTITY_HOVERING_ORB_AMBIENT = registerSound("entity.hovering_orb.ambient");
        ENTITY_HOVERING_ORB_HURT = registerSound("entity.hovering_orb.hurt");
        ENTITY_HOVERING_ORB_DEATH = registerSound("entity.hovering_orb.death");
        ENTITY_HOVERING_ORB_STEP = registerSound("entity.hovering_orb.step");

        ENTITY_NETHER_WORM_AMBIENT = registerSound("entity.nether_worm.ambient");
        ENTITY_NETHER_WORM_HURT = registerSound("entity.nether_worm.hurt");
        ENTITY_NETHER_WORM_DEATH = registerSound("entity.nether_worm.death");
        ENTITY_NETHER_WORM_STEP = registerSound("entity.nether_worm.step");
    }
    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
