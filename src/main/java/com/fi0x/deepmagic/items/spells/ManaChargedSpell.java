package com.fi0x.deepmagic.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ManaChargedSpell extends SpellBase
{
    /*Spell that has no effect by itself
     * Can be crafted together with a spell that has an effect
     * Uses the effect of the crafted spell
     * Can't be powered with playermana
     * EITHER needst to be charged with playermana in advance
     * OR can be linked with machine mana*/
    private final SpellType spellType;

    public ManaChargedSpell(String name, SpellType type, int tier)
    {
        super(name, tier);
        spellType = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public enum SpellType {
        HEAL,
        TIME_DAY,
        TIME_NIGHT,
        WEATHER,
        MOB_ANNIHILATION,
        RANGED_MOB_ANNIHILATION,
        MOB_PUSHER
    }
}