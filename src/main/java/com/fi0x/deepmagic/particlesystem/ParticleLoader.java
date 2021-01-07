package com.fi0x.deepmagic.particlesystem;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ParticleLoader
{
    @SubscribeEvent
    public static void spriteAtlas(TextureStitchEvent.Pre event)
    {
        event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "particle/" + ParticleEnum.MAGIC_LIGHT.getTextureName()));
    }
}
