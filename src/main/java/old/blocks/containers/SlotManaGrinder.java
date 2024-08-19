package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.mana.tile.TileEntityManaGrinder;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotManaGrinder extends Slot
{
    public SlotManaGrinder(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return TileEntityManaGrinder.isItemGrindable(stack);
    }
}
