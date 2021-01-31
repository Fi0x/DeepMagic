package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.spells.effects.defensive.*;
import com.fi0x.deepmagic.items.spells.effects.offensive.*;
import com.fi0x.deepmagic.items.spells.effects.util.*;
import com.fi0x.deepmagic.items.spells.modifiers.*;
import com.fi0x.deepmagic.items.spells.types.*;

import java.util.ArrayList;
import java.util.Arrays;

public class SpellPartHandler
{
    public static ArrayList<ISpellPart> getSectionParts(String section)
    {
        String[] partNames = section.split(":");

        ArrayList<ISpellPart> parts = new ArrayList<>();
        for(String p : partNames)
        {
            if(p.isEmpty()) continue;
            ArrayList<String> attributes = new ArrayList<>(Arrays.asList(p.split("_attr_")));
            ISpellPart nextPart = getSpellPart(attributes.get(0));
            if(nextPart == null) continue;
            attributes.remove(0);
            if(attributes.size() > 0) nextPart.setAttributesFromString(attributes);
            parts.add(nextPart);
        }

        return parts;
    }

    private static ISpellPart getSpellPart(String name)
    {
        switch(name)
        {
            case SpTyAreaOfEffect.NAME:
                return new SpTyAreaOfEffect();
            case SpTyBeam.NAME:
                return new SpTyBeam();
            case SpTyIterate.NAME:
                return new SpTyIterate();
            case SpTyProjectile.NAME:
                return new SpTyProjectile();
            case SpTyRune.NAME:
                return new SpTyRune();
            case SpTySelf.NAME:
                return new SpTySelf();
            case SpTyStream.NAME:
                return new SpTyStream();
            case SpTyTouch.NAME:
                return new SpTyTouch();
            case SpMoAutoSmelt.NAME:
                return new SpMoAutoSmelt();
            case SpMoDamage.NAME:
                return new SpMoDamage();
            case SpMoDuration.NAME:
                return new SpMoDuration();
            case SpMoEnvironmental.NAME:
                return new SpMoEnvironmental();
            case SpMoFortune.NAME:
                return new SpMoFortune();
            case SpMoGravity.NAME:
                return new SpMoGravity();
            case SpMoHealPower.NAME:
                return new SpMoHealPower();
            case SpMoLooting.NAME:
                return new SpMoLooting();
            case SpMoPower.NAME:
                return new SpMoPower();
            case SpMoPiercing.NAME:
                return new SpMoPiercing();
            case SpMoRadius.NAME:
                return new SpMoRadius();
            case SpMoRange.NAME:
                return new SpMoRange();
            case SpMoRicochet.NAME:
                return new SpMoRicochet();
            case SpMoSilkTouch.NAME:
                return new SpMoSilkTouch();
            case SpMoSplit.NAME:
                return new SpMoSplit();
            case SpMoTickSpeed.NAME:
                return new SpMoTickSpeed();
            case SpEfDeflect.NAME:
                return new SpEfDeflect();
            case SpEfFeatherFall.NAME:
                return new SpEfFeatherFall();
            case SpEfHaste.NAME:
                return new SpEfHaste();
            case SpEfHeal.NAME:
                return new SpEfHeal();
            case SpEfManaShield.NAME:
                return new SpEfManaShield();
            case SpEfMiningSpeed.NAME:
                return new SpEfMiningSpeed();
            case SpEfMirrorShield.NAME:
                return new SpEfMirrorShield();
            case SpEfRegeneration.NAME:
                return new SpEfRegeneration();
            case SpEfRoot.NAME:
                return new SpEfRoot();
            case SpEfSlowness.NAME:
                return new SpEfSlowness();
            case SpEfWall.NAME:
                return new SpEfWall();
            case SpEfWither.NAME:
                return new SpEfWither();
            case SpEfBlindness.NAME:
                return new SpEfBlindness();
            case SpEfDrown.NAME:
                return new SpEfDrown();
            case SpEfExplosion.NAME:
                return new SpEfExplosion();
            case SpEfFireDamage.NAME:
                return new SpEfFireDamage();
            case SpEfFrostDamage.NAME:
                return new SpEfFrostDamage();
            case SpEfIgnition.NAME:
                return new SpEfIgnition();
            case SpEfKnockback.NAME:
                return new SpEfKnockback();
            case SpEfMagicDamage.NAME:
                return new SpEfMagicDamage();
            case SpEfMidnight.NAME:
                return new SpEfMidnight();
            case SpEfPhysicalDamage.NAME:
                return new SpEfPhysicalDamage();
            case SpEfPoison.NAME:
                return new SpEfPoison();
            case SpEfSink.NAME:
                return new SpEfSink();
            case SpEfStun.NAME:
                return new SpEfStun();
            case SpEfAccelerate.NAME:
                return new SpEfAccelerate();
            case SpEfAge.NAME:
                return new SpEfAge();
            case SpEfBind.NAME:
                return new SpEfBind();
            case SpEfBlink.NAME:
                return new SpEfBlink();
            case SpEfBuff.NAME:
                return new SpEfBuff();
            case SpEfCharm.NAME:
                return new SpEfCharm();
            case SpEfDayNight.NAME:
                return new SpEfDayNight();
            case SpEfDig.NAME:
                return new SpEfDig();
            case SpEfDimensionalTeleport.NAME:
                return new SpEfDimensionalTeleport();
            case SpEfDry.NAME:
                return new SpEfDry();
            case SpEfExchange.NAME:
                return new SpEfExchange();
            case SpEfFly.NAME:
                return new SpEfFly();
            case SpEfFreeze.NAME:
                return new SpEfFreeze();
            case SpEfGrowth.NAME:
                return new SpEfGrowth();
            case SpEfLevitate.NAME:
                return new SpEfLevitate();
            case SpEfLifeLink.NAME:
                return new SpEfLifeLink();
            case SpEfLight.NAME:
                return new SpEfLight();
            case SpEfLightning.NAME:
                return new SpEfLightning();
            case SpEfMagnet.NAME:
                return new SpEfMagnet();
            case SpEfManaLink.NAME:
                return new SpEfManaLink();
            case SpEfMarkLocation.NAME:
                return new SpEfMarkLocation();
            case SpEfPlace.NAME:
                return new SpEfPlace();
            case SpEfRain.NAME:
                return new SpEfRain();
            case SpEfRepel.NAME:
                return new SpEfRepel();
            case SpEfSmelt.NAME:
                return new SpEfSmelt();
            case SpEfStore.NAME:
                return new SpEfStore();
            case SpEfStorm.NAME:
                return new SpEfStorm();
            case SpEfSummon.NAME:
                return new SpEfSummon();
            case SpEfSunshine.NAME:
                return new SpEfSunshine();
            case SpEfSwimSpeed.NAME:
                return new SpEfSwimSpeed();
            case SpEfTeleport.NAME:
                return new SpEfTeleport();
            case SpEfTotalRecall.NAME:
                return new SpEfTotalRecall();
            default:
                return null;
        }
    }
}
