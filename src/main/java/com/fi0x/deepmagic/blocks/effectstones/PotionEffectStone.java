package com.fi0x.deepmagic.blocks.effectstones;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PotionEffectStone extends BlockBase
{
    private final Potion blockEffect;
    private final int duration;
    private final int amplifier;
    private final boolean playerOnly;

    public PotionEffectStone(String name, Material material, Potion effect, int sDuration, int powerLevel, boolean playerOnly)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
        blockEffect = effect;
        duration = sDuration * 20;
        amplifier = powerLevel-1;
        this.playerOnly = playerOnly;
    }
    public PotionEffectStone(String name, Material material, Potion effect, int sDuration, int powerLevel)
    {
        this(name, material, effect, sDuration, powerLevel, false);
    }

    @Override
    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entity)
    {
        if(!playerOnly || (playerOnly && entity instanceof EntityPlayer))
        {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(blockEffect, duration, amplifier, false, false));
        }
    }
}