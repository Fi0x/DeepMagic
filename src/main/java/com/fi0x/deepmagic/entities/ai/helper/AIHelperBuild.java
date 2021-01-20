package com.fi0x.deepmagic.entities.ai.helper;

import com.fi0x.deepmagic.blocks.partial.DwarfLamp;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class AIHelperBuild
{
    public static void placeLightAt(World world, BlockPos pos)
    {
        BlockPos topBlock = pos.up();
        if(world.getBlockState(topBlock) == Blocks.AIR.getDefaultState())
        {
            if(world.getBlockState(topBlock.up()).isFullBlock()) world.setBlockState(topBlock, ModBlocks.DWARF_LAMP.getDefaultState());
            else world.setBlockState(pos, ModBlocks.DWARF_LAMP.getDefaultState().withProperty(DwarfLamp.FACING, EnumFacing.UP));
        }
    }

    public static void placeInventoryBlock(World world, BlockPos pos, ItemStackHandler inventory)
    {
        for(int i = 0; i < inventory.getSlots(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);
            switch(stack.getItem().getUnlocalizedName())
            {
                case "tile.insanity_cobble":
                    world.setBlockState(pos, ModBlocks.INSANITY_COBBLE.getDefaultState());
                    break;
                case "tile.depth_cobble":
                    world.setBlockState(pos, ModBlocks.DEPTH_COBBLE.getDefaultState());
                    break;
                case "tile.cobblestone":
                    world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
                    break;
                case "tile.dirt":
                    world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                    break;
                case "tile.insanity_dirt":
                    world.setBlockState(pos, ModBlocks.INSANITY_DIRT.getDefaultState());
                    break;
                case "tile.depth_dirt":
                    world.setBlockState(pos, ModBlocks.DEPTH_DIRT.getDefaultState());
                    break;
                default:
                    continue;
            }
            stack.shrink(1);
            return;
        }
        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
    }
}
