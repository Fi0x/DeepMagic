package com.fi0x.deepmagic.items.spells.effects;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpellEffect extends ISpellPart
{
    void applyEffect(EntityLivingBase caster, BlockPos targetPos, World world);
    void applyEffect(EntityLivingBase caster, EntityLivingBase targetEntity);
}
