package com.fi0x.deepmagic.entities.ai.helper;

import com.fi0x.deepmagic.blocks.DwarfBaseMarker;
import com.fi0x.deepmagic.blocks.MinerStash;
import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class AIHelperSearchBlocks
{
    public static BlockPos findStorage(World world, BlockPos... positions)
    {
        if(positions == null || positions.length == 0) return null;
        for(BlockPos pos : positions)
        {
            if(pos == null) continue;
            for(int range = 0; range <= ConfigHandler.aiSearchRange; range++)
            {
                for(int y = -range / 4; y <= range / 4; y++)
                {
                    for(int x = -range; x <= range; x++)
                    {
                        for(int z = -range; z <= range; z++)
                        {
                            Block block = world.getBlockState(pos.add(x, y, z)).getBlock();
                            if(block instanceof BlockChest || block instanceof MinerStash)
                            {
                                if(hasContainerSpace(world, pos.add(x, y, z))) return pos.add(x, y, z);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    private static boolean hasContainerSpace(World world, BlockPos pos)
    {
        IInventory te = ((IInventory) world.getTileEntity(pos));
        if(te == null) return false;

        for(int checkSlot = 0; checkSlot < te.getSizeInventory(); checkSlot++)
        {
            if(te.getStackInSlot(checkSlot).isEmpty()) return true;
        }
        return false;
    }

    public static ArrayList<BlockPos> getAdjacentWater(World world, BlockPos pos)
    {
        ArrayList<BlockPos> water = new ArrayList<>();

        if(world.getBlockState(pos.up()).getBlock() == Blocks.WATER) water.add(pos.up());
        if(world.getBlockState(pos.down()).getBlock() == Blocks.WATER) water.add(pos.down());
        if(world.getBlockState(pos.north()).getBlock() == Blocks.WATER) water.add(pos.north());
        if(world.getBlockState(pos.east()).getBlock() == Blocks.WATER) water.add(pos.east());
        if(world.getBlockState(pos.south()).getBlock() == Blocks.WATER) water.add(pos.south());
        if(world.getBlockState(pos.west()).getBlock() == Blocks.WATER) water.add(pos.west());

        return water;
    }
    public static ArrayList<BlockPos> getOreCluster(World world, ArrayList<BlockPos> alreadyFound, BlockPos firstOre)
    {
        ArrayList<BlockPos> ores = new ArrayList<>();
        ArrayList<BlockPos> toCheck = new ArrayList<>();
        toCheck.add(firstOre);

        while(!toCheck.isEmpty())
        {
            ArrayList<BlockPos> found = getSurroundingOres(world, toCheck.get(0), alreadyFound, ores);
            toCheck.remove(0);
            toCheck.addAll(found);
            ores.addAll(found);
        }

        return ores;
    }
    @SafeVarargs
    private static ArrayList<BlockPos> getSurroundingOres(World world, BlockPos center, ArrayList<BlockPos>... found)
    {
        ArrayList<BlockPos> ores = new ArrayList<>();

        for(EnumFacing direction : EnumFacing.VALUES)
        {
            if(AIHelperSearchMines.oreWhitelist.contains(world.getBlockState(center.offset(direction)))) ores.add(center.offset(direction));
        }

        for(int i = 0; i < ores.size(); i++)
        {
            for(ArrayList<BlockPos> oldPositions : found)
            {
                if(oldPositions.contains(ores.get(i)))
                {
                    ores.remove(i);
                    i--;
                    break;
                }
            }
        }

        return ores;
    }

    public static boolean hasHomePosition(World world, EntityDwarf entity)
    {
        if(entity.homePos != null && world.getBlockState(entity.homePos).getBlock() instanceof DwarfBaseMarker) return true;

        BlockPos pos = entity.getPosition();
        for(int range = 0; range <= ConfigHandler.aiSearchRange * 1.5; range++)
        {
            for(int y = -range / 4; y <= range / 4; y++)
            {
                for(int x = -range; x <= range; x++)
                {
                    for(int z = -range; z <= range; z++)
                    {
                        if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof DwarfBaseMarker)
                        {
                            entity.homePos = pos.add(x, y, z);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasWalls(World world, BlockPos floorPos, EnumFacing direction)
    {
        BlockPos right = floorPos.up().east().south();
        BlockPos left = floorPos.up().west().south();
        switch(direction)
        {
            case EAST:
                right = floorPos.up().south().west();
                left = floorPos.up().north().west();
                break;
            case SOUTH:
                right = floorPos.up().west().north();
                left = floorPos.up().east().north();
                break;
            case WEST:
                right = floorPos.up().north().east();
                left = floorPos.up().south().east();
                break;
        }
        boolean rightOK = AIHelperSearchMines.isWallBlock(world, right);
        boolean leftOK = AIHelperSearchMines.isWallBlock(world, left);

        for(int i = 0; i < 2; i++)
        {
            right = AIHelperSearchMines.getNextBlock(right, direction);
            left = AIHelperSearchMines.getNextBlock(left, direction);
            rightOK = rightOK || AIHelperSearchMines.isWallBlock(world, right);
            leftOK = leftOK || AIHelperSearchMines.isWallBlock(world, left);
        }

        return rightOK && leftOK;
    }

    public static boolean isBridge(World world, BlockPos floorPos)
    {
        BlockPos center = floorPos.down();
        int hangingBlocks = 0;

        if(world.getBlockState(center).getCollisionBoundingBox(world, center) == null) hangingBlocks++;
        if(world.getBlockState(center.north()).getCollisionBoundingBox(world, center.north()) == null) hangingBlocks++;
        if(world.getBlockState(center.east()).getCollisionBoundingBox(world, center.east()) == null) hangingBlocks++;
        if(world.getBlockState(center.south()).getCollisionBoundingBox(world, center.south()) == null) hangingBlocks++;
        if(world.getBlockState(center.west()).getCollisionBoundingBox(world, center.west()) == null) hangingBlocks++;

        return hangingBlocks > 0;
    }
}
