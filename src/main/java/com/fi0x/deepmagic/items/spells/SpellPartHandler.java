package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.effects.defensive.*;
import com.fi0x.deepmagic.items.spells.effects.offensive.*;
import com.fi0x.deepmagic.items.spells.effects.util.*;
import com.fi0x.deepmagic.items.spells.modifiers.*;
import com.fi0x.deepmagic.items.spells.trigger.*;
import com.fi0x.deepmagic.items.spells.types.*;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class SpellPartHandler
{
    public ArrayList<ISpellPart> getSectionParts(NBTTagCompound section)
    {
        ArrayList<ISpellPart> parts = new ArrayList<>();

        if(section.hasKey("type"))
        {
            String[] types = section.getString("type").split(":");
            for(String t : types)
            {
                parts.add(getSpellType(t));
            }
        }
        if(section.hasKey("trigger"))
        {
            String[] triggers = section.getString("trigger").split(":");
            for(String t : triggers)
            {
                parts.add(getSpellTrigger(t));
            }
        }
        if(section.hasKey("modifier"))
        {
            String[] modifiers = section.getString("modifier").split(":");
            for(String m : modifiers)
            {
                parts.add(getSpellModifier(m));
            }
        }
        if(section.hasKey("effect"))
        {
            String[] effects = section.getString("effect").split(":");
            for(String e : effects)
            {
                parts.add(getSpellEffect(e));
            }
        }

        return parts;
    }

    private ISpellType getSpellType(String name)
    {
        switch(name)
        {
            case "type_aoe":
                return new SpTyAreaOfEffect();
            case "type_beam":
                return new SpTyBeam();
            case "type_continuous":
                return new SpTyContinuous();
            case "type_iterate":
                return new SpTyIterate();
            case "type_projectile":
                return new SpTyProjectile();
            case "type_rune":
                return new SpTyRune();
            case "type_self":
                return new SpTySelf();
            case "type_stream":
                return new SpTyStream();
            case "type_touch":
                return new SpTyTouch();
            default:
                return null;
        }
    }
    private ISpellTrigger getSpellTrigger(String name)
    {
        switch(name)
        {
            case "trigger_damage":
                return new SpTrDamage();
            case "trigger_death":
                return new SpTrDeath();
            case "trigger_fall":
                return new SpTrFall();
            case "trigger_heal":
                return new SpTrHeal();
            default:
                return null;
        }
    }
    private ISpellModifier getSpellModifier(String name)
    {
        switch(name)
        {
            case "modifier_damage":
                return new SpMoDamage();
            case "modifier_duration":
                return new SpMoDuration();
            case "modifier_gravity":
                return new SpMoGravity();
            case "modifier_healpower":
                return new SpMoHealPower();
            case "modifier_miningpower":
                return new SpMoMiningPower();
            case "modifier_piercing":
                return new SpMoPiercing();
            case "modifier_radius":
                return new SpMoRadius();
            case "modifier_range":
                return new SpMoRange();
            case "modifier_ricochet":
                return new SpMoRicochet();
            case "modifier_split":
                return new SpMoSplit();
            case "modifier_tickspeed":
                return new SpMoTickSpeed();
            default:
                return null;
        }
    }
    private ISpellEffect getSpellEffect(String name)
    {
        switch(name)
        {
            case "effect_deflect":
                return new SpEfDeflect();
            case "effect_featherfall":
                return new SpEfFeatherFall();
            case "effect_haste":
                return new SpEfHaste();
            case "effect_heal":
                return new SpEfHeal();
            case "effect_mana_shield":
                return new SpEfManaShield();
            case "effect_miningspeed":
                return new SpEfMiningSpeed();
            case "effect_mirrorshield":
                return new SpEfMirrorShield();
            case "effect_regeneration":
                return new SpEfRegeneration();
            case "effect_root":
                return new SpEfRoot();
            case "effect_slowness":
                return new SpEfSlowness();
            case "effect_wall":
                return new SpEfWall();
            case "effect_wither":
                return new SpEfWither();
            case "effect_blindness":
                return new SpEfBlindness();
            case "effect_drown":
                return new SpEfDrown();
            case "effect_explosion":
                return new SpEfExplosion();
            case "effect_firedamage":
                return new SpEfFireDamage();
            case "effect_frostdamage":
                return new SpEfFrostDamage();
            case "effect_ignition":
                return new SpEfIgnition();
            case "effect_knockback":
                return new SpEfKnockback();
            case "effect_magicdamage":
                return new SpEfMagicDamage();
            case "effect_midnight":
                return new SpEfMidnight();
            case "effect_physicaldamage":
                return new SpEfPhysicalDamage();
            case "effect_poison":
                return new SpEfPoison();
            case "effect_sink":
                return new SpEfSink();
            case "effect_stun":
                return new SpEfStun();
            case "effect_accelerate":
                return new SpEfAccelerate();
            case "effect_age":
                return new SpEfAge();
            case "effect_bind":
                return new SpEfBind();
            case "effect_blink":
                return new SpEfBlink();
            case "effect_buff":
                return new SpEfBuff();
            case "effect_charm":
                return new SpEfCharm();
            case "effect_daynight":
                return new SpEfDayNight();
            case "effect_dig":
                return new SpEfDig();
            case "effect_dimensionalteleport":
                return new SpEfDimensionalTeleport();
            case "effect_dry":
                return new SpEfDry();
            case "effect_exchange":
                return new SpEfExchange();
            case "effect_fly":
                return new SpEfFly();
            case "effect_freeze":
                return new SpEfFreeze();
            case "effect_growth":
                return new SpEfGrowth();
            case "effect_levitate":
                return new SpEfLevitate();
            case "effect_lifelink":
                return new SpEfLifeLink();
            case "effect_light":
                return new SpEfLight();
            case "effect_lightning":
                return new SpEfLightning();
            case "effect_magnet":
                return new SpEfMagnet();
            case "effect_manalink":
                return new SpEfManaLink();
            case "effect_marklocation":
                return new SpEfMarkLocation();
            case "effect_place":
                return new SpEfPlace();
            case "effect_rain":
                return new SpEfRain();
            case "effect_repel":
                return new SpEfRepel();
            case "effect_smelt":
                return new SpEfSmelt();
            case "effect_store":
                return new SpEfStore();
            case "effect_storm":
                return new SpEfStorm();
            case "effect_summon":
                return new SpEfSummon();
            case "effect_sunshine":
                return new SpEfSunshine();
            case "effect_swimspeed":
                return new SpEfSwimSpeed();
            case "effect_teleport":
                return new SpEfTeleport();
            case "effect_totalrecall":
                return new SpEfTotalRecall();
            default:
                return null;
        }
    }
}
