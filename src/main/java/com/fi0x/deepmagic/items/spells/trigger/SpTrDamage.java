package com.fi0x.deepmagic.items.spells.trigger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class SpTrDamage implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_damage";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }

    public void damageEvent(LivingDamageEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            //TODO: Trigger spell
        }
    }
}
