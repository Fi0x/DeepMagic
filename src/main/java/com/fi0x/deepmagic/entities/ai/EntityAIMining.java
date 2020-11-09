package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Random;

public class EntityAIMining extends EntityAIBase
{
    protected final int executionChance;
    protected final World world;
    protected final EntityDwarf entity;
    protected final double speed;
    protected final float probability;
    protected final int maxExecutionHeight = 50;
    protected final Random random;
    protected BlockPos startPosition;
    protected BlockPos destination;
    protected ArrayList<BlockPos> miningBlocks;
    protected BlockPos chestPos;
    private int digDelay;

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
        miningBlocks = new ArrayList<>();
        AIHelper.fillMiningWhitelist();
    }

    @Override
    public boolean shouldExecute()
    {
        if (this.entity.getIdleTime() >= 100 || this.entity.getRNG().nextInt(this.executionChance) != 0) return false;
        return entity.posY <= maxExecutionHeight;
    }

    @Override
    public void startExecuting()
    {
        chestPos = findChest(entity.getPosition());

        startPosition = entity.getPosition();
        destination = getRandomPosition();
        digDelay = 0;

        getMiningBlocks(startPosition, destination);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if(entity.getNavigator().noPath())
        {
            if(digDelay == 0 && !miningBlocks.isEmpty())
            {
                while(!miningBlocks.isEmpty() && world.getBlockState(miningBlocks.get(0)).getBlock() instanceof BlockAir) miningBlocks.remove(0);
                if(miningBlocks.isEmpty()) return false;
                if(!digAtBlockPos(miningBlocks.get(0))) return false;
                entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX() + 0.5, miningBlocks.get(0).getY(), miningBlocks.get(0).getZ() + 0.5, 1);
                if(world.getLightBrightness(miningBlocks.get(0)) < 0.09) placeLightAt(miningBlocks.get(0));
                digDelay = 20;
            } else digDelay--;
            return !miningBlocks.isEmpty();
        } else return true;
    }

    protected boolean digAtBlockPos(BlockPos pos)
    {
        BlockPos floor = new BlockPos(pos.getX(), entity.posY - 1, pos.getZ());
        if(world.getBlockState(floor).getBlock() instanceof BlockAir) world.setBlockState(floor, ModBlocks.INSANITY_COBBLE.getDefaultState());
        Block block = world.getBlockState(pos).getBlock();

        ItemStack dropppedItemStack;
        if(block.getDefaultState() == Blocks.LAPIS_ORE.getDefaultState()) dropppedItemStack = new ItemStack(Items.DYE, block.quantityDropped(random), 4);
        else dropppedItemStack = new ItemStack(block.getItemDropped(world.getBlockState(pos), random, 1), block.quantityDropped(random));
        if(!ItemHandlerHelper.insertItemStacked(entity.itemHandler, dropppedItemStack, false).isEmpty())
        {
            if(chestPos != null && world.getBlockState(chestPos).getBlock() == Blocks.CHEST) inventoryToChest();
            if(!ItemHandlerHelper.insertItemStacked(entity.itemHandler, dropppedItemStack, false).isEmpty()) return false;
        }

        world.setBlockToAir(pos);
        return true;
    }
    protected void placeLightAt(BlockPos pos)
    {
        BlockPos ceiling = new BlockPos(pos.getX(), entity.posY + 2, pos.getZ());
        world.setBlockState(ceiling, ModBlocks.BRIGHT_INSANITY_STONE.getDefaultState());
    }
    protected void getMiningBlocks(BlockPos start, BlockPos end)
    {
        int xDifference = 0;
        int zDifference = 0;

        if(start.getX() == end.getX())
        {
            if(start.getZ() < end.getZ()) zDifference = 1;
            else zDifference = -1;
        } else
        {
            if(start.getX() < end.getX()) xDifference = 1;
            else xDifference = -1;
        }

        while(start != end && miningBlocks.size() < 30 && AIHelper.mineableBlocks.contains(world.getBlockState(start)) && AIHelper.mineableBlocks.contains(world.getBlockState(start.add(0, 1, 0))))
        {
            miningBlocks.add(start.add(0, 1, 0));
            miningBlocks.add(start);
            start = start.add(xDifference, 0, zDifference);
        }
    }
    protected BlockPos getRandomPosition()
    {
        int xIncrease = 0;
        int zIncrease = 0;
        if(Math.random() < 0.5) zIncrease = random.nextInt(ConfigHandler.aiSearchRange - ConfigHandler.aiSearchRange / 2);
        else xIncrease = random.nextInt(ConfigHandler.aiSearchRange - ConfigHandler.aiSearchRange / 2);

        return entity.getPosition().add(xIncrease, 0, zIncrease);
    }
    protected BlockPos findChest(BlockPos pos)
    {
        int height = ConfigHandler.aiSearchRange / 4;

        for(int range = 0; range <= ConfigHandler.aiSearchRange; range++)
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
    protected void inventoryToChest()
    {
        BlockPos currentPos = entity.getPosition();
        entity.getNavigator().tryMoveToXYZ(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1);

        TileEntity te = null;
        try
        {
            te = world.getTileEntity(chestPos);
        } catch (Exception ignored) { }
        if(te == null)
        {
            chestPos = null;
            return;
        }
        IItemHandler h = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for(int i = 0; i < entity.itemHandler.getSlots(); i++)
        {
            if(ItemHandlerHelper.insertItemStacked(h, entity.itemHandler.getStackInSlot(i), false).isEmpty()) entity.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }

        entity.getNavigator().tryMoveToXYZ(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1);
    }
}