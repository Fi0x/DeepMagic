package com.fi0x.deepmagic.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArmorHelper
{
    @SubscribeEvent
    public static void onPlayerAttacked(LivingDamageEvent event)
    {
        System.out.println("Living Damage Event triggered");
        if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if(player.world.isRemote) return;

        System.out.println("Trying to cancel Damage Event");
        event.setCanceled(true);
    }
    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event)
    {
        System.out.println("Living Attack Event triggered");
        if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if(player.world.isRemote) return;

        System.out.println("Trying to cancel Attack Event");
        event.setCanceled(true);
    }
}