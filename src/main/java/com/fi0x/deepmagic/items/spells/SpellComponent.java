package com.fi0x.deepmagic.items.spells;

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

        if(compound.hasKey("effect"))
        {
            tooltip.add(TextFormatting.GREEN + "Effect Component");
            switch(compound.getByte("effect"))
            {
                case 0:
                    tooltip.add(TextFormatting.GREEN + "Damage");
                    break;
                case 1:
                    tooltip.add(TextFormatting.GREEN + "Environmental Damage");
                    break;
                case 2:
                    tooltip.add(TextFormatting.GREEN + "Explosion");
                    break;
                case 3:
                    tooltip.add(TextFormatting.GREEN + "Heal");
                    break;
                case 4:
                    tooltip.add(TextFormatting.GREEN + "Time");
                    break;
                case 5:
                    tooltip.add(TextFormatting.GREEN + "Weather");
                    break;
                default:
                    tooltip.add(TextFormatting.RED + "Unknown Effect" );
                    break;
            }
        } else if(compound.hasKey("modifier"))
        {
            tooltip.add(TextFormatting.WHITE + "Modifier Component");
            switch(compound.getByte("modifier"))
            {
                case 0:
                    tooltip.add(TextFormatting.WHITE + "Range");
                    break;
                case 1:
                    tooltip.add(TextFormatting.WHITE + "Radius");
                    break;
                default:
                    tooltip.add(TextFormatting.RED + "Unknown Modifier" );
                    break;
            }

        } else if(compound.hasKey("target"))
        {
            tooltip.add(TextFormatting.YELLOW + "Target Component");
            switch(compound.getByte("target"))
            {
                case 0:
                    tooltip.add(TextFormatting.YELLOW + "Self" );
                    break;
                case 1:
                    tooltip.add(TextFormatting.YELLOW + "Current Position" );
                    break;
                case 2:
                    tooltip.add(TextFormatting.YELLOW + "Focused Entity" );
                    break;
                case 3:
                    tooltip.add(TextFormatting.YELLOW + "Focused Block" );
                    break;
                default:
                    tooltip.add(TextFormatting.RED + "Unknown Target" );
                    break;
            }
        } else
        {
            tooltip.add(TextFormatting.RED + "Unknown Component" );
        }
    }
}