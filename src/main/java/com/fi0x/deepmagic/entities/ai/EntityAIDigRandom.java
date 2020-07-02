package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIDigRandom extends EntityAIBase
{
    EntityDwarf creature;
    World world;

    public EntityAIDigRandom(EntityDwarf entity, World world)
    {
        this.creature = entity;
        this.setMutexBits(1);
        this.world = world;
    }

    @Override
    public boolean shouldExecute()
    {
        return !creature.isMining && creature.posY < 50;
    }

    @Override
    public void startExecuting()
    {
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return false;
    }
}