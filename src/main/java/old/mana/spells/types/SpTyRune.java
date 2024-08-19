package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityRune;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class SpTyRune implements ISpellType
{
    public static final String NAME = "type_rune";
    private int casts = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Rune";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += casts;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        casts = Integer.parseInt(attributes.get(0));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_FLOW_CONTROLLER));
        list.add(new ItemStack(ModItems.MAGIC_SIGIL));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{0, 0.5, 3, 0};
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        if(!applicableParts.isEmpty()) applicableParts.remove(0);

        if(world.getBlockState(castLocation) instanceof BlockAir) world.setBlockState(castLocation, ModBlocks.RUNE.getDefaultState());
        else if(world.getBlockState(castLocation.up()) instanceof BlockAir) world.setBlockState(castLocation.up(), ModBlocks.RUNE.getDefaultState());
        else return;

        ((TileEntityRune) Objects.requireNonNull(world.getTileEntity(castLocation))).setSpell(caster, applicableParts, remainingSections);
    }

    @Override
    public void setDuration(double value)
    {
        casts = (int) value;
    }
    @Override
    public double getDuration()
    {
        return casts;
    }
}
