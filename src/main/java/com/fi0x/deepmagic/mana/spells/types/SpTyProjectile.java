package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.entities.projectiles.EntitySpellProjectile;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpTyProjectile implements ISpellType
{
    public static final String NAME = "type_projectile";
    private double existingDuration = 5;
    private double range = 16;
    private double velocity = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Projectile";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += existingDuration + "_attr_";
        ret += range + "_attr_";
        ret += velocity;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        existingDuration = Double.parseDouble(attributes.get(0));
        range = Double.parseDouble(attributes.get(1));
        velocity = Double.parseDouble(attributes.get(2));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_FLOW_CONTROLLER));
        list.add(new ItemStack(Items.BOW));
        list.add(new ItemStack(Items.ARROW));

        return list;
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        if(caster != null)
        {
            EntitySpellProjectile projectile = new EntitySpellProjectile(world, caster);
            projectile.setSpell(caster, applicableParts, remainingSections);

            Vec3d vector = caster.getLookVec();
            projectile.motionX = vector.x * velocity;
            projectile.motionY = vector.y * velocity;
            projectile.motionZ = vector.z * velocity;

            projectile.posX += vector.x;
            projectile.posY += vector.y;
            projectile.posZ += vector.z;
            world.spawnEntity(projectile);
        } else
        {
            while(!applicableParts.isEmpty())
            {
                if(applicableParts.get(0) instanceof ISpellType)
                {
                    ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, castLocation, caster, world);
                    break;
                }
                applicableParts.remove(0);
            }
        }
    }

    @Override
    public void setDuration(double value)
    {
        existingDuration = value;
    }
    @Override
    public double getDuration()
    {
        return existingDuration;
    }
    @Override
    public void setRange(double value)
    {
        range = value;
    }
    @Override
    public double getRange()
    {
        return range;
    }
    @Override
    public void setVelocity(double value)
    {
        velocity = value;
    }
    @Override
    public double getVelocity()
    {
        return velocity;
    }
}
