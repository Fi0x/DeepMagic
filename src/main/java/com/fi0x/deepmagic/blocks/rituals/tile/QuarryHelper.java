package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.world.StructureChecker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuarryHelper
{
    public static BlockPos getRandomFilledStructurePos(World world, BlockPos center)
    {
        for(BlockPos offset : StructureChecker.ritualFloorLocations)
        {
            BlockPos position = center.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualStructure) return position;
        }
        for(BlockPos offset : StructureChecker.ritualArmLocations)
        {
            BlockPos position = center.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualStructure) return position;
        }
        return null;
    }

    public static BlockPos getRandomFreeStructurePos(World world, BlockPos center)
    {
        //TODO: Iterate over the whole structure and find a block that is air
        for(BlockPos offset : StructureChecker.ritualFloorLocations)
        {
            BlockPos position = center.add(offset);
            if(world.isAirBlock(position)) return position;
        }
        return null;
    }
}
