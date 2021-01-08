package com.fi0x.deepmagic.particlesystem;

import com.fi0x.deepmagic.particlesystem.particles.ParticleDwarfSearchMine;
import com.fi0x.deepmagic.particlesystem.particles.ParticleMagicLight;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;

public enum ParticleEnum
{
    MAGIC_LIGHT("magic_light", ConfigHandler.firstParticleID, true, ParticleMagicLight.class),
    DWARF_SEARCH_MINE("dwarf_search_mine", ConfigHandler.firstParticleID + 1, false, ParticleDwarfSearchMine.class);

    private final Class particleClass;
    private final String textureName;
    private final int particleID;
    private final boolean shouldIgnoreRange;

    private ParticleEnum(String textureName, int particleIDIn, boolean shouldIgnoreRangeIn, Class clazz)
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