package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
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
    protected final Random random;
    protected BlockPos startPosition;
    protected BlockPos destination;
    protected ArrayList<BlockPos> miningBlocks;
    protected BlockPos chestPos;
    private int digDelay;
    private boolean searchChest;
    private boolean goHome;
    public EnumFacing direction;

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
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.entity.getIdleTime() >= 100 || this.entity.getRNG().nextInt(this.executionChance) != 0) return false;

        if(!AIHelperMining.hasHomePosition(world, entity)) return false;
        return entity.posY < ConfigHandler.dwarfMaxMiningHeight || entity.dimension == 43;//TODO: Use config-handler value
    }
    @Override
    public void startExecuting()
    {
        chestPos = AIHelperMining.findChest(world, entity.homePos, entity.getPosition());

        startPosition = AIHelperMining.findMiningStartPosition(world, this);
        if(startPosition == null) return;
        else if(startPosition == entity.homePos)
        {
            goHome = true;
            return;
        }

        destination = AIHelperMining.getRandomPosition(startPosition, direction, random);
        digDelay = 0;

        getMiningBlocks(startPosition, destination);
        goHome = false;
    }
    @Override
    public boolean shouldContinueExecuting()
    {
        if(entity.getNavigator().noPath())
        {
            if(goHome)
            {
                entity.getNavigator().tryMoveToXYZ(entity.homePos.getX(), entity.homePos.getY(), entity.homePos.getZ(), 1);
                return !entity.getNavigator().noPath();
            }
            inventoryToChest();
            if(searchChest) searchAndGoToChest();

            if(digDelay == 0)
            {
                return tryToMine();
            } else digDelay--;
        }
        return true;
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

        while(start != end && miningBlocks.size() <= ConfigHandler.aiSearchRange * 2)
        {
            if(AIHelperMining.isMineable(world, start.up())) miningBlocks.add(start.up());
            else if(world.getBlockState(start.up()).getCollisionBoundingBox(world, start.up()) != null) break;
            else miningBlocks.add(start.up());

            if(AIHelperMining.isMineable(world, start)) miningBlocks.add(start);
            else if(world.getBlockState(start).getCollisionBoundingBox(world, start) != null) break;
            else miningBlocks.add(start);

            start = start.add(xDifference, 0, zDifference);
        }
    }
    protected void inventoryToChest()
    {
        if(chestPos == null || entity.getDistanceSq(chestPos) > 64) return;
        searchChest = false;

        TileEntityChest te = null;
        try
        {
            te = (TileEntityChest) world.getTileEntity(chestPos);
        } catch(Exception ignored)
        {
        }
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
    }
    protected void searchAndGoToChest()
    {
        if(chestPos == null) chestPos = AIHelperMining.findChest(world, entity.homePos, entity.getPosition());
        if(chestPos != null)
        {
            entity.getNavigator().tryMoveToXYZ(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1);
            if(entity.getNavigator().noPath()) searchChest = false;
        }
    }
    protected boolean tryToMine()
    {
        if(miningBlocks.isEmpty()) return true;

        if(entity.getDistanceSq(miningBlocks.get(0)) < 64)
        {
            while(!miningBlocks.isEmpty() && world.getBlockState(miningBlocks.get(0)).getCollisionBoundingBox(world, miningBlocks.get(0)) == null)
            {
                BlockPos floor = new BlockPos(miningBlocks.get(0).getX(), entity.posY - 1, miningBlocks.get(0).getZ());
                if(world.getBlockState(floor).getBlock() == Blocks.AIR) break;
                miningBlocks.remove(0);
            }
            if(miningBlocks.isEmpty()) return false;

            if(!digAtBlockPos(miningBlocks.get(0))) return false;
            if(!entity.getNavigator().noPath()) return true;
            if(entity.getDistanceSq(entity.homePos) > ConfigHandler.dwarfMineRange * ConfigHandler.dwarfMineRange)
            {
                entity.getNavigator().tryMoveToXYZ(entity.homePos.getX(), entity.homePos.getY(), entity.homePos.getZ(), 1);
                miningBlocks.clear();
                goHome = true;
                return true;
            }
            if(entity.getNavigator().noPath()) entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX() + 0.5, miningBlocks.get(0).getY(), miningBlocks.get(0).getZ() + 0.5, 1);
            if(world.getLightBrightness(entity.getPosition()) == 0) AIHelperMining.placeLightAt(world, entity.getPosition());
            digDelay = 20;
        } else
        {
            entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX(), miningBlocks.get(0).getY(), miningBlocks.get(0).getZ(), 1);
            return !entity.getNavigator().noPath();
        }
        return true;
    }
    protected boolean digAtBlockPos(BlockPos pos)
    {
        BlockPos floor = new BlockPos(pos.getX(), entity.posY - 1, pos.getZ());
        if(!hasWalls(floor.up())) return false;
        if(world.getBlockState(floor).getBlock() instanceof BlockAir) world.setBlockState(floor, ModBlocks.INSANITY_COBBLE.getDefaultState());//TODO: Use block from dwarf inventory
        Block block = world.getBlockState(pos).getBlock();

        if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null) return true;

        ItemStack droppedItemStack;
        if(block == Blocks.LAPIS_ORE) droppedItemStack = new ItemStack(Items.DYE, block.quantityDropped(random), 4);
        else if(block == Blocks.STONE) droppedItemStack = new ItemStack(Blocks.COBBLESTONE);
        else droppedItemStack = new ItemStack(block.getItemDropped(world.getBlockState(pos), random, 1), block.quantityDropped(random));

        if(!ItemHandlerHelper.insertItemStacked(entity.itemHandler, droppedItemStack, false).isEmpty())
        {
            if(chestPos != null && world.getBlockState(chestPos).getBlock() == Blocks.CHEST)
            {
                entity.getNavigator().tryMoveToXYZ(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1);
                searchChest = true;
                return true;
            } else return false;
        }

        SoundEvent sound = SoundEvents.BLOCK_STONE_BREAK;
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1, (float) (0.9 + random.nextFloat() * 0.1));
        world.setBlockToAir(pos);
        return true;
    }
    private boolean hasWalls(BlockPos floorPos)
    {
        BlockPos right = floorPos.east().south();
        BlockPos left = floorPos.west().south();
        switch(direction)
        {
            case EAST:
                right = floorPos.south().west();
                left = floorPos.north().west();
                break;
            case SOUTH:
                right = floorPos.west().north();
                left = floorPos.east().north();
                break;
            case WEST:
                right = floorPos.north().east();
                left = floorPos.south().east();
                break;
        }
        boolean rightOK = AIHelperMining.isWallBlock(world, right);
        boolean leftOK = AIHelperMining.isWallBlock(world, left);

        for(int i = 0; i < 2; i++)
        {
            right = AIHelperMining.getNextBlock(right, direction);
            left = AIHelperMining.getNextBlock(left, direction);
            rightOK = rightOK || AIHelperMining.isWallBlock(world, right);
            leftOK = leftOK || AIHelperMining.isWallBlock(world, left);
        }

        return rightOK && leftOK;
    }
}