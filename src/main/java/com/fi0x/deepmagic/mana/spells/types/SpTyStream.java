package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

/*
 * Constant stream of the effect
 * Like the beam, but not as focused
 */
public class SpTyStream implements ISpellType
{
    public static final String NAME = "type_stream";
    private double range = 4;
    private double radius = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Stream";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += range + "_attr_";
        ret += radius;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        range = Integer.parseInt(attributes.get(0));
        radius = Integer.parseInt(attributes.get(1));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_FLOW_CONTROLLER));
        //TODO: Recipe missing

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{0, 0.3, 0, 3};
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        if(!applicableParts.isEmpty()) applicableParts.remove(0);
        //TODO: Execute spell
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
    public void setRadius(double value)
    {
        radius = value;
    }
    @Override
    public double getRadius()
    {
        return radius;
    }
}
