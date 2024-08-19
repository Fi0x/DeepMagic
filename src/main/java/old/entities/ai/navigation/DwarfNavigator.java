package com.fi0x.deepmagic.entities.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DwarfNavigator extends PathNavigateGround
{
    public DwarfNavigator(EntityLiving entitylivingIn, World worldIn)
    {
        super(entitylivingIn, worldIn);
    }

    @Nonnull
    @Override
    protected PathFinder getPathFinder()
    {
        this.nodeProcessor = new WalkNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(true);
        return new DwarfPathFinder(this.nodeProcessor);
    }
}
