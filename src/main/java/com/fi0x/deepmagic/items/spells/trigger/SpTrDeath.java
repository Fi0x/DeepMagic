package com.fi0x.deepmagic.items.spells.trigger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class SpTrDeath implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_death";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }

    public void deathEvent(LivingDeathEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            //TODO: Trigger spell
        }
    }
}
