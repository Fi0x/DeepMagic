package com.fi0x.deepmagic.blocks.mana.tile;

import com.fi0x.deepmagic.blocks.mana.SpellStone;
import com.fi0x.deepmagic.items.spells.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

public class TileEntitySpellStone extends TileEntity implements IInventory
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(7, ItemStack.EMPTY);
    private String customName;

    private final ArrayList<String> spellParts = new ArrayList<>();

    private int manaAdder;
    private double manaMultiplier;
    private double tier;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        StringBuilder parts = new StringBuilder();
        if(!spellParts.isEmpty())
        {
            parts.append(spellParts.get(0));
            for(int i = 1; i < spellParts.size(); i++)
            {
                parts.append(":").append(spellParts.get(i));
            }
        }
        compound.setString("parts", parts.toString());

        compound.setInteger("manaAdder", manaAdder);
        compound.setDouble("manaMultiplier", manaMultiplier);
        compound.setDouble("spellTier", tier);

        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        String parts = compound.getString("parts");
        spellParts.addAll(Arrays.asList(parts.split(":")));

        manaAdder = compound.getInteger("manaAdder");
        manaMultiplier = compound.getDouble("manaMultiplier");
        tier = compound.getDouble("spellTier");

        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));

        super.readFromNBT(compound);
    }

    public int getPartCount()
    {
        return spellParts.size();
    }
    public String getSpellParts()
    {
        StringBuilder parts = new StringBuilder();
        if(!spellParts.isEmpty())
        {
            parts.append(spellParts.get(0));
            for(int i = 1; i < spellParts.size(); i++)
            {
                parts.append(":").append(spellParts.get(i));
            }
        }
        return parts.toString();
    }
    public void resetParts()
    {
        spellParts.clear();
        markDirty();
    }
    public int getManaAdder()
    {
        return manaAdder;
    }
    public void resetManaAdder()
    {
        manaAdder = 0;
        markDirty();
    }
    public double getManaMultiplier()
    {
        return manaMultiplier;
    }
    public void resetManaMultiplier()
    {
        manaMultiplier = 0;
        markDirty();
    }
    public double getSpellTier()
    {
        return tier;
    }
    public void resetSpellTier()
    {
        tier = 0;
        markDirty();
    }

    public ItemStack chargeSpell()
    {
        if(inventory.get(1).isEmpty() && inventory.get(0).getItem() instanceof Spell)
        {
            return ((SpellStone) blockType).chargeSpell(inventory.get(0), this);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.size();
    }
    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : inventory)
        {
            if(!stack.isEmpty()) return false;
        }
        return true;
    }
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return inventory.get(index);
    }
    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(inventory, index, count);
    }
    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(inventory, index);
    }
    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
    {
        inventory.set(index, stack);

        if(stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
    }
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player)
    {
        return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64;
    }
    @Override
    public void openInventory(@Nonnull EntityPlayer player)
    {
    }
    @Override
    public void closeInventory(@Nonnull EntityPlayer player)
    {
    }
    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack)
    {
        return index != 1;
    }
    @Override
    public int getField(int id)
    {
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {

    }
    @Override
    public int getFieldCount()
    {
        return 0;
    }
    @Override
    public void clear()
    {
        inventory.clear();
    }
    @Nonnull
    @Override
    public String getName()
    {
        return hasCustomName() ? customName : "container.spell_stone";
    }
    @Override
    public boolean hasCustomName()
    {
        return customName != null && !customName.isEmpty();
    }
    @Nonnull
    @Override
    public ITextComponent getDisplayName()
    {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }
}
