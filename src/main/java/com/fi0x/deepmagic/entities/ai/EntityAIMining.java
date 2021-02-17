package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.ai.helper.*;
import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class EntityAIMining extends EntityAIBase
{
    protected final int executionChance;
    public final World world;
    public final EntityDwarf entity;
    protected final double speed;
    protected final float probability;
    public final Random random;
    protected BlockPos startPosition;
    public ArrayList<BlockPos> miningBlocks;
    public ArrayList<BlockPos> additionalBlocks;
    public BlockPos storagePos;
    private int digDelay;
    public boolean searchStorage;
    private boolean goHome;
    public EnumFacing direction;
    public int shaftFloor;

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
        additionalBlocks = new ArrayList<>();
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.entity.getIdleTime() >= 100 || this.entity.getRNG().nextInt(this.executionChance) != 0) return false;

        if(!AIHelperSearchBlocks.hasHomePosition(world, entity)) return false;
        return entity.posY < ConfigHandler.dwarfMaxMiningHeight || entity.dimension == ConfigHandler.dimensionIdDepthID;
    }
    @Override
    public void startExecuting()
    {
        storagePos = AIHelperSearchBlocks.findStorage(world, entity.homePos, entity.getPosition());

        startPosition = AIHelperSearchMines.findMiningStartPosition(world, this);
        if(startPosition == null) return;
        else if(startPosition == entity.homePos)
        {
            goHome = true;
            return;
        }
        shaftFloor = startPosition.getY() - 1;

        miningBlocks = AIHelperSearchMines.getMineBlocks(world, startPosition, direction, random);
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
            AIHelperStorage.inventoryToStorage(this, entity);
            if(searchStorage)
            {
                AIHelperStorage.searchAndGoToStorage(this);
                return true;
            }

            if(digDelay == 0)
            {
                return performAction();
            } else digDelay--;
        }
        return true;
    }

    protected boolean performAction()
    {
        if(!additionalBlocks.isEmpty())
        {
            if(entity.getDistanceSq(miningBlocks.get(0)) < 64)
            {
                AIHelperDig.digAtBlockPos(this, true);
                additionalBlocks.remove(0);
                digDelay = getNextDigDelay();
                return true;
            } else
            {
                entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX(), miningBlocks.get(0).getY(), miningBlocks.get(0).getZ(), 1);
                return !entity.getNavigator().noPath();
            }
        }
        if(miningBlocks.isEmpty()) return false;

        if(entity.getDistanceSq(miningBlocks.get(0)) < 64)
        {
            if(world.getLightBrightness(entity.getPosition()) <= 0)
            {
                AIHelperBuild.placeLightAt(world, entity.getPosition());
                digDelay = getNextDigDelay();
                return true;
            }
            return mineBlock();
        } else
        {
            entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX(), miningBlocks.get(0).getY(), miningBlocks.get(0).getZ(), 1);
            return !entity.getNavigator().noPath();
        }
    }

    private boolean mineBlock()
    {
        if(!AIHelperDig.digAtBlockPos(this, false)) return false;
        if(!entity.getNavigator().noPath()) return true;

        if(entity.getDistanceSq(entity.homePos) > ConfigHandler.dwarfMineRange * ConfigHandler.dwarfMineRange)
        {
            entity.getNavigator().tryMoveToXYZ(entity.homePos.getX(), entity.homePos.getY(), entity.homePos.getZ(), 1);
            miningBlocks.clear();
            additionalBlocks.clear();
            goHome = true;
            return true;
        }

        entity.getNavigator().tryMoveToXYZ(miningBlocks.get(0).getX() + 0.5, miningBlocks.get(0).getY(), miningBlocks.get(0).getZ() + 0.5, 1);

        while(!miningBlocks.isEmpty() && world.getBlockState(miningBlocks.get(0)).getCollisionBoundingBox(world, miningBlocks.get(0)) == null)
        {
            if(miningBlocks.get(0).getY() > shaftFloor && miningBlocks.get(0).getY() < shaftFloor + 4)
            {
                BlockPos floor = new BlockPos(miningBlocks.get(0).getX(), shaftFloor, miningBlocks.get(0).getZ());
                if(world.isAirBlock(floor)) break;
            }
            miningBlocks.remove(0);
        }
        if(miningBlocks.isEmpty()) return false;

        digDelay = getNextDigDelay();
        return true;

    }

    private int getNextDigDelay()
    {
        if(entity.getNavigator().noPath())
        {
            if(world.getLightBrightness(entity.getPosition()) <= 0) return 10;
        } else
        {
            PathPoint destination = Objects.requireNonNull(entity.getNavigator().getPath()).getFinalPathPoint();
            assert destination != null;
            if(world.getLightBrightness(new BlockPos(destination.x, destination.y, destination.z)) <= 0) return 10;
        }

        if(!additionalBlocks.isEmpty())
        {
            return (int) (world.getBlockState(additionalBlocks.get(0)).getBlockHardness(world, additionalBlocks.get(0)) * 20);
        } else if(!miningBlocks.isEmpty())
        {
            return (int) (world.getBlockState(miningBlocks.get(0)).getBlockHardness(world, miningBlocks.get(0)) * 20);
        }
        return 20;
    }
}