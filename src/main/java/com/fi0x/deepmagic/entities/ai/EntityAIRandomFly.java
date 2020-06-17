package com.fi0x.deepmagic.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EntityAIRandomFly extends EntityAIBase
{
    private EntityLiving entity;
    private int destX;
    private int destY;
    private int destZ;

    public EntityAIRandomFly(EntityLiving entity)
    {
        this.entity = entity;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        return true;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if(entity.posX == destX && entity.posY == destY && entity.posZ == destZ)
        {
            System.out.println("Destination reached");
            return false;
        }
        return true;
    }

    @Override
    public void startExecuting()
    {
        Random random = this.entity.getRNG();
        double d0 = this.entity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.entity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.entity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        if(getHeight(entity) > 5) d1 = this.entity.posY - Math.random() * getHeight(entity);

        this.entity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }

    private int getHeight(EntityLiving entity)
    {
        int height = 0;
        BlockPos checkPos = entity.getPosition().down();
        while(entity.world.getBlockState(checkPos) == Blocks.AIR)
        {
            height ++;
            checkPos = checkPos.down();
        }
        return height;
    }
}