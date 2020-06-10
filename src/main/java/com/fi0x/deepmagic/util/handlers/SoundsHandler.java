package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler
{
    public static SoundEvent ENTITY_HOVERING_ORB_AMBIENT, ENTITY_HOVERING_ORB_HURT, ENTITY_HOVERING_ORB_DEATH, ENTITY_HOVERING_ORB_STEP;

    public static void registerSounds()
    {
        ENTITY_HOVERING_ORB_AMBIENT = registerSound("entity.hovering_orb.ambient");
        ENTITY_HOVERING_ORB_HURT = registerSound("entity.hovering_orb.hurt");
        ENTITY_HOVERING_ORB_DEATH = registerSound("entity.hovering_orb.death");
        ENTITY_HOVERING_ORB_STEP = registerSound("entity.hovering_orb.step");
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
