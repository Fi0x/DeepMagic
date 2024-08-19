package com.fi0x.deepmagic.entities.ai.helper;

import com.fi0x.deepmagic.blocks.MinerStash;
import com.fi0x.deepmagic.entities.ai.EntityAIMining;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;

public class AIHelperDig
{
    public static boolean digAtBlockPos(EntityAIMining miningAI, boolean ore)
    {
        BlockPos pos = ore ? miningAI.additionalBlocks.get(0) : miningAI.miningBlocks.get(0);
        BlockPos floor = new BlockPos(pos.getX(), miningAI.shaftFloor, pos.getZ());

        if(!AIHelperSearchBlocks.hasWalls(miningAI.world, floor, miningAI.direction) && !AIHelperSearchBlocks.isBridge(miningAI.world, floor)) return false;
        if(!ore && miningAI.world.isAirBlock(floor) && miningAI.world.getBlockState(floor.down()).getCollisionBoundingBox(miningAI.world, floor.down()) == null)
        {
            if(miningAI.entity.getPosition().getY() > floor.getY()) AIHelperBuild.placeInventoryBlock(miningAI.world, floor, miningAI.entity.itemHandler);
        }
        Block block = miningAI.world.getBlockState(pos).getBlock();

        if(miningAI.world.getBlockState(pos).getCollisionBoundingBox(miningAI.world, pos) == null) return true;

        ArrayList<BlockPos> surroundingWater = AIHelperSearchBlocks.getAdjacentWater(miningAI.world, pos);
        for(BlockPos waterBlock : surroundingWater)
        {
            AIHelperBuild.placeInventoryBlock(miningAI.world, waterBlock, miningAI.entity.itemHandler);
        }

        miningAI.additionalBlocks.addAll(AIHelperSearchBlocks.getOreCluster(miningAI.world, miningAI.miningBlocks, pos));

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