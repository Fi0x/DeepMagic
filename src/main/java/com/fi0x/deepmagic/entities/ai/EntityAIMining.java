package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import net.minecraft.block.Block;
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

import java.util.Random;

public class EntityAIMining extends EntityAIBase
{
    protected final int executionChance;
    protected final World world;
    protected final EntityCreature entity;
    protected final double speed;
    protected final int searchRange = 30;
    protected final float probability;
    protected final int maxExecutionHeight = 50;
    protected final Random random;
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
        random = new Random();
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
            System.out.println("Starting mining ai");
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
        System.out.println("Starting digging process");
        Block block = world.getBlockState(pos).getBlock();
        ItemStack droppedItemStack = new ItemStack(block.getItemDropped(world.getBlockState(pos), random, 1), block.quantityDropped(random));

        int currentSlot = 0;
        ItemStack currentSlotContent = chestEntity.getStackInSlot(currentSlot);
        while(currentSlot < chestEntity.getSizeInventory())
        {
            if(currentSlotContent == ItemStack.EMPTY) break;
            if(currentSlotContent.getItem() == droppedItemStack.getItem())
            {
                if(64 - currentSlotContent.getCount() >= droppedItemStack.getCount()) break;
            }
            currentSlot++;
        }

        System.out.println("Slot " + currentSlot + " can hold the new block");
        if(currentSlot >= chestEntity.getSizeInventory())
        {
            world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos).getBlock().getDefaultState(), 1);
        } else
        {
            if(currentSlotContent == ItemStack.EMPTY) chestEntity.getStackInSlot(currentSlot).setCount(droppedItemStack.getCount());
            else chestEntity.getStackInSlot(currentSlot).setCount(currentSlotContent.getCount() + droppedItemStack.getCount());
        }
        world.setBlockToAir(pos);
        System.out.println("Block destroyed");
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