package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class AIHelper
{
    public static ArrayList<IBlockState> mineableBlocks = null;

    public static void fillMiningWhitelist()
    {
        mineableBlocks = new ArrayList<>();
        mineableBlocks.add(Blocks.AIR.getDefaultState());
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

    public static BlockPos findMiningStartPosition(World world, BlockPos entityLocation)
    {
        int i = (int) (Math.random() * 2);
        if(i == 0) i = -1;

        for(int r = i; Math.abs(r) <= ConfigHandler.aiSearchRange; r += i)
        {
            for(int x = -r; x <= Math.abs(r); x++)
            {
                for (int z = -r; z <= Math.abs(r); z++)
                {
                    BlockPos pos = entityLocation.add(x, 0, z);
                    if(mineableBlocks.contains(world.getBlockState(pos)) && mineableBlocks.contains(world.getBlockState(pos.up())))
                    {
                        if(world.getBlockState(pos) != Blocks.AIR.getDefaultState() || world.getBlockState(pos.up()) != Blocks.AIR.getDefaultState()) return pos;
                    }
                }
            }
        }
        return null;
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
}