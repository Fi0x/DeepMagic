package com.fi0x.deepmagic.blocks.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotManaInfuserOutput extends Slot
{
    public SlotManaInfuserOutput(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return false;
    }
}
