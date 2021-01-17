package com.fi0x.deepmagic.entities.ai.helper;

import com.fi0x.deepmagic.entities.ai.EntityAIMining;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class AIHelperMining
{
    private static ArrayList<IBlockState> mineableBlocks = null;
    public static ArrayList<IBlockState> oreWhitelist = null;

    public static void fillMiningWhitelists()
    {
        mineableBlocks = new ArrayList<>();
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
        mineableBlocks.add(Blocks.GRASS.getDefaultState());
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
        mineableBlocks.add(Blocks.NETHERRACK.getDefaultState());

        mineableBlocks.add(ModBlocks.INSANITY_STONE.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_DIRT.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_GRASS.getDefaultState());

        mineableBlocks.add(ModBlocks.DEPTH_STONE.getDefaultState());
        mineableBlocks.add(ModBlocks.DEPTH_DIRT.getDefaultState());
        if(ConfigHandler.dwarfMineResources)
        {
            mineableBlocks.add(Blocks.CLAY.getDefaultState());
            mineableBlocks.add(Blocks.OBSIDIAN.getDefaultState());
            mineableBlocks.add(Blocks.SOUL_SAND.getDefaultState());
            mineableBlocks.add(Blocks.END_STONE.getDefaultState());
            mineableBlocks.add(Blocks.MYCELIUM.getDefaultState());
            mineableBlocks.add(Blocks.GRAVEL.getDefaultState());
            mineableBlocks.add(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.SAND));
            mineableBlocks.add(Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
        }

        oreWhitelist = new ArrayList<>();
        if(ConfigHandler.dwarfMineOres)
        {
            oreWhitelist.add(Blocks.COAL_ORE.getDefaultState());
            oreWhitelist.add(Blocks.IRON_ORE.getDefaultState());
            oreWhitelist.add(Blocks.GOLD_ORE.getDefaultState());
            oreWhitelist.add(Blocks.DIAMOND_ORE.getDefaultState());
            oreWhitelist.add(Blocks.EMERALD_ORE.getDefaultState());
            oreWhitelist.add(Blocks.REDSTONE_ORE.getDefaultState());
            oreWhitelist.add(Blocks.LAPIS_ORE.getDefaultState());
            oreWhitelist.add(Blocks.QUARTZ_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEEP_CRYSTAL_NETHER_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEEP_CRYSTAL_END_ORE.getDefaultState());

            oreWhitelist.add(ModBlocks.INSANITY_COAL_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_IRON_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_REDSTONE_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_LAPIS_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_GOLD_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_DIAMOND_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_EMERALD_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.INSANITY_DEEP_CRYSTAL_ORE.getDefaultState());

            oreWhitelist.add(ModBlocks.DEPTH_COAL_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEPTH_IRON_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEPTH_REDSTONE_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEPTH_LAPIS_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEPTH_GOLD_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEPTH_DIAMOND_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEPTH_EMERALD_ORE.getDefaultState());
            oreWhitelist.add(ModBlocks.DEEP_CRYSTAL_ORE_COMPRESSED.getDefaultState());
        }
    }
    public static BlockPos findMiningStartPosition(World world, EntityAIMining ai)
    {
        ArrayList<BlockPos> checkBlocks = new ArrayList<>();
        ArrayList<BlockPos> blocksDone = new ArrayList<>();
        checkBlocks.add(ai.entity.getPosition());

        while(!checkBlocks.isEmpty())
        {
            int idx = (int) (Math.random() * checkBlocks.size());
            BlockPos pos = checkBlocks.get(idx);
            if(ConfigHandler.showAISearchParticles) ParticleSpawner.spawnParticle(ParticleEnum.DWARF_SEARCH_MINE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

            if((pos.getY() - ai.entity.homePos.getY()) % 3 == 0)
            {
                BlockPos possibleStart = getStartBlock(world, pos, ai);
                if(possibleStart != pos) return possibleStart;
            }
            blocksDone.add(pos);

            if(pos.distanceSq(ai.entity.getPosition()) < ConfigHandler.aiSearchRange * ConfigHandler.aiSearchRange)
            {
                checkBlocks.addAll(getNewCheckPositions(world, pos, blocksDone));
            }
            checkBlocks.remove(idx);
        }
        return null;
    }
    private static BlockPos getStartBlock(World world, BlockPos checkPos, EntityAIMining ai)
    {
        ArrayList<EnumFacing> directions = new ArrayList<>();
        directions.add(EnumFacing.NORTH);
        directions.add(EnumFacing.EAST);
        directions.add(EnumFacing.SOUTH);
        directions.add(EnumFacing.WEST);

        int rand = (int) (Math.random() * 4);
        switch(rand)
        {
            case 0:
                directions.add(directions.get(0));
                directions.remove(0);
            case 1:
                directions.add(directions.get(0));
                directions.remove(0);
            case 2:
                directions.add(directions.get(0));
                directions.remove(0);
                break;
        }

        for(EnumFacing direct : directions)
        {
            BlockPos current = checkPos.north();
            switch(direct)
            {
                case EAST:
                    current = checkPos.east();
                    break;
                case SOUTH:
                    current = checkPos.south();
                    break;
                case WEST:
                    current = checkPos.west();
                    break;
            }

            if(isMineable(world, current))
            {
                if(isValidStart(world, current, direct))
                {
                    ai.direction = direct;
                    return current;
                }
            }
        }

        return checkPos;
    }
    public static boolean isMineable(World world, BlockPos pos)
    {
        if(mineableBlocks.contains(world.getBlockState(pos)) || oreWhitelist.contains(world.getBlockState(pos)))
        {
            return mineableBlocks.contains(world.getBlockState(pos.up())) || oreWhitelist.contains(world.getBlockState(pos.up()));
        }
        return false;
    }
    private static boolean isValidStart(World world, BlockPos startPos, EnumFacing direction)
    {
        BlockPos right = startPos.east();
        BlockPos left = startPos.west();
        BlockPos right2 = right.east();
        BlockPos left2 = left.west();

        switch(direction)
        {
            case EAST:
                right = startPos.south();
                left = startPos.north();
                right2 = right.south();
                left2 = left.north();
                break;
            case SOUTH:
                right = startPos.west();
                left = startPos.east();
                right2 = right.west();
                left2 = left.east();
                break;
            case WEST:
                right = startPos.north();
                left = startPos.south();
                right2 = right.north();
                left2 = left.south();
                break;
        }

        boolean rightOK = isWallBlock(world, right);
        boolean rightNextOK = isWallBlock(world, right2);
        boolean leftOK = isWallBlock(world, left);
        boolean leftNextOK = isWallBlock(world, left2);
        boolean firstRowGood = rightOK && rightNextOK && leftOK && leftNextOK;

        right = getNextBlock(right, direction);
        right2 = getNextBlock(right2, direction);
        left = getNextBlock(left, direction);
        left2 = getNextBlock(left2, direction);

        rightOK = isWallBlock(world, right);
        rightNextOK = isWallBlock(world, right2);
        leftOK = isWallBlock(world, left);
        leftNextOK = isWallBlock(world, left2);
        boolean secondRowGood = rightOK && rightNextOK && leftOK && leftNextOK;

        return firstRowGood && secondRowGood;
    }
    public static boolean isWallBlock(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getCollisionBoundingBox(world, pos) != null || world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) != null;
    }
    public static BlockPos getNextBlock(BlockPos pos, EnumFacing direction)
    {
        switch(direction)
        {
            case NORTH:
                return pos.north();
            case EAST:
                return pos.east();
            case SOUTH:
                return pos.south();
            case WEST:
                return pos.west();
        }
        return pos;
    }
    private static ArrayList<BlockPos> getNewCheckPositions(World world, BlockPos centerPos, ArrayList<BlockPos> blocksDone)
    {
        ArrayList<BlockPos> positions = new ArrayList<>();
        BlockPos[] attachedBlocks = {centerPos.north(), centerPos.east(), centerPos.south(), centerPos.west()};

        for(BlockPos pos : attachedBlocks)
        {
            if(world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) == null)
            {
                if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null)
                {
                    if(world.getBlockState(pos.down()).isFullCube())
                    {
                        if(!blocksDone.contains(pos)) positions.add(pos);
                    } else if(world.getBlockState(pos.down()).getCollisionBoundingBox(world, pos.down()) == null)
                    {
                        if(world.getBlockState(pos.down().down()).isFullCube())
                        {
                            if(!blocksDone.contains(pos.down())) positions.add(pos.down());
                        }
                    }
                } else if(world.getBlockState(pos).isFullCube())
                {
                    if(world.getBlockState(pos.up().up()).getCollisionBoundingBox(world, pos.up().up()) == null)
                    {
                        if(!blocksDone.contains(pos.up())) positions.add(pos.up());
                    }
                }
            }
        }

        return positions;
    }
    public static ArrayList<BlockPos> getMineBlocks(World world, BlockPos start, EnumFacing direction, Random rand)
    {
        int distance = rand.nextInt(ConfigHandler.aiSearchRange / 2) + ConfigHandler.aiSearchRange / 2;
        BlockPos end = start.add(0, 0, -distance);

        switch(direction)
        {
            case EAST:
                end = start.add(distance, 0, 0);
                break;
            case SOUTH:
                end = start.add(0, 0, distance);
                break;
            case WEST:
                end = start.add(-distance, 0, 0);
                break;
        }

        ArrayList<BlockPos> blocks = new ArrayList<>();

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

        while(start != end && blocks.size() <= ConfigHandler.aiSearchRange * 2)
        {
            if(AIHelperMining.isMineable(world, start.up())) blocks.add(start.up());
            else if(world.getBlockState(start.up()).getCollisionBoundingBox(world, start.up()) != null) break;
            else blocks.add(start.up());

            if(AIHelperMining.isMineable(world, start)) blocks.add(start);
            else if(world.getBlockState(start).getCollisionBoundingBox(world, start) != null) break;
            else blocks.add(start);

            start = start.add(xDifference, 0, zDifference);
        }

        return blocks;
    }
}