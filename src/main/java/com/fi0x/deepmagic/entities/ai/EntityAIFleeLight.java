package com.fi0x.deepmagic.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAIFleeLight extends EntityAIFleeSun
{
    private final EntityCreature creature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final World world;

    public EntityAIFleeLight(EntityCreature theCreatureIn, double movementSpeedIn)
    {
        super(theCreatureIn, movementSpeedIn);
        this.movementSpeed = movementSpeedIn;
        this.creature = theCreatureIn;
        world = theCreatureIn.world;
    }

    @Override
    public boolean shouldExecute()
    {
        if(world.getLight(creature.getPosition()) > 10)
        {
            Vec3d vec3d = this.findPossibleShelter();
            if (vec3d == null)
            {
                return false;
            }
            else
            {
                this.shelterX = vec3d.x;
                this.shelterY = vec3d.y;
                this.shelterZ = vec3d.z;
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting()
    {
        this.creature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    private Vec3d findPossibleShelter()
    {
        Random random = this.creature.getRNG();
        BlockPos blockpos = new BlockPos(this.creature.posX, this.creature.getEntityBoundingBox().minY, this.creature.posZ);

        for (int i = 0; i < 20; ++i)
        {
            BlockPos blockpos1 = blockpos.add(random.nextInt(40) - 20, random.nextInt(12) - 6, random.nextInt(40) - 20);

            if(world.getLight(blockpos1) < 7 && world.isAirBlock(blockpos1))
            {
                return new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
            }
        }

        return null;
    }
}