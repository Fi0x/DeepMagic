package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class SpellComponent extends ItemBase
{
    public SpellComponent(String name)
    {
        super(name);
        setCreativeTab(DeepMagicTab.SPELLS);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;

        if(compound.hasKey("target"))
        {
            if(compound.getByte("target") == 5) tooltip.add(TextFormatting.GREEN + "Target is 5" );
            else tooltip.add(TextFormatting.YELLOW + "Target Tag found, but not 5");
        } else tooltip.add(TextFormatting.RED + "No Target tag found" );
    }
}