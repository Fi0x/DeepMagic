package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpTyBeam implements ISpellType
{
    public static final String NAME = "type_beam";
    private double range = 8;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Beam";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += range;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        range = Double.parseDouble(attributes.get(0));
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
}
