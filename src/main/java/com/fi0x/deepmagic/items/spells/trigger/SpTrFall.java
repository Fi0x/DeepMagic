package com.fi0x.deepmagic.items.spells.trigger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class SpTrFall implements ISpellTrigger
{
    @Override
    public String getName()
    {
        return "trigger_fall";
    }
    @Override
    public ISpellTrigger getTrigger()
    {
        return this;
    }

    public void fallEvent(LivingFallEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            //TODO: Trigger spell
        }
    }
}
