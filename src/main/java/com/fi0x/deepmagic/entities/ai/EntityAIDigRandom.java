package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIDigRandom extends EntityAIWanderAvoidWater
{
    World world;

    public EntityAIDigRandom(EntityDwarf entity, World world)
    {
        super(entity, 1, 30);
        this.setMutexBits(1);
        this.world = world;
    }

    @Override
    public boolean shouldExecute()
    {
        return super.shouldExecute();
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if(entity.getNavigator().noPath())
        {
            BlockPos pos = getRandomBlock();
            world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos).getBlock().getDefaultState(), 1);
            world.setBlockToAir(pos);
            pos = pos.add(0, (Math.random() * 3) - 1, 0);
            world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos).getBlock().getDefaultState(), 1);
            world.setBlockToAir(pos);
            return false;
        }
        return true;
    }

    private BlockPos getRandomBlock()
    {
        int x = 0;
        int z = 0;
        while(x == 0 && z == 0)
        {
            x = (int) (Math.random() * 3) - 1;
            if(x == 0) z = (int) (Math.random() * 3) - 1;
        }
        return entity.getPosition().add(x, 0, z);
    }
}