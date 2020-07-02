package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EntityAIMining extends EntityAIBase
{
    protected final EntityDwarf creature;
    private BlockPos destination;
    private World world;
    private DigHelper digger;

    public EntityAIMining(EntityDwarf creature, World world)
    {
        this.creature = creature;
        this.setMutexBits(1);
        this.world = world;
        digger = new DigHelper();
    }

    public boolean shouldExecute()
    {
        return !creature.isMining && creature.posY < 50;
    }

    public void startExecuting()
    {
        this.creature.isMining = true;
        destination = getRandomDestination();

        ArrayList<BlockPos> miningBlocks = digger.getMiningPath(world, creature.getPosition(), destination);
        if(digger.isMineablePath(world, miningBlocks)) digger.mineBlocks(world, miningBlocks, creature);
        else creature.isMining = false;
    }

    public boolean shouldContinueExecuting()
    {
        if(creature.getPosition() == destination) creature.isMining = false;

        if(creature.isMining) return true;
        return false;
    }

    private BlockPos getRandomDestination()
    {
        return new BlockPos(creature.posX + (Math.random() * 20) - 10, creature.posY, creature.posZ + (Math.random() * 20) - 10);
    }
}