package com.fi0x.deepmagic.items.spells.trigger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class SpTrHeal implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_heal";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }

    public void healEvent(LivingHealEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            //TODO: Trigger spell
        }
    }
}
