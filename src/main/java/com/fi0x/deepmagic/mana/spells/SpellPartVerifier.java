package com.fi0x.deepmagic.mana.spells;

import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.defensive.SpEfDeflect;
import com.fi0x.deepmagic.mana.spells.effects.offensive.*;
import com.fi0x.deepmagic.mana.spells.effects.util.*;
import com.fi0x.deepmagic.mana.spells.modifiers.*;
import com.fi0x.deepmagic.mana.spells.types.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpellPartVerifier
{
    TileEntitySpellStone te;
    public SpellPartVerifier(TileEntitySpellStone tileEntity)
    {
        te = tileEntity;
    }

    public ISpellPart getPartFromItems()
    {
        ArrayList<Item> inputs = new ArrayList<>();
        for(int i = 2; i <= 6; i++)
        {
            if(te.getStackInSlot(i) == ItemStack.EMPTY) continue;
            inputs.add(te.getStackInSlot(i).getItem());
        }

        if(inputs.contains(ModItems.MAGIC_FLOW_CONTROLLER))
        {
            if(inputs.size() == 2)
            {
                if(inputs.contains(Items.SPLASH_POTION))
                {
                    te.addManaMultiplier(1);
                    te.addSpellTier(2);
                    return new SpTyAreaOfEffect();
                }
                if(inputs.contains(ModItems.MAGIC_SIGIL))
                {
                    te.addManaMultiplier(0.5);
                    te.addSpellTier(3);
                    return new SpTyRune();
                }
                if(inputs.contains(ModItems.MANA_INTERFACE))
                {
                    te.addManaAdder(50);
                    return new SpTySelf();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.STONE_BUTTON)))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(1);
                    return new SpTyTouch();
                }
            }
            if(inputs.size() == 3)
            {
                if(inputs.contains(Items.CLOCK) && inputs.contains(Items.COMPARATOR))
                {
                    te.addManaMultiplier(1);
                    te.addSpellTier(1);
                    return new SpTyIterate();
                }
                if(inputs.contains(Items.BOW) && inputs.contains(Items.ARROW))
                {
                    te.addManaMultiplier(1);
                    te.addSpellTier(1);
                    return new SpTyProjectile();
                }
            }
        }

        if(inputs.contains(ModItems.MAGIC_POWDER))
        {
            if(inputs.size() == 2)
            {
                if(inputs.contains(Items.LAVA_BUCKET))
                {
                    te.addManaAdder(100);
                    te.setSpellTier(2);
                    return new SpMoAutoSmelt();
                }
                if(inputs.contains(Items.IRON_SWORD))
                {
                    te.addManaAdder(100);
                    te.setSpellTier(2);
                    return new SpMoDamage();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.OBSIDIAN)))
                {
                    te.addManaAdder(20);
                    te.setSpellTier(2);
                    return new SpMoGravity();
                }
                if(inputs.contains(Items.GOLDEN_APPLE))
                {
                    te.addManaMultiplier(0.2);
                    te.setSpellTier(2);
                    return new SpMoHealPower();
                }
                if(inputs.contains(Items.DIAMOND_SWORD))
                {
                    te.addManaMultiplier(0.5);
                    te.setSpellTier(2);
                    return new SpMoPiercing();
                }
                if(inputs.contains(Items.COMPASS))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(2);
                    return new SpMoRadius();
                }
                if(inputs.contains(Items.ARROW))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(2);
                    return new SpMoRange();
                }
                if(inputs.contains(Items.SLIME_BALL))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(2);
                    return new SpMoRicochet();
                }
            }
            if(inputs.size() == 3)
            {
                if(inputs.contains(Items.CLOCK))
                {
                    if(inputs.contains(Items.REPEATER))
                    {
                        te.addManaMultiplier(0.2);
                        te.setSpellTier(2);
                        return new SpMoDuration();
                    }
                    if(inputs.contains(ModItems.MAGIC_SIGIL))
                    {
                        te.addManaAdder(100);
                        te.setSpellTier(4);
                        return new SpMoTickSpeed();
                    }
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.LAPIS_BLOCK)))
                {
                    if(inputs.contains(Items.EMERALD))
                    {
                        te.addManaMultiplier(0.2);
                        te.setSpellTier(2);
                        return new SpMoFortune();
                    }
                    if(inputs.contains(Items.DIAMOND))
                    {
                        te.addManaMultiplier(0.2);
                        te.setSpellTier(2);
                        return new SpMoLooting();
                    }
                }
            }
            if(inputs.size() == 4)
            {
                if(inputs.contains(Items.IRON_SHOVEL) && inputs.contains(Items.IRON_PICKAXE) && inputs.contains(Items.IRON_AXE))
                {
                    te.addManaMultiplier(0.5);
                    te.setSpellTier(4);
                    return new SpMoEnvironmental();
                }
            }
            if(inputs.size() == 5)
            {
                if(inputs.contains(Items.COAL) && inputs.contains(Items.IRON_INGOT) && inputs.contains(Items.GOLD_INGOT) && inputs.contains(Items.DIAMOND))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(2);
                    return new SpMoPower();
                }
            }
        }

        if(inputs.contains(ModItems.MAGIC_CONVERTER))
        {
            if(inputs.size() == 2)
            {
                if(inputs.contains(Items.SHIELD))
                {
                    te.addManaAdder(20);
                    return new SpEfDeflect();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.TNT)))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(2);
                    return new SpEfExplosion();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.MAGMA)))
                {
                    te.addManaAdder(30);
                    te.setSpellTier(2);
                    return new SpEfFireDamage();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.ICE)))
                {
                    te.addManaAdder(30);
                    te.setSpellTier(2);
                    return new SpEfFrostDamage();
                }
                if(inputs.contains(Items.FLINT_AND_STEEL))
                {
                    te.addManaAdder(30);
                    return new SpEfIgnition();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.PISTON)))
                {
                    te.addManaAdder(20);
                    return new SpEfKnockback();
                }
                if(inputs.contains(Items.ENDER_PEARL))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(2);
                    return new SpEfBlink();
                }
                if(inputs.contains(Items.COOKIE))
                {
                    te.addManaAdder(20);
                    te.setSpellTier(5);
                    return new SpEfCookie();
                }
                if(inputs.contains(Items.ENDER_EYE))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(3);
                    return new SpEfDimensionalTeleport();
                }
                if(inputs.contains(Items.BUCKET))
                {
                    te.addManaAdder(20);
                    return new SpEfDry();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.SNOW)))
                {
                    te.addManaAdder(30);
                    return new SpEfFreeze();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.GLOWSTONE)))
                {
                    te.addManaAdder(20);
                    return new SpEfLight();
                }
                if(inputs.contains(Items.WATER_BUCKET))
                {
                    te.addManaAdder(50);
                    te.setSpellTier(4);
                    return new SpEfRain();
                }
                if(inputs.contains(Item.getItemFromBlock(Blocks.CHEST)))
                {
                    te.addManaAdder(20);
                    return new SpEfStore();
                }
                if(inputs.contains(ModItems.TELEPORTATION_CRYSTAL))
                {
                    te.addManaAdder(50);
                    te.addSpellTier(2);
                    return new SpEfTeleport();
                }
            }
            if(inputs.size() == 3)
            {
                if(inputs.contains(Items.IRON_PICKAXE) && inputs.contains(Items.FLINT_AND_STEEL))
                {
                    te.addManaAdder(50);
                    return new SpEfSmelt();
                }
            }
            if(inputs.size() == 4)
            {
                if(inputs.contains(Items.WHEAT) && inputs.contains(Items.CARROT) && inputs.contains(Items.WHEAT_SEEDS))
                {
                    te.addManaAdder(40);
                    return new SpEfCharm();
                }
                if(inputs.contains(Items.IRON_SHOVEL) && inputs.contains(Items.IRON_PICKAXE) && inputs.contains(Items.IRON_AXE))
                {
                    te.addManaAdder(20);
                    return new SpEfDig();
                }
            }
        }
        return null;
    }
}