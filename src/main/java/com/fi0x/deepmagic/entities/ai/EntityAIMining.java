package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIMining extends EntityAIBase
{
    protected final int executionChance;
    protected final EntityCreature entity;
    protected final World world;
    protected double x;
    protected double y;
    protected double z;
    protected final double speed;
    protected final float probability;
    protected BlockPos chestPos;
    private final int searchRange = 30;

    public EntityAIMining(EntityDwarf entity)
    {
        this(entity, 1);
    }
    public EntityAIMining(EntityDwarf entity, int speed)
    {
        this(entity, speed, 120);
    }
    public EntityAIMining(EntityDwarf entity, int speed, int executionChance)
    {
        this.setMutexBits(3);
        this.entity = entity;
        this.world = entity.world;
        this.probability = 0.001F;
        this.speed = speed;
        this.executionChance = executionChance;
    }

    @Override
    public boolean shouldExecute()
    {
        if (this.entity.getIdleTime() >= 100) return false;
        if (this.entity.getRNG().nextInt(this.executionChance) != 0) return false;

        Vec3d vec3d = this.getPosition();

        if (vec3d == null) return false;
        else
        {
            this.x = vec3d.x;
            this.y = vec3d.y;
            this.z = vec3d.z;
            return true;
        }
    }

    @Override
    public void startExecuting()
    {
        chestPos = findChest(entity.getPosition());
        this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
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

    protected BlockPos getRandomBlock()
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
    protected Vec3d getPosition()
    {
        if (entity.isInWater())
        {
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.entity, 15, 7);
            return vec3d == null ? RandomPositionGenerator.findRandomTarget(this.entity, 10, 7) : vec3d;
        }
        return this.entity.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.entity, 10, 7) : RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
    }
    protected BlockPos findChest(BlockPos pos)
    {
        int height = 5;

        for(int range = 0; range <= searchRange; range++)
        {
            int x = -range;
            int z = -range;
            for(int y = -height; y <= height; y++)
            {
                for(; x <= range; x++)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
                for(; z <= range; z++)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
                for(; x >= -range; x--)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
                for(; z >= -range; z--)
                {
                    if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest) return pos.add(x, y, z);
                }
            }
        }
        System.out.println("No chest found");
        return null;
    }
}