package com.fi0x.deepmagic.mana.spells;

import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.mana.spells.effects.defensive.*;
import com.fi0x.deepmagic.mana.spells.effects.offensive.*;
import com.fi0x.deepmagic.mana.spells.effects.util.*;
import com.fi0x.deepmagic.mana.spells.modifiers.*;
import com.fi0x.deepmagic.mana.spells.types.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpellPartVerifier
{
    private final TileEntitySpellStone te;
    private final ArrayList<ISpellPart> spellParts;

    public SpellPartVerifier(TileEntitySpellStone tileEntity)
    {
        te = tileEntity;
        spellParts = new ArrayList<>();
        initiateSpellPartList();
    }

    public ISpellPart getPartFromItems()
    {
        ArrayList<Item> inputs = new ArrayList<>();
        for(int i = 2; i <= 6; i++)
        {
            if(te.getStackInSlot(i) == ItemStack.EMPTY) continue;
            inputs.add(te.getStackInSlot(i).getItem());
        }

        for(ISpellPart part : spellParts)
        {
            ArrayList<ItemStack> required = part.getRequiredItems();
            if(required.size() == inputs.size())
            {
                if(required.size() > 0 && inputs.contains(required.get(0).getItem()))
                {
                    if(required.size() > 1 && inputs.contains(required.get(1).getItem()))
                    {
                        if(required.size() > 2 && inputs.contains(required.get(2).getItem()))
                        {
                            if(required.size() > 3 && inputs.contains(required.get(3).getItem()))
                            {
                                if(required.size() > 4 && inputs.contains(required.get(4).getItem()))
                                {
                                    return part;
                                } else if(required.size() == 4) return part;
                            } else if(required.size() == 3) return part;
                        } else if(required.size() == 2) return part;
                    } else if(required.size() == 1) return part;
                }
            }
        }
        return null;
    }

    private void initiateSpellPartList()
    {
        spellParts.add(new SpTyAreaOfEffect());
        spellParts.add(new SpTyBeam());
        spellParts.add(new SpTyIterate());
        spellParts.add(new SpTyProjectile());
        spellParts.add(new SpTyRune());
        spellParts.add(new SpTySelf());
        spellParts.add(new SpTyStream());
        spellParts.add(new SpTyTouch());

        spellParts.add(new SpMoAutoSmelt());
        spellParts.add(new SpMoDamage());
        spellParts.add(new SpMoEnvironmental());
        spellParts.add(new SpMoFortune());
        spellParts.add(new SpMoGravity());
        spellParts.add(new SpMoHealPower());
        spellParts.add(new SpMoLooting());
        spellParts.add(new SpMoPiercing());
        spellParts.add(new SpMoPower());
        spellParts.add(new SpMoRadius());
        spellParts.add(new SpMoRange());
        spellParts.add(new SpMoRicochet());
        spellParts.add(new SpMoSilkTouch());
        spellParts.add(new SpMoSplit());
        spellParts.add(new SpMoTickSpeed());

        spellParts.add(new SpEfDeflect());
        spellParts.add(new SpEfFeatherFall());
        spellParts.add(new SpEfHaste());
        spellParts.add(new SpEfHeal());
        spellParts.add(new SpEfManaShield());
        spellParts.add(new SpEfMiningSpeed());
        spellParts.add(new SpEfMirrorShield());
        spellParts.add(new SpEfRegeneration());
        spellParts.add(new SpEfRoot());
        spellParts.add(new SpEfSlowness());
        spellParts.add(new SpEfWall());
        spellParts.add(new SpEfWither());
        spellParts.add(new SpEfAccelerate());
        spellParts.add(new SpEfBlindness());
        spellParts.add(new SpEfDrown());
        spellParts.add(new SpEfExplosion());
        spellParts.add(new SpEfFireDamage());
        spellParts.add(new SpEfFrostDamage());
        spellParts.add(new SpEfIgnition());
        spellParts.add(new SpEfKnockback());
        spellParts.add(new SpEfMagicDamage());
        spellParts.add(new SpEfMidnight());
        spellParts.add(new SpEfPhysicalDamage());
        spellParts.add(new SpEfPoison());
        spellParts.add(new SpEfSink());
        spellParts.add(new SpEfStun());
        spellParts.add(new SpEfAge());
        spellParts.add(new SpEfBind());
        spellParts.add(new SpEfBlink());
        spellParts.add(new SpEfBuff());
        spellParts.add(new SpEfCharm());
        spellParts.add(new SpEfCookie());
        spellParts.add(new SpEfDayNight());
        spellParts.add(new SpEfDig());
        spellParts.add(new SpEfDimensionalTeleport());
        spellParts.add(new SpEfDry());
        spellParts.add(new SpEfExchange());
        spellParts.add(new SpEfFly());
        spellParts.add(new SpEfFreeze());
        spellParts.add(new SpEfGrowth());
        spellParts.add(new SpEfLevitate());
        spellParts.add(new SpEfLifeLink());
        spellParts.add(new SpEfLight());
        spellParts.add(new SpEfLightning());
        spellParts.add(new SpEfMagnet());
        spellParts.add(new SpEfManaLink());
        spellParts.add(new SpEfMarkLocation());
        spellParts.add(new SpEfPlace());
        spellParts.add(new SpEfRain());
        spellParts.add(new SpEfRepel());
        spellParts.add(new SpEfSmelt());
        spellParts.add(new SpEfStore());
        spellParts.add(new SpEfStorm());
        spellParts.add(new SpEfSummon());
        spellParts.add(new SpEfSunshine());
        spellParts.add(new SpEfSwimSpeed());
        spellParts.add(new SpEfTeleport());
        spellParts.add(new SpEfTotalRecall());
    }
}