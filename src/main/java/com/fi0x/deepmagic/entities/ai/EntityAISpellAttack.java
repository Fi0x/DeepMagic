package com.fi0x.deepmagic.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISpellAttack extends EntityAIBase
{
    EntityLiving attacker;

    public EntityAISpellAttack(EntityLiving attacker, float movementSpeed)
    {
        this.attacker = attacker;
    }

    @Override
    public boolean shouldExecute()
    {
        return attacker != null && attacker.getAttackTarget() != null;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return shouldExecute() && !attacker.getNavigator().noPath();
    }

    @Override
    public void startExecuting()
    {
    }

    @Override
    public void updateTask()
    {
        //TODO: Implement ars magica 2 stuff
    }
    private void doRangedAttack()
    {
        //TODO: Implement ars magica 2 stuff
    }
}