package com.fi0x.deepmagic.items.spells.effects;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

public interface ISpellEffect extends ISpellPart
{
    void applyEffect(BlockPos targetPos);
    void applyEffect(EntityLivingBase targetEntity);
}
