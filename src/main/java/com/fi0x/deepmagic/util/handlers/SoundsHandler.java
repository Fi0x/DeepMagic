package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler
{
    public static SoundEvent ENTITY_HOVERING_ORB_AMBIENT, ENTITY_HOVERING_ORB_HURT, ENTITY_HOVERING_ORB_DEATH, ENTITY_HOVERING_ORB_STEP;
    public static SoundEvent ENTITY_NETHER_WORM_AMBIENT, ENTITY_NETHER_WORM_HURT, ENTITY_NETHER_WORM_DEATH, ENTITY_NETHER_WORM_STEP;
    public static SoundEvent ENTITY_GIANT_AMBIENT, ENTITY_GIANT_HURT, ENTITY_GIANT_DEATH, ENTITY_GIANT_STEP;
    public static SoundEvent ENTITY_INSANITY_COW_AMBIENT, ENTITY_INSANITY_COW_HURT, ENTITY_INSANITY_COW_DEATH, ENTITY_INSANITY_COW_STEP;
    public static SoundEvent MUSIC_INSANITY_DIMENSION;

    public static void registerSounds()
    {
        MUSIC_INSANITY_DIMENSION = registerSound("music.insanity_dimension");
        EnumHelperClient.addMusicType("INSANITY_DIMENSION", MUSIC_INSANITY_DIMENSION, 0, 1);

        ENTITY_HOVERING_ORB_AMBIENT = registerSound("entity.hovering_orb.ambient");
        ENTITY_HOVERING_ORB_HURT = registerSound("entity.hovering_orb.hurt");
        ENTITY_HOVERING_ORB_DEATH = registerSound("entity.hovering_orb.death");
        ENTITY_HOVERING_ORB_STEP = registerSound("entity.hovering_orb.step");

        ENTITY_NETHER_WORM_AMBIENT = registerSound("entity.nether_worm.ambient");
        ENTITY_NETHER_WORM_HURT = registerSound("entity.nether_worm.hurt");
        ENTITY_NETHER_WORM_DEATH = registerSound("entity.nether_worm.death");
        ENTITY_NETHER_WORM_STEP = registerSound("entity.nether_worm.step");

        ENTITY_GIANT_AMBIENT = registerSound("entity.giant.ambient");
        ENTITY_GIANT_HURT = registerSound("entity.giant.hurt");
        ENTITY_GIANT_DEATH = registerSound("entity.giant.death");
        ENTITY_GIANT_STEP = registerSound("entity.giant.step");

        ENTITY_INSANITY_COW_AMBIENT = registerSound("entity.insanity_cow.ambient");
        ENTITY_INSANITY_COW_HURT = registerSound("entity.insanity_cow.hurt");
        ENTITY_INSANITY_COW_DEATH = registerSound("entity.insanity_cow.death");
        ENTITY_INSANITY_COW_STEP = registerSound("entity.insanity_cow.step");
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
