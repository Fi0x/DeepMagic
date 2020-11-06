package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGeneratorInsanity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotManaGeneratorInsanity extends Slot
{
    public SlotManaGeneratorInsanity(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return TileEntityManaGeneratorInsanity.isItemFuel(stack);
    }
}
