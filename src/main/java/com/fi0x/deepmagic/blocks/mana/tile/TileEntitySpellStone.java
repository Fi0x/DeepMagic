package com.fi0x.deepmagic.blocks.mana.tile;

import com.fi0x.deepmagic.items.spells.Spell;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import com.fi0x.deepmagic.mana.spells.SpellPartHandler;
import com.fi0x.deepmagic.mana.spells.SpellPartVerifier;
import com.fi0x.deepmagic.network.PacketGetSpellStone;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

public class TileEntitySpellStone extends TileEntity implements IInventory, ITickable
{
    private final SpellPartVerifier verifier = new SpellPartVerifier(this);

    private NonNullList<ItemStack> inventory = NonNullList.withSize(7, ItemStack.EMPTY);
    private String customName;

    private int totalTime;
    private int remainingTime;
    private int buttonHandling;

    private ArrayList<String> spellParts = new ArrayList<>();
    private ArrayList<String> partNames = new ArrayList<>();
    private String currentPartName = "";

    private int sync = 0;

    private int manaAdder;
    private double manaMultiplier;
    private double tier;

    @Override
    public void update()
    {
        boolean dirty = false;

        if(!world.isRemote)
        {
            if(remainingTime > 0)
            {
                remainingTime--;
                if(!(inventory.get(0).getItem() instanceof Spell) || !inventory.get(1).isEmpty())
                {
                    totalTime = 0;
                    remainingTime = 0;
                } else if(remainingTime == 0)
                {
                    ItemStack inputSpell = inventory.get(0).copy();
                    chargeSpell(inputSpell);
                    inventory.set(1, inputSpell);
                    inventory.set(0, ItemStack.EMPTY);
                    totalTime = 0;
                }
                dirty = true;
            } else if(buttonHandling != 0)
            {
                switch(buttonHandling)
                {
                    case 1:
                        totalTime = spellParts.size() * 100;
                        remainingTime = totalTime;
                        break;
                    case 2:
                        ISpellPart matchingPart = verifier.getPartFromItems();
                        if(matchingPart == null) break; //TODO: Find out why this is sometimes null when a recipe should be available

                        double[] castModifiers = matchingPart.getCastModifiers();
                        manaAdder += castModifiers[0];
                        manaMultiplier += castModifiers[1];
                        tier += castModifiers[2];
                        if(castModifiers[3] != 0) setSpellTier(castModifiers[3]);

                        spellParts.add(matchingPart.getName());
                        partNames.add(matchingPart.getDisplayName());
                        for(int i = 2; i <= 6; i++)
                        {
                            inventory.get(i).shrink(1);
                        }
                        break;
                    case 3:
                        clearPartLists();
                }
                buttonHandling = 0;
                dirty = true;
            }
        } else
        {
            sync--;
            if(sync < 0)
            {
                sync = 10;
                PacketHandler.INSTANCE.sendToServer(new PacketGetSpellStone(world.provider.getDimension(), pos));
                ISpellPart currentPart = verifier.getPartFromItems();
                if(currentPart != null) currentPartName = currentPart.getDisplayName();
                else currentPartName = "Unknown";
            }
        }

        if(dirty) markDirty();
    }

    private void chargeSpell(ItemStack stack)
    {
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;

        int sectionNumber = 0;
        while(compound.hasKey("section" + sectionNumber)) sectionNumber++;
        compound.setString("section" + sectionNumber, getSpellParts());
        spellParts.clear();
        partNames.clear();

        int manaBase = ConfigHandler.spellBaseManaCost;
        if(compound.hasKey("manaBase")) manaBase = compound.getInteger("manaBase");
        manaBase += manaAdder;
        compound.setInteger("manaBase", manaBase);
        manaAdder = 0;

        double manaMult = 1;
        if(compound.hasKey("manaMultiplier")) manaMult = compound.getDouble("manaMultiplier");
        manaMult += manaMultiplier;
        compound.setDouble("manaMultiplier", manaMult);
        manaMultiplier = 0;

        int manaCosts = (int) (manaBase * manaMult);
        compound.setInteger("manaCosts", manaCosts);

        double tier = Math.min(Math.log(Math.pow(manaCosts, 2.4)), 0.01 * Math.pow(manaCosts, 0.7));
        if(compound.hasKey("tier")) tier = compound.getInteger("tier");
        tier += this.tier;
        compound.setInteger("tier", (int) tier);
        this.tier = 0;

        compound.setDouble("skillXP", Math.pow(manaCosts, 0.3));
    }
    private void clearPartLists()
    {
        ArrayList<ItemStack> consumedItems = new ArrayList<>();
        ArrayList<ISpellPart> partsToRemove = SpellPartHandler.getSectionParts(getSpellParts());
        for(ISpellPart removePart : partsToRemove)
        {
            consumedItems.addAll(removePart.getRequiredItems());
        }

        spellParts.clear();
        partNames.clear();

        for(ItemStack stack : consumedItems)
        {
            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, stack);
            world.spawnEntity(entityItem);
        }
    }

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

        parts = new StringBuilder();
        if(!partNames.isEmpty())
        {
            parts.append(partNames.get(0));
            for(int i = 1; i < partNames.size(); i++)
            {
                parts.append("_:_").append(partNames.get(i));
            }
        }
        compound.setString("partNames", parts.toString());
        compound.setString("currentPartName", currentPartName);

        compound.setInteger("manaAdder", manaAdder);
        compound.setDouble("manaMultiplier", manaMultiplier);
        compound.setDouble("spellTier", tier);

        ItemStackHelper.saveAllItems(compound, inventory);
        if(hasCustomName()) compound.setString("customName", customName);

        compound.setInteger("totalTime", totalTime);
        compound.setInteger("remainingTime", remainingTime);
        compound.setInteger("buttonPress", buttonHandling);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        String parts = compound.getString("parts");
        spellParts.addAll(Arrays.asList(parts.split(":")));
        if(spellParts.size() == 1 && spellParts.get(0).equals("")) spellParts.clear();

        parts = compound.getString("partNames");
        partNames.addAll(Arrays.asList(parts.split("_:_")));
        if(partNames.size() == 1 && partNames.get(0).equals("")) partNames.clear();

        currentPartName = compound.getString("currentPartName");

        manaAdder = compound.getInteger("manaAdder");
        manaMultiplier = compound.getDouble("manaMultiplier");
        tier = compound.getDouble("spellTier");

        inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        if(compound.hasKey("customName")) setCustomName(compound.getString("customName"));

        totalTime = compound.getInteger("totalTime");
        remainingTime = compound.getInteger("remainingTime");
        buttonHandling = compound.getInteger("buttonPress");

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
    public ArrayList<String> getPartNames()
    {
        return partNames;
    }
    public String getPacketParts()
    {
        StringBuilder packetString = new StringBuilder();
        if(spellParts.size() > 0) packetString.append(spellParts.get(0));
        for(int i = 1; i < spellParts.size(); i++)
        {
            packetString.append("_:_").append(spellParts.get(i));
        }
        packetString.append("___");
        if(partNames.size() > 0) packetString.append(partNames.get(0));
        for(int i = 1; i < partNames.size(); i++)
        {
            packetString.append("_:_").append(partNames.get(i));
        }

        return packetString.toString();
    }
    public void setPartsFromPacket(String parts)
    {
        String[] partLists = parts.split("___");
        if(partLists.length < 2)
        {
            spellParts.clear();
            partNames.clear();
            return;
        }
        spellParts = new ArrayList<>(Arrays.asList(partLists[0].split("_:_")));
        partNames = new ArrayList<>(Arrays.asList(partLists[1].split("_:_")));
    }
    public String getCurrentPartName()
    {
        return currentPartName;
    }
    public void setSpellTier(double newTier)
    {
        if(newTier > tier) tier = newTier;
        else tier += newTier / tier;
        markDirty();
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
        switch(id)
        {
            case 0:
                return totalTime;
            case 1:
                return remainingTime;
            case 2:
                return buttonHandling;
        }
        return 0;
    }
    @Override
    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                totalTime = value;
                break;
            case 1:
                remainingTime = value;
                break;
            case 2:
                buttonHandling = value;
                break;
        }
    }
    @Override
    public int getFieldCount()
    {
        return 3;
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
