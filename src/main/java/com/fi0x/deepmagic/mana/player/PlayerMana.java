package com.fi0x.deepmagic.mana.player;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class PlayerMana
{
    //Mana System
    public double mana = 0;

    private double skillXP = 0;
    private int skillpoints = 1;
    private int skillLevel = 1;

    //Skills
    private double manaRegenRate = 0;
    private double manaEfficiency = 0;
    public int maxManaMultiplier = 0;
    public int addedHP = 0;
    public int hpRegeneration = 0;
    private int spellTier = 1;

    public PlayerMana()
    {
    }

    public double getMana()
    {
        return mana;
    }
    public void setMana(double mana)
    {
        this.mana = mana;
    }
    public void addMana(double value)
    {
        if(mana + (value * (manaRegenRate + 1)) > getMaxMana() && mana < getMaxMana())
        {
            mana = getMaxMana();
        } else if(mana + (value * (manaRegenRate + 1)) <= getMaxMana())
        {
            mana += value * (manaRegenRate + 1);
        }
    }
    public boolean removeMana(EntityPlayer player, double value, boolean verbose)
    {
        if(player.isCreative()) return true;

        if(mana - (value * getManaEfficiencyMultiplier()) < 0)
        {
            if(verbose) player.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
            return false;
        }
        mana -= (value * getManaEfficiencyMultiplier());
        return true;
    }
    public double getMaxMana()
    {
        int base = ConfigHandler.baseMana;
        double f1 = base * Math.pow(1.1, maxManaMultiplier);
        double f2 = 10 * base * Math.pow(maxManaMultiplier + 1, 0.9);
        return Math.min(f1, f2);
    }
    public double getManaPercentage()
    {
        if(mana <= getMaxMana()) return (100 / getMaxMana()) * mana;
        return 100;
    }

    public double getSkillXP()
    {
        return skillXP;
    }
    public void setSkillXP(double newValue)
    {
        skillXP = newValue;
    }
    public void addSkillXP(EntityPlayer player, double addAmount)
    {
        skillXP += addAmount;
        while(skillXP >= ConfigHandler.manaXPForLevelup + 5 * Math.pow(skillLevel, 1.5))
        {
            addSkillpoint(player);
            skillXP -= (ConfigHandler.manaXPForLevelup + 5 * Math.pow(skillLevel, 1.5));
            skillLevel++;
        }
    }
    public int getSkillpoints()
    {
        return skillpoints;
    }
    public void setSkillpoints(int newCount)
    {
        skillpoints = newCount;
    }
    public void addSkillpoint(EntityPlayer player)
    {
        if(ConfigHandler.showSkillpointAddedText) player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Skillpoint gained"));
        skillpoints++;
    }
    public void removeSkillpoint()
    {
        if(skillpoints > 0)
        {
            skillpoints--;
        }
    }
    public void setManaRegenRate(double manaRegenRate)
    {
        this.manaRegenRate = manaRegenRate;
    }
    public double getManaRegenRate()
    {
        return manaRegenRate;
    }
    public void setManaEfficiencyValue(double manaEfficiency)
    {
        this.manaEfficiency = manaEfficiency;
    }
    public double getManaEfficiencyValue()
    {
        return manaEfficiency;
    }
    public double getManaEfficiencyMultiplier()
    {
        double f1 = 1 / Math.pow(1.1, manaEfficiency);
        double f2 = 1 / (0.1 * (manaEfficiency + 13));
        double f12 = Math.max(f1, f2);
        double f3 = 0.1 + 1 / (manaEfficiency + 1);
        return Math.max(f12, f3);
    }
    public int getHpRegenerationAmount()
    {
        return (int) (Math.pow(hpRegeneration, 0.5));
    }
    public void setSpellTier(int newTier)
    {
        spellTier = newTier;
    }
    public void addSpellTier()
    {
        spellTier++;
    }
    public int getSpellTier()
    {
        return spellTier;
    }
    public int getAddedHP()
    {
        return (int) Math.pow(addedHP, 0.9);
    }
    public void updatePlayerHP(EntityPlayer player)
    {
        AttributeModifier modifier = new AttributeModifier(player.getUniqueID(), "deepMagicHpIncrease", getAddedHP(), 0);
        IAttributeInstance playerHP = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        if(playerHP.hasModifier(modifier)) player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(modifier);
        playerHP.applyModifier(modifier);
    }
    public void resetAllStats()
    {
        mana = 0;

        skillXP = 0;
        skillpoints = 1;
        skillLevel = 1;

        manaRegenRate = 0;
        manaEfficiency = 0;
        maxManaMultiplier = 0;
        addedHP = 0;
        hpRegeneration = 0;
        spellTier = 1;
    }

    public void copyFrom(PlayerMana source)
    {
        mana = source.mana;
        skillXP = source.skillXP;
        skillpoints = source.skillpoints;
        skillLevel = source.skillLevel;
        manaRegenRate = source.manaRegenRate;
        manaEfficiency = source.manaEfficiency;
        maxManaMultiplier = source.maxManaMultiplier;
        addedHP = source.addedHP;
        hpRegeneration = source.hpRegeneration;
        spellTier = source.spellTier;
    }
    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setDouble("mana", mana);
        compound.setDouble("skillXP", skillXP);
        compound.setInteger("skillpoints", skillpoints);
        compound.setInteger("skillLevel", skillLevel);
        compound.setDouble("manaRegenerationRate", manaRegenRate);
        compound.setDouble("manaEfficiency", manaEfficiency);
        compound.setInteger("maxManaMultiplier", maxManaMultiplier);
        compound.setInteger("addedHP", addedHP);
        compound.setInteger("hpRegeneration", hpRegeneration);
        compound.setInteger("spellTier", spellTier);
    }
    public void loadNBTData(NBTTagCompound compound)
    {
        mana = compound.getDouble("mana");
        skillXP = compound.getDouble("skillXP");
        skillpoints = compound.getInteger("skillpoints");
        skillLevel = compound.getInteger("skillLevel");
        manaRegenRate = compound.getDouble("manaRegenerationRate");
        manaEfficiency = compound.getDouble("manaEfficiency");
        maxManaMultiplier = compound.getInteger("maxManaMultiplier");
        addedHP = compound.getInteger("addedHP");
        hpRegeneration = compound.getInteger("hpRegeneration");
        spellTier = compound.getInteger("spellTier");
    }
}