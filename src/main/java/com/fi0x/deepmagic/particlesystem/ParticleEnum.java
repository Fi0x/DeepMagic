package com.fi0x.deepmagic.particlesystem;

import com.fi0x.deepmagic.particlesystem.particles.*;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;

public enum ParticleEnum
{
    MAGIC_LIGHT("magic_light", ConfigHandler.firstParticleID, true, ParticleMagicLight.class),
    DWARF_SEARCH_MINE("dwarf_search_mine", ConfigHandler.firstParticleID + 1, false, ParticleDwarfSearchMine.class),
    MANA_BLOCK("mana_block", ConfigHandler.firstParticleID + 2, false, ParticleManaBlock.class),
    SPELL_PROJECTILE("spell_projectile", ConfigHandler.firstParticleID + 3, false, ParticleSpellProjectile.class),
    DEEP_CRYSTAL_ORE("deep_crystal_ore", ConfigHandler.firstParticleID + 4, false, ParticleDeepCrystalOre.class),
    INSANITY_PLANT("insanity_plant", ConfigHandler.firstParticleID + 5, false, ParticlePlant.class),
    DEPTH_PLANT("depth_plant", ConfigHandler.firstParticleID + 6, false, ParticlePlant.class),
    DEMON_STONE("demon_stone", ConfigHandler.firstParticleID + 7, false, ParticleDemonStone.class),
    SPELL_STONE("spell_stone", ConfigHandler.firstParticleID + 8, false, ParticleSpellStone.class),
    RITUAL_MISSING("ritual_missing", ConfigHandler.firstParticleID + 9, false, ParticleRitualMissing.class),
    SPELL_STREAM("spell_stream", ConfigHandler.firstParticleID + 10, false, ParticleSpellStream.class);

    private final Class particleClass;
    private final String textureName;
    private final int particleID;
    private final boolean shouldIgnoreRange;

    ParticleEnum(String textureName, int particleIDIn, boolean shouldIgnoreRangeIn, Class clazz)
    {
        particleClass = clazz;
        this.textureName = textureName;
        this.particleID = particleIDIn;
        this.shouldIgnoreRange = shouldIgnoreRangeIn;
    }

    public Class getParticleClass()
    {
        return particleClass;
    }
    public String getTextureName()
    {
        return this.textureName;
    }
    public int getParticleID()
    {
        return this.particleID;
    }
    public boolean getShouldIgnoreRange()
    {
        return this.shouldIgnoreRange;
    }
}