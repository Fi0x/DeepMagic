package com.fi0x.deepmagic.entities.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FlightPathFinder extends PathFinder
{
    public FlightPathFinder(NodeProcessor processor)
    {
        super(processor);
    }

    @Nullable
    @Override
    public Path findPath(@Nonnull IBlockAccess worldIn, @Nonnull EntityLiving entityLivingIn, @Nonnull BlockPos targetPos, float maxDistance)
    {
        return super.findPath(worldIn, entityLivingIn, targetPos, maxDistance);
    }
}