package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityRockTroll;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIDefence extends EntityAIBase
{
    protected final EntityRockTroll creature;
    private int startTick;

    public EntityAIDefence(EntityRockTroll creature)
    {
        this.creature = creature;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (this.creature.getRevengeTarget() == null) return false;
        startTick = creature.ticksExisted;
        return true;
    }

    public void startExecuting()
    {
        this.creature.defenceState = true;
    }

    public boolean shouldContinueExecuting()
    {
        if(creature.ticksExisted - startTick > 5*20)
        {
            creature.defenceState = false;
            return false;
        }
        return true;
    }
}