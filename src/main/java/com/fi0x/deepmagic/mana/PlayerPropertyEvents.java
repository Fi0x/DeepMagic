package com.fi0x.deepmagic.mana;

import com.fi0x.deepmagic.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerPropertyEvents
{
    public static PlayerPropertyEvents instance = new PlayerPropertyEvents();
    
    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            if (!event.getObject().hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                event.addCapability(new ResourceLocation(Reference.MOD_ID, "Mana"), new PropertiesDispatcher());
            }
        }
    }
    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            if (event.getOriginal().hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana oldStore = event.getOriginal().getCapability(PlayerProperties.PLAYER_MANA, null);
                PlayerMana newStore = PlayerProperties.getPlayerMana(event.getEntityPlayer());
                newStore.copyFrom(oldStore);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
    	PlayerMana playermana = event.player.getCapability(PlayerProperties.PLAYER_MANA, null);
    	playermana.addMana(0.1);
    	
    }
}