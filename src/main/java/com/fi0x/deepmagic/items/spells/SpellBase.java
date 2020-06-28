package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.entity.player.EntityPlayer;

public class SpellBase extends ItemBase implements IMagicItem
{
    protected int manaCost;
    protected int tier;
    protected double skillXP;

    public SpellBase(String name, int tier)
    {
        super(name);
        this.tier = tier;
        this.skillXP = 10 * tier;
        setMaxStackSize(1);
    }

    protected void addSkillXP(EntityPlayer player)
    {
        player.getCapability(PlayerProperties.PLAYER_MANA, null).addSkillXP(skillXP);
    }
}