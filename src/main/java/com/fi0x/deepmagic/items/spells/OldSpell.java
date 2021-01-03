package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

@Deprecated
public class OldSpell extends ItemBase implements IMagicItem
{
    public OldSpell(String name)
    {
        super(name);
        setCreativeTab(DeepMagicTab.SPELLS);
        setMaxStackSize(1);
    }

    private static void editMobsInArea(World world, EntityPlayer player, BlockPos pos, int radius, int dmg, int heal, boolean explode, boolean envDmg)
    {
        AxisAlignedBB area = new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius);
        List<EntityCreature> entities = world.getEntitiesWithinAABB(EntityCreature.class, area);

        while(!entities.isEmpty())
        {
            if(!entities.get(0).equals(player)) entities.get(0).attackEntityFrom(DamageSource.MAGIC, dmg);
            entities.get(0).heal(heal);
            BlockPos explosionPos = entities.get(0).getPosition();
            if(explode) world.createExplosion(player, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), 5, envDmg);
            entities.remove(0);
        }
    }
}
