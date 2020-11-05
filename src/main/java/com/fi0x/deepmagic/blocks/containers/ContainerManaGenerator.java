package com.fi0x.deepmagic.blocks.containers;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerManaGenerator extends Container
{
    private final TileEntityManaGenerator te;
    private int burnTime, currentBurnTime, storedMana;

    public ContainerManaGenerator(InventoryPlayer player, TileEntityManaGenerator tileEntity)
    {
        te = tileEntity;

        addSlotToContainer(new SlotManaGenerator(te, 0, 26, 31));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for(int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void addListener(@Nonnull IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, te);
    }
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (IContainerListener listener : listeners)
        {
            if (burnTime != te.getField(0)) listener.sendWindowProperty(this, 0, te.getField(0));
            if (currentBurnTime != te.getField(1)) listener.sendWindowProperty(this, 1, te.getField(1));
            if (storedMana != te.getField(2)) listener.sendWindowProperty(this, 2, te.getField(2));
        }

        burnTime = te.getField(0);
        currentBurnTime = te.getField(1);
        storedMana = te.getField(2);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        te.setField(id, data);
    }
    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return te.isUsableByPlayer(playerIn);
    }
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index)
    {
        //TODO: implement this method if problems occur (Harry Talks - Furnace pt4)
        return super.transferStackInSlot(playerIn, index);
    }
}