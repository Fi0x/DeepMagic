package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.blocks.MinerStash;
import com.fi0x.deepmagic.entities.ai.helper.AIHelperBuild;
import com.fi0x.deepmagic.entities.ai.helper.AIHelperMining;
import com.fi0x.deepmagic.entities.ai.helper.AIHelperSearch;
import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
    public final EntityDwarf entity;
    protected final double speed;
    protected final float probability;
    protected final Random random;
    protected BlockPos startPosition;
    protected BlockPos destination;
    protected ArrayList<BlockPos> miningBlocks;
    protected BlockPos storagePos;
    private int digDelay;
    private boolean searchStorage;
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

        if(!AIHelperSearch.hasHomePosition(world, entity)) return false;
        return entity.posY < ConfigHandler.dwarfMaxMiningHeight || entity.dimension == ConfigHandler.dimensionIdDepthID;
    }
    @Override
    public void startExecuting()
    {
        storagePos = AIHelperSearch.findStorage(world, entity.homePos, entity.getPosition());

        startPosition = AIHelperMining.findMiningStartPosition(world, this);
        if(startPosition == null) return;
        else if(startPosition == entity.homePos)
        {
            goHome = true;
            return;
        }

        miningBlocks = AIHelperMining.getMineBlocks(world, startPosition, direction, random);
        digDelay = 0;

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
            inventoryToStorage();
            if(searchStorage)
            {
                searchAndGoToStorage();
                return true;
            }

            if(digDelay == 0)
            {
                return tryToMine();
            } else digDelay--;
        }
        return true;
    }

    protected void inventoryToStorage()
    {
        for(int i = 0; i < entity.itemHandler.getSlots(); i++)
        {
            if(entity.itemHandler.getStackInSlot(i).isEmpty()) return;
        }
        if(storagePos == null || entity.getDistanceSq(storagePos) > 64) return;
        searchStorage = false;

        TileEntity te = null;
        try
        {
            te = world.getTileEntity(storagePos);
        } catch(Exception ignored)
        {
        }
        if(te == null)
        {
            storagePos = null;
            return;
        }

        if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
        {
            IItemHandler h = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

            for(int i = 0; i < entity.itemHandler.getSlots(); i++)
            {
                if(ItemHandlerHelper.insertItemStacked(h, entity.itemHandler.getStackInSlot(i), false).isEmpty()) entity.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                else
                {
                    storagePos = AIHelperSearch.findStorage(world, storagePos, entity.getPosition(), entity.homePos);
                    if(storagePos != null) searchStorage = true;
                    break;
                }
            }
        } else System.out.println("Capability not found");
    }
    protected void searchAndGoToStorage()
    {
        if(storagePos == null) storagePos = AIHelperSearch.findStorage(world, entity.homePos, entity.getPosition());
        if(storagePos != null)
        {
            entity.getNavigator().tryMoveToXYZ(storagePos.getX(), storagePos.getY(), storagePos.getZ(), 1);
            if(entity.getNavigator().noPath()) searchStorage = false;
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
                if(world.isAirBlock(floor)) break;
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
            if(world.getLightBrightness(entity.getPosition()) <= 0) AIHelperBuild.placeLightAt(world, entity.getPosition());
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
        if(!AIHelperSearch.hasWalls(world, floor, direction) && !AIHelperSearch.isBridge(world, floor)) return false;
        if(world.getBlockState(floor).getBlock() instanceof BlockAir) AIHelperBuild.placeInventoryBlock(world, floor, entity.itemHandler);
        Block block = world.getBlockState(pos).getBlock();

        if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null) return true;

        ArrayList<BlockPos> surroundingWater = AIHelperSearch.getAdjacentWater(world, pos);
        for(BlockPos waterBlock : surroundingWater)
        {
            AIHelperBuild.placeInventoryBlock(world, waterBlock, entity.itemHandler);
        }

        miningBlocks.addAll(1, AIHelperSearch.getOreCluster(world, pos));

        ItemStack droppedItemStack;
        if(block == Blocks.LAPIS_ORE) droppedItemStack = new ItemStack(Items.DYE, block.quantityDropped(random), 4);
        else if(block == Blocks.STONE) droppedItemStack = new ItemStack(Blocks.COBBLESTONE);
        else droppedItemStack = new ItemStack(block.getItemDropped(world.getBlockState(pos), random, 1), block.quantityDropped(random));

        if(!ItemHandlerHelper.insertItemStacked(entity.itemHandler, droppedItemStack, false).isEmpty())
        {
            System.out.println("Inventory full");
            if(storagePos != null && (world.getBlockState(storagePos).getBlock() instanceof BlockChest || world.getBlockState(storagePos).getBlock() instanceof MinerStash))
            {
                System.out.println("Moving to storage");
                entity.getNavigator().tryMoveToXYZ(storagePos.getX(), storagePos.getY(), storagePos.getZ(), 1);
                searchStorage = true;
                return true;
            } else return false;
        }

        SoundEvent sound = SoundEvents.BLOCK_STONE_BREAK;
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1, (float) (0.9 + random.nextFloat() * 0.1));
        world.setBlockToAir(pos);
        return true;
    }
}