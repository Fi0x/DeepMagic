package com.fi0x.deepmagic.entities.ai.helper;

import com.fi0x.deepmagic.entities.ai.EntityAIMining;
import com.fi0x.deepmagic.entities.mobs.EntityDwarf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class AIHelperStorage
{
    public static void searchAndGoToStorage(EntityAIMining miningAI)
    {
        if(miningAI.storagePos == null) miningAI.storagePos = AIHelperSearchBlocks.findStorage(miningAI.world, miningAI.entity.homePos, miningAI.entity.getPosition());
        if(miningAI.storagePos != null)
        {
            miningAI.entity.getNavigator().tryMoveToXYZ(miningAI.storagePos.getX(), miningAI.storagePos.getY(), miningAI.storagePos.getZ(), 1);
            if(miningAI.entity.getNavigator().noPath()) miningAI.searchStorage = false;
        }
    }

    public static void inventoryToStorage(EntityAIMining miningAI, EntityDwarf entity)
    {
        for(int i = 0; i < entity.itemHandler.getSlots(); i++)
        {
            if(entity.itemHandler.getStackInSlot(i).isEmpty()) return;
        }
        if(miningAI.storagePos == null || entity.getDistanceSq(miningAI.storagePos) > 64) return;
        miningAI.searchStorage = false;

        TileEntity te = null;
        try
        {
            te = miningAI.world.getTileEntity(miningAI.storagePos);
        } catch(Exception ignored)
        {
        }
        if(te == null)
        {
            miningAI.storagePos = null;
            return;
        }

        if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
        {
            IItemHandler h = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

            for(int i = 0; i < entity.itemHandler.getSlots(); i++)
            {
                if(ItemHandlerHelper.insertItemStacked(h, entity.itemHandler.getStackInSlot(i), false).isEmpty()) entity.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                else
                {
                    miningAI.storagePos = AIHelperSearchBlocks.findStorage(miningAI.world, miningAI.storagePos, entity.getPosition(), entity.homePos);
                    if(miningAI.storagePos != null) miningAI.searchStorage = true;
                    break;
                }
            }
        }
    }
}
