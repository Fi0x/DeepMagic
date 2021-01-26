package com.fi0x.deepmagic.items.armor;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

        float avoidableDamage = event.getAmount() / 4 * armor.size();
        double requiredMana = avoidableDamage * 100;

        double consumedMana = removeMana(armor, requiredMana);
        float remainingDamage = (float) (event.getAmount() - avoidableDamage * (consumedMana / requiredMana));

        if(remainingDamage > 0) player.attackEntityFrom(event.getSource(), remainingDamage);
    }

    private static double removeMana(ArrayList<ItemStack> armor, double amount)
    {
        for(ItemStack piece : armor)
        {
            //TODO: Use NBT data to get stored mana and mana link to altar
        }
        /*
        TODO: Remove mana from armor / linked mana altar
         If not enough mana is available, use player mana
         Return amount of mana removed
         */
        return 0;
    }
}