package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIMining extends EntityAIBase
{
    protected final int executionChance;
    protected final World world;
    protected final EntityCreature entity;
    protected final double speed;
    protected final int searchRange = 30;
    protected final float probability;
    protected final int maxExecutionHeight = 50;
    protected double x;
    protected double y;
    protected double z;
    protected BlockPos chestPos;
    protected TileEntityChest chestEntity;

    public EntityAIMining(EntityDwarf entity)
    {
        this(entity, 1);
    }
    public EntityAIMining(EntityDwarf entity, int speed)
    {
        this(entity, speed, 50);
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
        if (this.entity.getIdleTime() >= 100 || this.entity.getRNG().nextInt(this.executionChance) != 0) return false;
        if (entity.posY > maxExecutionHeight) return false;

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
        TileEntity te = world.getTileEntity(chestPos);
        if(te instanceof TileEntityChest) chestEntity = (TileEntityChest) te;

        this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
        //TODO: get a random position that might be a wall
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        //TODO: start digging a tunnel to the new destination
        if(entity.getNavigator().noPath())
        {
            BlockPos pos = getRandomSurroundingBlock();
            digAtBlockPos(pos);
            digAtBlockPos(pos.add(0, (Math.random() * 3) - 1, 0));
            return false;
        }
        return true;
    }

    protected void digAtBlockPos(BlockPos pos)
    {
        //TODO: find out which item will be dropped by the block at specified position

        int currentSlot = 0;
        ItemStack currentSlotContent = chestEntity.getStackInSlot(currentSlot);
        while(currentSlotContent != ItemStack.EMPTY || currentSlot < chestEntity.getSizeInventory())
        {
            //TODO: check if dropped item can be placed in current inventory-slot
        }
        //TODO: store Item in inventory slot if slot is not null
        world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos).getBlock().getDefaultState(), 1);
        world.setBlockToAir(pos);
    }
    protected BlockPos getRandomSurroundingBlock()
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
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.entity, searchRange, searchRange / 3);
            if(vec3d == null) return RandomPositionGenerator.findRandomTarget(this.entity, searchRange, searchRange / 3);
            else return vec3d;
        }
        if(entity.getRNG().nextFloat() >= this.probability) return RandomPositionGenerator.getLandPos(this.entity, searchRange, searchRange / 3);
        else return RandomPositionGenerator.findRandomTarget(this.entity, searchRange, searchRange / 3);
    }
    protected BlockPos findChest(BlockPos pos)
    {
        int height = searchRange / 4;

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
        return null;
    }
}