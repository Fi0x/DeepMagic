package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.blocks.partial.DwarfLamp;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class AIHelperMining
{
    public static ArrayList<IBlockState> mineableBlocks = null;

    public static void fillMiningWhitelist()
    {
        mineableBlocks = new ArrayList<>();
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
        mineableBlocks.add(Blocks.GRASS.getDefaultState());
        mineableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE));
        mineableBlocks.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
        mineableBlocks.add(Blocks.NETHERRACK.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_STONE.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_DIRT.getDefaultState());
        mineableBlocks.add(ModBlocks.INSANITY_GRASS.getDefaultState());
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
        if(ConfigHandler.dwarfMineOres)
        {
            mineableBlocks.add(Blocks.COAL_ORE.getDefaultState());
            mineableBlocks.add(Blocks.IRON_ORE.getDefaultState());
            mineableBlocks.add(Blocks.GOLD_ORE.getDefaultState());
            mineableBlocks.add(Blocks.DIAMOND_ORE.getDefaultState());
            mineableBlocks.add(Blocks.EMERALD_ORE.getDefaultState());
            mineableBlocks.add(Blocks.REDSTONE_ORE.getDefaultState());
            mineableBlocks.add(Blocks.LAPIS_ORE.getDefaultState());
            mineableBlocks.add(Blocks.QUARTZ_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_NETHER_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_END_ORE.getDefaultState());

            mineableBlocks.add(ModBlocks.INSANITY_COAL_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_IRON_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_REDSTONE_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_LAPIS_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_GOLD_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_DIAMOND_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_EMERALD_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_DEEP_CRYSTAL_ORE.getDefaultState());
        }
    }

    public static BlockPos findMiningStartPosition(World world, BlockPos entityLocation, EnumFacing direction)
    {
        ArrayList<BlockPos> checkBlocks = new ArrayList<>();
        ArrayList<BlockPos> blocksDone = new ArrayList<>();
        checkBlocks.add(entityLocation);

        while(!checkBlocks.isEmpty())
        {
            BlockPos pos = checkBlocks.get(0);
            if(validatePosition(world, pos, direction)) return pos;
            blocksDone.add(pos);

            if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null && world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) == null && world.getBlockState(pos.down()).isFullBlock())
            {
                int dX = Math.max(entityLocation.getX(), pos.getX()) - Math.min(entityLocation.getX(), pos.getX());
                int dY = Math.max(entityLocation.getY(), pos.getY()) - Math.min(entityLocation.getY(), pos.getY());
                int dZ = Math.max(entityLocation.getZ(), pos.getZ()) - Math.min(entityLocation.getZ(), pos.getZ());
                int distance = dX * dX + dY * dY + dZ * dZ;

                if(distance <= ConfigHandler.aiSearchRange * ConfigHandler.aiSearchRange)
                {
                    if(!blocksDone.contains(pos.north()) && !checkBlocks.contains(pos.north())) checkBlocks.add(pos.north());

                    if(!blocksDone.contains(pos.east()) && !checkBlocks.contains(pos.east())) checkBlocks.add(pos.east());

                    if(!blocksDone.contains(pos.south()) && !checkBlocks.contains(pos.south())) checkBlocks.add(pos.south());

                    if(!blocksDone.contains(pos.west()) && !checkBlocks.contains(pos.west())) checkBlocks.add(pos.west());
                }
            }
            checkBlocks.remove(0);
        }
        return null;
    }
    private static boolean validatePosition(World world, BlockPos pos, EnumFacing direction)
    {
        BlockPos freeBlock = pos.south();
        BlockPos rightBlock = pos.east();
        BlockPos rightNext = rightBlock.north();
        BlockPos leftBlock = pos.west();
        BlockPos leftNext = leftBlock.north();
        if(direction == EnumFacing.EAST)
        {
            freeBlock = pos.west();
            rightBlock = pos.south();
            rightNext = rightBlock.east();
            leftBlock = pos.north();
            leftNext = leftBlock.east();
        }
        else if(direction == EnumFacing.SOUTH)
        {
            freeBlock = pos.north();
            rightBlock = pos.west();
            rightNext = rightBlock.south();
            leftBlock = pos.east();
            leftNext = leftBlock.south();
        }
        else if(direction == EnumFacing.WEST)
        {
            freeBlock = pos.east();
            rightBlock = pos.north();
            rightNext = rightBlock.west();
            leftBlock = pos.south();
            leftNext = leftBlock.west();
        }

        if(world.getBlockState(freeBlock).getCollisionBoundingBox(world, freeBlock) != null) return false;
        if(world.getBlockState(freeBlock.up()).getCollisionBoundingBox(world, freeBlock.up()) != null) return false;
        if(world.getBlockState(rightBlock).getBlock() == Blocks.AIR && world.getBlockState(rightNext).getBlock() == Blocks.AIR) return false;
        if(world.getBlockState(leftBlock).getBlock() == Blocks.AIR && world.getBlockState(leftNext).getBlock() == Blocks.AIR) return false;

        boolean downBlock = mineableBlocks.contains(world.getBlockState(pos));
        boolean downAir = world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null;
        boolean upBlock = mineableBlocks.contains(world.getBlockState(pos.up()));
        boolean upAir = world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) == null;

        if(downBlock && upAir) return true;
        if(downBlock && upBlock) return true;
        return downAir && upBlock;
    }
    public static BlockPos getRandomPosition(BlockPos start, EnumFacing direction, Random rand)
    {
        int distance = rand.nextInt(ConfigHandler.aiSearchRange / 2) + ConfigHandler.aiSearchRange / 2;

        if(direction == EnumFacing.NORTH) return start.add(0, 0, -distance);
        if(direction == EnumFacing.EAST) return start.add(distance, 0, 0);
        if(direction == EnumFacing.SOUTH) return start.add(0, 0, distance);
        if(direction == EnumFacing.WEST) return start.add(-distance, 0, 0);

        return start;
    }

    public static BlockPos findChest(World world, BlockPos pos)
    {
        if(pos == null) return null;
        for(int range = 0; range <= ConfigHandler.aiSearchRange; range++)
        {
            for(int y = -range / 4; y <= range / 4; y++)
            {
                for(int x = -range; x <= range; x++)
                {
                    for(int z = -range; z <= range; z++)
                    {
                        if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest)
                        {
                            if(hasChestSpace(world, pos.add(x, y, z))) return pos.add(x, y, z);
                        }
                    }
                }
            }
        }
        return null;
    }
    private static boolean hasChestSpace(World world, BlockPos pos)
    {
        TileEntityChest te = ((TileEntityChest) world.getTileEntity(pos));
        if(te == null) return false;

        for(int checkSlot = 0; checkSlot < te.getSizeInventory(); checkSlot++)
        {
            if(te.getStackInSlot(checkSlot).isEmpty()) return true;
        }
        return false;
    }

    public static void placeLightAt(World world, BlockPos pos)
    {
        BlockPos topBlock = pos.up();
        if(world.getBlockState(topBlock) == Blocks.AIR.getDefaultState())
        {
            if(world.getBlockState(topBlock.up()).isFullBlock()) world.setBlockState(topBlock, ModBlocks.DWARF_LAMP.getDefaultState());
            else world.setBlockState(pos, ModBlocks.DWARF_LAMP.getDefaultState().withProperty(DwarfLamp.FACING, EnumFacing.UP));
        }
    }
}