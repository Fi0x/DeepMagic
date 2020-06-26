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
    public static SoundEvent ENTITY_ROCK_TROLL_AMBIENT, ENTITY_ROCK_TROLL_HURT, ENTITY_ROCK_TROLL_DEATH, ENTITY_ROCK_TROLL_STEP;
    public static SoundEvent ENTITY_DEPTH_MAGE_AMBIENT, ENTITY_DEPTH_MAGE_HURT, ENTITY_DEPTH_MAGE_DEATH, ENTITY_DEPTH_MAGE_STEP;
    public static SoundEvent ENTITY_DWARF_AMBIENT, ENTITY_DWARF_HURT, ENTITY_DWARF_DEATH, ENTITY_DWARF_STEP;
    public static SoundEvent ENTITY_DEMON_AMBIENT, ENTITY_DEMON_HURT, ENTITY_DEMON_DEATH, ENTITY_DEMON_STEP;

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

        ENTITY_ROCK_TROLL_AMBIENT = registerSound("entity.rock_troll.ambient");
        ENTITY_ROCK_TROLL_HURT = registerSound("entity.rock_troll.hurt");
        ENTITY_ROCK_TROLL_DEATH = registerSound("entity.rock_troll.death");
        ENTITY_ROCK_TROLL_STEP = registerSound("entity.rock_troll.step");

        ENTITY_DEPTH_MAGE_AMBIENT = registerSound("entity.depth_mage.ambient");
        ENTITY_DEPTH_MAGE_HURT = registerSound("entity.depth_mage.hurt");
        ENTITY_DEPTH_MAGE_DEATH = registerSound("entity.depth_mage.death");
        ENTITY_DEPTH_MAGE_STEP = registerSound("entity.depth_mage.step");

        ENTITY_DWARF_AMBIENT = registerSound("entity.dwarf.ambient");
        ENTITY_DWARF_HURT = registerSound("entity.dwarf.hurt");
        ENTITY_DWARF_DEATH = registerSound("entity.dwarf.death");
        ENTITY_DWARF_STEP = registerSound("entity.dwarf.step");

        ENTITY_DEMON_AMBIENT = registerSound("entity.demon.ambient");
        ENTITY_DEMON_HURT = registerSound("entity.demon.hurt");
        ENTITY_DEMON_DEATH = registerSound("entity.demon.death");
        ENTITY_DEMON_STEP = registerSound("entity.demon.step");
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
