package com.fi0x.deepmagic.entities.ai.navigation;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DwarfPathFinder extends PathFinder
{
    public DwarfPathFinder(NodeProcessor processor)
    {
        super(processor);
    }

    @Nullable
    @Override
    public Path findPath(@Nonnull IBlockAccess chunkCache, @Nonnull EntityLiving entity, @Nonnull BlockPos targetPos, float maxDistance)
    {
        return super.findPath(chunkCache, entity, targetPos, (float) (ConfigHandler.dwarfMineRange * 4));
    }
}
