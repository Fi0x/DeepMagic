package com.fi0x.deepmagic.mana.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PlayerMana
{
	//Mana System
	private double mana = 0;
	private double maxMana = 100;

	//Skills
	private double manaRegenRate = 0;
	private double manaEfficiency = 0;
	private int maxManaMultiplier = 0;
	private int addedHP = 0;
	private int hpRegeneration = 0;
	
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
		if(mana+(value*Math.pow(1.1, manaRegenRate)) > maxMana*Math.pow(1.1, maxManaMultiplier) && mana < maxMana*Math.pow(1.1, maxManaMultiplier))
		{
			mana = maxMana*Math.pow(1.1, maxManaMultiplier);
			return false;
		} else if(mana+(value*Math.pow(1.1, manaRegenRate)) <= maxMana*Math.pow(1.1, maxManaMultiplier))
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
		return maxMana*Math.pow(1.1, maxManaMultiplier);
	}
	public void setMaxMana(double maxMana)
	{
		this.maxMana = maxMana;
	}
	public double getManaPercentage()
	{
		if(mana <= maxMana) return (100/maxMana*Math.pow(1.1, maxManaMultiplier))*mana;
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
			player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Mana: " + (int) mana + " / " + (int) maxMana*Math.pow(1.1, maxManaMultiplier)));
		}
	}
	
	public void copyFrom(PlayerMana source)
	{
		mana = source.mana;
		maxMana = source.maxMana;
		manaRegenRate = source.manaRegenRate;
		manaEfficiency = source.manaEfficiency;
		maxManaMultiplier = source.maxManaMultiplier;
		addedHP = source.addedHP;
		hpRegeneration = source.hpRegeneration;
	}
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setDouble("mana", mana);
		compound.setDouble("maxMana", maxMana);
		compound.setDouble("manaRegenerationRate", manaRegenRate);
		compound.setDouble("manaEfficiency", manaEfficiency);
		compound.setInteger("maxManaMultiplier", maxManaMultiplier);
		compound.setInteger("addedHP", addedHP);
		compound.setInteger("hpRegeneration", hpRegeneration);
	}
	public void loadNBTData(NBTTagCompound compound)
	{
		mana = compound.getDouble("mana");
		maxMana = compound.getDouble("maxMana");
		manaRegenRate = compound.getDouble("manaRegenerationRate");
		manaEfficiency = compound.getDouble("manaEfficiency");
		maxManaMultiplier = compound.getInteger("maxManaMultiplier");
		addedHP = compound.getInteger("addedHP");
		hpRegeneration = compound.getInteger("hpRegeneration");
	}
}