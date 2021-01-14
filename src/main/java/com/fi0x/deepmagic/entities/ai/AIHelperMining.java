package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.blocks.DwarfBaseMarker;
import com.fi0x.deepmagic.blocks.partial.DwarfLamp;
import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.*;
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

            mineableBlocks.add(ModBlocks.DEPTH_COAL_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_IRON_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.INSANITY_REDSTONE_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEPTH_LAPIS_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEPTH_GOLD_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEPTH_DIAMOND_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEPTH_EMERALD_ORE.getDefaultState());
            mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_ORE_COMPRESSED.getDefaultState());
        }
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
    public static BlockPos findMiningStartPosition(World world, BlockPos entityLocation, BlockPos baseMarker)
    {
        ArrayList<BlockPos> checkBlocks = new ArrayList<>();
        ArrayList<BlockPos> blocksDone = new ArrayList<>();
        checkBlocks.add(entityLocation);

        while(!checkBlocks.isEmpty())
        {
            int idx = (int) (Math.random() * checkBlocks.size());
            BlockPos pos = checkBlocks.get(idx);
            if(ConfigHandler.showAISearchParticles) ParticleSpawner.spawnParticle(ParticleEnum.DWARF_SEARCH_MINE, pos);

            if((pos.getY() - baseMarker.getY()) % 3 == 0)
            {
                BlockPos possibleStart = getStartBlock(world, pos, baseMarker);
                if(possibleStart != pos) return possibleStart;
            }
            blocksDone.add(pos);

            if(pos.distanceSq(entityLocation) < ConfigHandler.aiSearchRange * ConfigHandler.aiSearchRange)
            {
                checkBlocks.addAll(getNewCheckPositions(world, pos, blocksDone));
            }
            checkBlocks.remove(idx);
        }
        return null;
    }
    private static BlockPos getStartBlock(World world, BlockPos checkPos, BlockPos baseMarker)
    {
        //TODO: check if any adjacent block is a valid start block and return it if true

        return checkPos;
    }
    private static boolean validatePosition(World world, BlockPos pos, BlockPos baseMarker)
    {
        /*
        TODO: Use direction that faces away from baseMarker
         verify if height is valid (% 3 == 0)
         */
        BlockPos freeBlock = pos.south();
        BlockPos rightBlock = pos.east();
        BlockPos rightNext = rightBlock.north();
        BlockPos leftBlock = pos.west();
        BlockPos leftNext = leftBlock.north();

        if(world.getBlockState(freeBlock).getCollisionBoundingBox(world, freeBlock) != null || world.getBlockState(freeBlock.up()).getCollisionBoundingBox(world, freeBlock.up()) != null) return false;
        if(world.getBlockState(rightBlock).getCollisionBoundingBox(world, rightBlock) == null && world.getBlockState(rightNext).getCollisionBoundingBox(world, rightNext) == null) return false;
        if(world.getBlockState(leftBlock).getCollisionBoundingBox(world, leftBlock) == null && world.getBlockState(leftNext).getCollisionBoundingBox(world, leftNext) == null) return false;

        boolean downAir = world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null;
        boolean upAir = world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) == null;
        boolean downBlock = mineableBlocks.contains(world.getBlockState(pos));
        boolean upBlock = mineableBlocks.contains(world.getBlockState(pos.up()));

        Block b = world.getBlockState(pos).getBlock();
        if(b == Blocks.DIRT || b == Blocks.STONE || b == Blocks.SAND) downBlock = true;
        b = world.getBlockState(pos.up()).getBlock();
        if(b == Blocks.DIRT || b == Blocks.STONE || b == Blocks.SAND) downBlock = true;

        if(downBlock && upAir) return true;
        if(downBlock && upBlock) return true;
        return downAir && upBlock;
    }
    private static ArrayList<BlockPos> getNewCheckPositions(World world, BlockPos centerPos, ArrayList<BlockPos> blocksDone)
    {
        ArrayList<BlockPos> positions = new ArrayList<>();

        //TODO: Get reachable floor positions

        return positions;
    }
    public static BlockPos getRandomPosition(BlockPos start, EnumFacing direction, Random rand)
    {
        int distance = rand.nextInt(ConfigHandler.aiSearchRange / 2) + ConfigHandler.aiSearchRange / 2;

        switch(direction)
        {
            case NORTH:
                return start.add(0, 0, -distance);
            case EAST:
                return start.add(distance, 0, 0);
            case SOUTH:
                return start.add(0, 0, distance);
            case WEST:
                return start.add(-distance, 0, 0);
        }

        return start;
    }

    public static BlockPos findChest(World world, BlockPos... positions)
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
                            if(world.getBlockState(pos.add(x, y, z)).getBlock() instanceof BlockChest)
                            {
                                if(hasChestSpace(world, pos.add(x, y, z))) return pos.add(x, y, z);
                            }
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