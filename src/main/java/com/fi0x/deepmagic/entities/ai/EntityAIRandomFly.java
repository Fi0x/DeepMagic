package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.ai.navigation.CustomPositionGenerator;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class EntityAIRandomFly extends EntityAIWanderAvoidWater
{
    private final EntityLiving entity;

    public EntityAIRandomFly(EntityLiving entity, double speed)
    {
        super((EntityCreature) entity, speed);
        this.entity = entity;
        this.executionChance = 50;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if (!this.mustUpdate)
        {
            if (this.entity.getIdleTime() >= 100)
                return false;

            if (this.entity.getRNG().nextInt(this.executionChance) != 0)
                return false;
        }

        Vec3d vec3d = this.getPosition();
        if (vec3d == null)
            return false;
        else
        {
            this.x = vec3d.x;
            this.y = vec3d.y;
            this.z = vec3d.z;
            this.mustUpdate = false;
            return true;
        }
    }

    @Override
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !entity.getNavigator().noPath();
    }

    @Nullable
    @Override
    protected Vec3d getPosition()
    {
        return this.entity.getRNG().nextFloat() >= this.probability
                ? CustomPositionGenerator.getLandPos((EntityCreature) this.entity, 10, 7)
                : CustomPositionGenerator.findRandomTarget((EntityCreature) this.entity, 10, 7);
    }
}