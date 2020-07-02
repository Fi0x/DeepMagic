package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
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
        creature.isMining = true;
        BlockPos pos = getRandomBlock();
        world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos).getBlock().getDefaultState(), 1);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return false;
    }

    private BlockPos getRandomBlock()
    {
        return creature.getPosition().add(Math.random() * 3 - 1, Math.random() * 2, Math.random() * 3 - 1);
    }
}