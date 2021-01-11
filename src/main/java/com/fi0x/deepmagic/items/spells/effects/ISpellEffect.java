package com.fi0x.deepmagic.items.spells.effects;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface ISpellEffect extends ISpellPart
{
    void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world);
    void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity);
}
