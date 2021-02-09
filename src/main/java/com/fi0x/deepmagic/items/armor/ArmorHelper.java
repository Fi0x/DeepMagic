package com.fi0x.deepmagic.items.armor;

import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class ArmorHelper
{
    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event)
    {
        if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if(player.world.isRemote) return;
        if(!ConfigHandler.depthArmorActive) return;

        ArrayList<ItemStack> armor = new ArrayList<>();
        for(int i = 0; i < 4; i++)
        {
            if(player.inventory.armorItemInSlot(i).isEmpty()) continue;
            if(player.inventory.armorItemInSlot(i).getItem() instanceof ArmorBase) armor.add((player.inventory.armorItemInSlot(i)));
        }

        if(armor.isEmpty()) return;

        event.setCanceled(true);

        if(event.getSource() == DamageSource.FALL)
        {
            //TODO: Check if player wears boots and negate fall damage if enough mana is provided
        } else
        {
            float avoidableDamage = event.getAmount() / 4 * armor.size();
            double requiredMana = avoidableDamage * 100;

            double consumedMana = removeMana(armor, requiredMana, player);
            float remainingDamage = (float) (event.getAmount() - avoidableDamage * (consumedMana / requiredMana));

            if(remainingDamage > 0) player.attackEntityFrom(event.getSource(), remainingDamage);
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        if(!ConfigHandler.depthArmorActive) return;

        flight(player);
        nightVision(player);
    }

    private static void flight(EntityPlayer player)
    {
        if(player.isCreative() || player.isSpectator())
        {
            player.capabilities.allowFlying = true;
            return;
        }

        if(!player.inventory.armorItemInSlot(2).isEmpty() && player.inventory.armorItemInSlot(2).getItem() instanceof ArmorBase)
        {
            if(removeMana(player.inventory.armorItemInSlot(2), ConfigHandler.manaFlightCost, player) >= ConfigHandler.manaFlightCost)
            {
                player.capabilities.allowFlying = true;
            } else
            {
                player.capabilities.allowFlying = false;
                player.capabilities.isFlying = false;
            }
        } else
        {
            //TODO: Check if armor was unequipped in this tick and disable flight if true
        }
    }
    private static void nightVision(EntityPlayer player)
    {
        if(!player.inventory.armorItemInSlot(3).isEmpty() && player.inventory.armorItemInSlot(3).getItem() instanceof ArmorBase)
        {
            if(removeMana(player.inventory.armorItemInSlot(3), ConfigHandler.manaNightVisionCost, player) >= ConfigHandler.manaNightVisionCost)
            {
                //TODO: Add night vision to player
            } else
            {
                //TODO: Disable night vision
            }
        } else
        {
            //TODO: Disable night vision
        }
    }

    private static double removeMana(ArrayList<ItemStack> armor, double amount, EntityPlayer player)
    {
        double removedMana = 0;
        for(ItemStack piece : armor)
        {
            removedMana += removeMana(piece, Math.max(amount - removedMana, 0), player);
        }
        return removedMana;
    }
    private static double removeMana(ItemStack armorPiece, double amount, EntityPlayer player)
    {
        if(armorPiece.isEmpty()) return 0;

        double removedMana = 0;
        NBTTagCompound compound = null;
        if(armorPiece.hasTagCompound()) compound = armorPiece.getTagCompound();

        if(compound != null)
        {
            /*
            TODO: Get stored mana and mana link to altar from compound
             Remove mana from armor / linked mana altar
             Add removed amount to removedMana variable
             */
        }

        if(removedMana < amount)
        {
            PlayerMana mana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
            assert mana != null;
            if(mana.getMana() >= amount - removedMana)
            {
                if(mana.removeMana(player, amount - removedMana, false)) removedMana = amount;
            }
        }

        return removedMana;
    }
}