package com.fi0x.deepmagic.entities.ai.helper;

import com.fi0x.deepmagic.blocks.MinerStash;
import com.fi0x.deepmagic.entities.ai.EntityAIMining;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;

public class AIHelperDig
{
    public static boolean digAtBlockPos(EntityAIMining miningAI, ArrayList<BlockPos> miningBlocks, EnumFacing direction, int floorHeight)
    {
        BlockPos pos = miningBlocks.get(0);
        BlockPos floor = new BlockPos(pos.getX(), floorHeight, pos.getZ());

        if(!AIHelperSearchBlocks.hasWalls(miningAI.world, floor, direction) && !AIHelperSearchBlocks.isBridge(miningAI.world, floor)) return false;
        if(miningAI.world.isAirBlock(floor) && miningAI.world.getBlockState(floor.down()).getCollisionBoundingBox(miningAI.world, floor.down()) == null)
        {
            if(miningAI.entity.getPosition().getY() > floor.getY()) AIHelperBuild.placeInventoryBlock(miningAI.world, floor, miningAI.entity.itemHandler);
        }
        Block block = miningAI.world.getBlockState(pos).getBlock();

        if(miningAI.world.getBlockState(pos).getCollisionBoundingBox(miningAI.world, pos) == null) return true;

        if(pos.getY() == floor.getY())
        {
            if(miningAI.world.getBlockState(pos.up(2)).getCollisionBoundingBox(miningAI.world, pos.up(2)) != null) miningBlocks.add(0, new BlockPos(pos.up(2)));
        } else if(pos.getY() == floor.getY() + 3 && miningAI.world.getBlockState(pos.down()).getCollisionBoundingBox(miningAI.world, pos.down()) == null)
        {
            if(miningAI.world.getBlockState(pos.down(2)).getCollisionBoundingBox(miningAI.world, pos.down(2)) != null) miningBlocks.add(0, new BlockPos(pos.down(2)));
        } else if(pos.getY() == floor.getY() + 1)
        {
            for(EnumFacing offset : EnumFacing.Plane.HORIZONTAL)
            {
                if(miningAI.world.getBlockState(pos.offset(offset)).getCollisionBoundingBox(miningAI.world, pos.offset(offset)) == null)
                {
                    if(miningAI.world.getBlockState(pos.offset(offset).up()).getCollisionBoundingBox(miningAI.world, pos.offset(offset).up()) != null) miningBlocks.add(0, new BlockPos(pos.offset(offset).up()));
                    break;
                }
            }
        } else if(pos.getY() == floor.getY() + 2)
        {
            for(EnumFacing offset : EnumFacing.Plane.HORIZONTAL)
            {
                if(miningAI.world.getBlockState(pos.offset(offset)).getCollisionBoundingBox(miningAI.world, pos.offset(offset)) == null)
                {
                    if(miningAI.world.getBlockState(pos.offset(offset).down()).getCollisionBoundingBox(miningAI.world, pos.offset(offset).down()) != null) miningBlocks.add(0, new BlockPos(pos.offset(offset).down()));
                    break;
                }
            }
        }

        ArrayList<BlockPos> surroundingWater = AIHelperSearchBlocks.getAdjacentWater(miningAI.world, pos);
        for(BlockPos waterBlock : surroundingWater)
        {
            AIHelperBuild.placeInventoryBlock(miningAI.world, waterBlock, miningAI.entity.itemHandler);
        }

        miningBlocks.addAll(1, AIHelperSearchBlocks.getOreCluster(miningAI.world, pos));

        Item droppedItem = block.getItemDropped(miningAI.world.getBlockState(pos), miningAI.random, 1);
        int quantity = block.quantityDropped(miningAI.random);
        int itemMeta = block.damageDropped(miningAI.world.getBlockState(pos));
        ItemStack droppedItemStack = new ItemStack(droppedItem, quantity, itemMeta);

        if(!ItemHandlerHelper.insertItemStacked(miningAI.entity.itemHandler, droppedItemStack, false).isEmpty())
        {
            if(miningAI.storagePos != null && (miningAI.world.getBlockState(miningAI.storagePos).getBlock() instanceof BlockChest || miningAI.world.getBlockState(miningAI.storagePos).getBlock() instanceof MinerStash))
            {
                miningAI.entity.getNavigator().tryMoveToXYZ(miningAI.storagePos.getX(), miningAI.storagePos.getY(), miningAI.storagePos.getZ(), 1);
                miningAI.searchStorage = true;
                return true;
            } else return false;
        }

        miningAI.world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1, (float) (0.9 + miningAI.random.nextFloat() * 0.1));
        miningAI.world.setBlockToAir(pos);
        return true;
    }
}