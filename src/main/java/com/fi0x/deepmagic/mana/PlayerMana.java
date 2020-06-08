package com.fi0x.deepmagic.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PlayerMana
{
	private double mana = 0;
	private double maxMana = 100;
	private double manaRegenRate = 0;
	private double manaEfficiency = 0;
	
	public PlayerMana() {}
	
	public double getMana()
	{
		return mana;
	}
	public void setMana(double mana)
	{
		this.mana = mana;
	}
	public boolean addMana(double value)
	{
		if(mana+(value*Math.pow(1.1, manaRegenRate)) > maxMana && mana < maxMana)
		{
			mana = maxMana;
			return false;
		} else if(mana+(value*Math.pow(1.1, manaRegenRate)) <= maxMana)
		{
			mana += (value*Math.pow(1.1, manaRegenRate));
			return true;
		}
		return false;
	}
	public boolean removeMana(double value, EntityPlayer player)
	{
		if(mana-(value/Math.pow(1.1, manaEfficiency)) < 0)
		{
			player.sendMessage(new TextComponentString(TextFormatting.RED + "Mana needed: " + (value/Math.pow(1.1, manaEfficiency))));
			return false;
		}
		mana -= (value/Math.pow(1.1, manaEfficiency));
		player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Mana consumed: " + (value/Math.pow(1.1, manaEfficiency))));
		return true;
	}
	public double getMaxMana()
	{
		return maxMana;
	}
	public void setMaxMana(double maxMana)
	{
		this.maxMana = maxMana;
	}
	public double getManaPercentage()
	{
		if(mana <= maxMana) return (100/maxMana)*mana;
		return 100;
	}
	public void setManaRegenRate(double manaRegenRate)
	{
		this.manaRegenRate = manaRegenRate;
	}
	public double getManaRegenRate()
	{
		return manaRegenRate;
	}
	public void setManaEfficiency(double manaEfficiency)
	{
		this.manaEfficiency = manaEfficiency;
	}
	public double getManaEfficiency()
	{
		return manaEfficiency;
	}
	public void showMana(EntityPlayer player, World world)
	{
		if(!world.isRemote)
		{
			player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Mana: " + (int) mana + " / " + (int) maxMana));
		}
	}
	
	public void copyFrom(PlayerMana source)
	{
		mana = source.mana;
		maxMana = source.maxMana;
		manaRegenRate = source.manaRegenRate;
		manaEfficiency = source.manaEfficiency;
	}
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setDouble("mana", mana);
		compound.setDouble("maxMana", maxMana);
		compound.setDouble("manaRegenerationRate", manaRegenRate);
		compound.setDouble("manaEfficiency", manaEfficiency);
	}
	public void loadNBTData(NBTTagCompound compound)
	{
		mana = compound.getDouble("mana");
		maxMana = compound.getDouble("maxMana");
		manaRegenRate = compound.getDouble("manaRegenerationRate");
		manaEfficiency = compound.getDouble("manaEfficiency");
	}
}