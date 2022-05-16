package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.world.StructureChecker;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class QuarryHelper
{
    public static final ArrayList<Block> blacklistedBlocks = new ArrayList<>();

    static
    {
        blacklistedBlocks.add(ModBlocks.RITUAL_STRUCTURE);
        blacklistedBlocks.add(Blocks.BEDROCK);
    }

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
        for(BlockPos offset : StructureChecker.ritualFloorLocations)
        {
            BlockPos position = center.add(offset);
            if(world.isAirBlock(position)) return position;
        }
        return null;
    }

    public static boolean isBlacklistedBlock(World world, BlockPos pos)
    {
        return blacklistedBlocks.contains(world.getBlockState(pos).getBlock());
    }
}
