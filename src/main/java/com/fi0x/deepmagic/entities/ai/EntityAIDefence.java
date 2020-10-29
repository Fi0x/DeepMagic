package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.mobs.EntityRockTroll;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIDefence extends EntityAIBase
{
    protected final EntityRockTroll creature;

    public EntityAIDefence(EntityRockTroll creature)
    {
        this.creature = creature;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        return this.creature.getRevengeTarget() != null;
    }

    public void startExecuting()
    {
        this.creature.defenceTime = 5*20;
    }

    public boolean shouldContinueExecuting()
    {
        if(creature.defenceTime <= 0)
        {
            return false;
        }
        creature.defenceTime--;
        return true;
    }
}