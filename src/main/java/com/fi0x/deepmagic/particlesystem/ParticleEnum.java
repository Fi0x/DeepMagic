package com.fi0x.deepmagic.particlesystem;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public enum ParticleEnum
{
    MAGIC_LIGHT("magic_light", ConfigHandler.firstParticleID, false);

    private final String particleName;
    private final int particleID;
    private final boolean shouldIgnoreRange;
    private final int argumentCount;
    private static final Map<Integer, ParticleEnum> PARTICLES = Maps.<Integer, ParticleEnum>newHashMap();
    private static final Map<String, ParticleEnum> BY_NAME = Maps.<String, ParticleEnum>newHashMap();

    private ParticleEnum(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn, int argumentCountIn)
    {
        this.particleName = particleNameIn;
        this.particleID = particleIDIn;
        this.shouldIgnoreRange = shouldIgnoreRangeIn;
        this.argumentCount = argumentCountIn;
    }


    private ParticleEnum(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn)
    {
        this(particleNameIn, particleIDIn, shouldIgnoreRangeIn, 0);
    }

    public static Set<String> getParticleNames()
    {
        return BY_NAME.keySet();
    }

    public String getParticleName()
    {
        return this.particleName;
    }

    public int getParticleID()
    {
        return this.particleID;
    }

    public int getArgumentCount()
    {
        return this.argumentCount;
    }

    public boolean getShouldIgnoreRange()
    {
        return this.shouldIgnoreRange;
    }

    /**
     * Gets the relative EnumParticleTypes by id.
     */
    @Nullable
    public static ParticleEnum getParticleFromId(int particleId)
    {
        return PARTICLES.get(Integer.valueOf(particleId));
    }

    @Nullable
    public static ParticleEnum getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }

    static
    {
        for(ParticleEnum enumparticletypes : values())
        {
            PARTICLES.put(Integer.valueOf(enumparticletypes.getParticleID()), enumparticletypes);
            BY_NAME.put(enumparticletypes.getParticleName(), enumparticletypes);
        }
    }

}