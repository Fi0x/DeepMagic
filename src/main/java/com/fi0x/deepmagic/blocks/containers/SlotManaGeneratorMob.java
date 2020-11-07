package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGeneratorMob;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotManaGeneratorMob extends Slot//TODO: Adjust class
{
    public SlotManaGeneratorMob(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return TileEntityManaGeneratorMob.isItemFuel(stack);
    }
}
