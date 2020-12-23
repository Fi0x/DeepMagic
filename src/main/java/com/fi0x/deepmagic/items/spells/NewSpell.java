package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import com.fi0x.deepmagic.items.spells.types.ISpellType;
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

        ArrayList<ArrayList<ISpellPart>> spellParts = new ArrayList<>();
        int section = 0;
        do
        {
            section++;
            if(compound.hasKey("section" + section))
            {
                spellParts.add(new SpellPartHandler().getSectionParts(compound.getCompoundTag("section" + section)));
            } else section = 0;
        } while(section > 0);

        castSpell(spellParts, playerIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private void castSpell(ArrayList<ArrayList<ISpellPart>> spellParts, EntityPlayer caster)
    {
        for(ArrayList<ISpellPart> section : spellParts)
        {
            for(int i = 0; i < section.size(); i++)
            {
                if(section.get(i) instanceof ISpellModifier && i > 0)
                {
                    section.set(i - 1, ((ISpellModifier) section.get(i)).modifyPart(section.get(i - 1)));
                }
            }

            if(section.get(0) instanceof ISpellType)
            {
                ((ISpellType) section.get(0)).execute(section, caster.getPosition(), caster);
            }
        }
    }
}
