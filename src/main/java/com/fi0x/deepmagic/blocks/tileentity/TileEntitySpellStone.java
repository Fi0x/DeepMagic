package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.items.spells.modifiers.SpMoRange;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntitySpellStone extends TileEntity implements ITickable
{
    private ArrayList<String> spellParts = new ArrayList<>();
    private ArrayList<String> consumedItems = new ArrayList<>();

    private int manaAdder;
    private double manaMultiplier;

    @Override
    public void update()
    {
        AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ()));
        List<EntityItem> itemEntities = world.getEntitiesWithinAABB(EntityItem.class, aabb);

        for(EntityItem i : itemEntities)
        {
            String name = i.getItem().getItem().getClass().getName();
            for(int j = 0; j < i.getItem().getCount(); j++)
            {
                consumedItems.add(name);
            }
            world.removeEntity(i);
            markDirty();
        }

        if(consumedItems.contains(ItemArrow.class.getName()))
        {
            spellParts.add(SpMoRange.NAME);
            consumedItems.remove(ItemArrow.class.getName());
            markDirty();
        }
        //TODO: Use Items to update stats of spell-stone
    }
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        StringBuilder parts = new StringBuilder();
        parts.append(spellParts.get(0));
        for(int i = 1; i < spellParts.size(); i++)
        {
            parts.append(":").append(spellParts.get(i));
        }
        compound.setString("parts", parts.toString());

        StringBuilder items = new StringBuilder();
        items.append(consumedItems.get(0));
        for(int i = 1; i < consumedItems.size(); i++)
        {
            items.append("_:_").append(consumedItems.get(i));
        }
        compound.setString("items", items.toString());

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        String parts = compound.getString("parts");
        spellParts.addAll(Arrays.asList(parts.split(":")));

        String items = compound.getString("items");
        consumedItems.addAll(Arrays.asList(items.split("_:_")));

        super.readFromNBT(compound);
    }

    public ArrayList<String> getSpellParts()
    {
        return spellParts;
    }
    public void resetParts()
    {
        spellParts.clear();
        if(!consumedItems.isEmpty())
        {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, false);
            consumedItems.clear();
        }
        manaMultiplier = 1;
        markDirty();
    }
    public int getManaAdder()
    {
        return manaAdder;
    }
    public void resetManaAdder()
    {
        manaAdder = 0;
    }
    public double getManaMultiplier()
    {
        return manaMultiplier;
    }
    public void resetManaMultiplier()
    {
        manaMultiplier = 0;
    }
}
