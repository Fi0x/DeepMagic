package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.util.IMagicItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class NewSpell extends ItemBase implements IMagicItem
{
    public NewSpell(String name)
    {
        super(name);
        setCreativeTab(DeepMagicTab.SPELLS);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        NBTTagCompound compound;
        if(!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
        compound = itemStack.getTagCompound();
        assert compound != null;

        ArrayList<ISpellPart> spellParts = new ArrayList<>();
        int section = 0;
        do
        {
            section++;
            if(compound.hasKey("section" + section))
            {
                spellParts.addAll(getSectionParts(compound.getCompoundTag("section" + section)));
            } else section = 0;
        } while(section > 0);

        castSpell(spellParts);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private ArrayList<ISpellPart> getSectionParts(NBTTagCompound section)
    {
        ArrayList<ISpellPart> parts = new ArrayList<>();

        if(section.hasKey("type"))
        {
            String[] types = section.getString("type").split(":");
            for(String t : types)
            {
                parts.add(SpellPartHandler.getSpellType(t));
            }
        }
        if(section.hasKey("trigger"))
        {
            String[] triggers = section.getString("trigger").split(":");
            for(String t : triggers)
            {
                parts.add(SpellPartHandler.getSpellTrigger(t));
            }
        }
        if(section.hasKey("modifier"))
        {
            String[] modifiers = section.getString("modifier").split(":");
            for(String m : modifiers)
            {
                parts.add(SpellPartHandler.getSpellModifier(m));
            }
        }
        if(section.hasKey("effect"))
        {
            String[] effects = section.getString("effect").split(":");
            for(String e : effects)
            {
                parts.add(SpellPartHandler.getSpellEffect(e));
            }
        }

        return parts;
    }

    private void castSpell(ArrayList<ISpellPart> spellParts)
    {
    }
}
