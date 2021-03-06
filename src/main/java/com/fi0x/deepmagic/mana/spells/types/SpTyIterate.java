package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpTyIterate implements ISpellType
{
    public static final String NAME = "type_iterate";
    private int iterations = 4;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Iterate";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += iterations;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        iterations = Integer.parseInt(attributes.get(0));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_FLOW_CONTROLLER));
        list.add(new ItemStack(Items.CLOCK));
        list.add(new ItemStack(Items.COMPARATOR));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{0, 1, 1, 0};
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        if(!applicableParts.isEmpty()) applicableParts.remove(0);

        while(!applicableParts.isEmpty() && !(applicableParts.get(0) instanceof ISpellType))
        {
            applicableParts.remove(0);
        }
        if(!applicableParts.isEmpty())
        {
            ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, castLocation, caster, world);
        }
    }

    @Override
    public void setDuration(double value)
    {
        iterations = (int) value;
    }
    @Override
    public double getDuration()
    {
        return iterations;
    }
}
