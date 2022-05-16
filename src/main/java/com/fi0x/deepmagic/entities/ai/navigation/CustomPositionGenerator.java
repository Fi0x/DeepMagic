package com.fi0x.deepmagic.entities.ai.navigation;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CustomPositionGenerator
{
    @Nullable
    public static Vec3d findRandomTarget(EntityCreature entityCreatureIn, int xz, int y)
    {
        return generateRandomPos(entityCreatureIn, xz, y, true);
    }

    @Nullable
    public static Vec3d getLandPos(EntityCreature entity, int xz, int y)
    {
        return generateRandomPos(entity, xz, y, false);
    }

    @Nullable
    private static Vec3d generateRandomPos(EntityCreature entity, int xz, int y, boolean allowWater)
    {
        PathNavigate entityNavigator = entity.getNavigator();
        Random random = entity.getRNG();
        boolean shouldReturnHome;

        if(entity.hasHome())
        {
            double d0 = entity.getHomePosition().distanceSq(MathHelper.floor(entity.posX), MathHelper.floor(entity.posY), MathHelper.floor(entity.posZ)) + 4.0D;
            double d1 = entity.getMaximumHomeDistance() + (float) xz;
            shouldReturnHome = d0 < d1 * d1;
        } else
            shouldReturnHome = false;

        boolean flag1 = false;
        float f = -99999.0F;
        int xOffset = 0;
        int yOffset = 0;
        int zOffset = 0;

        for(int i = 0; i < 10; i++)
        {
            int xAdd = random.nextInt(2 * xz + 1) - xz;
            int yAdd = random.nextInt(2 * y + 1) - y;
            int zAdd = random.nextInt(2 * xz + 1) - xz;

            if(entity.hasHome() && xz > 1)
            {
                BlockPos blockpos = entity.getHomePosition();

                if(entity.posX > (double) blockpos.getX())
                    xAdd -= random.nextInt(xz / 2);
                else
                    xAdd += random.nextInt(xz / 2);

                if(entity.posZ > (double) blockpos.getZ())
                    zAdd -= random.nextInt(xz / 2);
                else
                    zAdd += random.nextInt(xz / 2);
            }

            BlockPos possibleDestination = new BlockPos((double) xAdd + entity.posX, (double) yAdd + entity.posY, (double) zAdd + entity.posZ);

            if((!shouldReturnHome || entity.isWithinHomeDistanceFromPosition(possibleDestination)) && entityNavigator.canEntityStandOnPos(possibleDestination))
            {
                if(!allowWater)
                {
                    possibleDestination = moveAboveSolid(possibleDestination, entity);
                    if(isWaterDestination(possibleDestination, entity))
                        continue;
                }

                float f1 = entity.getBlockPathWeight(possibleDestination);

                if(f1 > f)
                {
                    f = f1;
                    xOffset = xAdd;
                    yOffset = yAdd;
                    zOffset = zAdd;
                    flag1 = true;
                }
            }
        }

        if(flag1)
        {
            int groundDistance = getDistanceToGround(entity.world, new BlockPos(xOffset + entity.posX, yOffset + entity.posY, zOffset + entity.posZ));
            if(groundDistance > 5)
                yOffset -= (groundDistance - 2);
            else if(groundDistance < 1)
                yOffset++;
            return new Vec3d((double) xOffset + entity.posX, (double) yOffset + entity.posY, (double) zOffset + entity.posZ);
        }
        else
            return null;
    }

    private static BlockPos moveAboveSolid(BlockPos blockPos, EntityCreature entity)
    {
        if(!entity.world.getBlockState(blockPos).getMaterial().isSolid())
            return blockPos;
        else
        {
            BlockPos destinationPos = blockPos.up();

            while(destinationPos.getY() < entity.world.getHeight() && entity.world.getBlockState(destinationPos).getMaterial().isSolid())
                destinationPos = destinationPos.up();

            return destinationPos;
        }
    }

    private static boolean isWaterDestination(BlockPos blockPos, EntityCreature entity)
    {
        return entity.world.getBlockState(blockPos).getMaterial() == Material.WATER;
    }

    private static int getDistanceToGround(World world, BlockPos position)
    {
        int distanceCounter = 0;
        BlockPos groundSearch = position;
        while(groundSearch.getY() > 0 && !world.getBlockState(groundSearch).getMaterial().isSolid())
        {
            groundSearch = groundSearch.down();
            distanceCounter++;
        }

        return distanceCounter;
    }
}
