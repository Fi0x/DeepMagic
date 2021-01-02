package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.blocks.SpellStone;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.items.ItemBase;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IMagicItem;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Spell extends ItemBase implements IMagicItem
{
    public Spell(String name)
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

        BlockPos blockPos = getFocusedBlock(playerIn, 5);
        if(blockPos != null && worldIn.getBlockState(blockPos).getBlock() instanceof SpellStone) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        if(compound.hasKey("tier") && playerMana.getSpellTier() < compound.getInteger("tier"))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You are not skilled enough for this spell"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
        if(compound.hasKey("manaCosts") && !(playerMana.removeMana(compound.getDouble("manaCosts"))))
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You don't have enough mana"));
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        castSpell(compound, playerIn);
        if(compound.hasKey("skillXP")) playerMana.addSkillXP(playerIn, compound.getDouble("skillXP"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        //TODO: Add correct information about spell effect
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.GREEN + "Spell Effects:");
            if(compound.hasKey("damage")) tooltip.add(TextFormatting.GREEN + "Damage: " + compound.getInteger("damage"));
            if(compound.hasKey("environmentalDamage") && compound.getBoolean("environmentalDamage")) tooltip.add(TextFormatting.GREEN + "Does environmental damage");
            if(compound.hasKey("explosion") && compound.getBoolean("explosion")) tooltip.add(TextFormatting.GREEN + "Creates an explosion");
            if(compound.hasKey("heal")) tooltip.add(TextFormatting.GREEN + "Healing amount: " + compound.getInteger("heal"));
            if(compound.hasKey("time")) tooltip.add(TextFormatting.GREEN + "Add " + compound.getInteger("time") + " timeunits");
            if(compound.hasKey("weather") && compound.getBoolean("weather")) tooltip.add(TextFormatting.GREEN + "Can toggle downfall");
            tooltip.add(TextFormatting.WHITE + "Modifications:");
            if(compound.hasKey("range")) tooltip.add(TextFormatting.WHITE + "Range: " + compound.getInteger("range"));
            if(compound.hasKey("radius")) tooltip.add(TextFormatting.WHITE + "Radius: " + compound.getInteger("radius"));
            tooltip.add(TextFormatting.YELLOW + "Targets:");
            if(compound.hasKey("targetSelf") && compound.getBoolean("targetSelf")) tooltip.add(TextFormatting.YELLOW + "Yourself");
            if(compound.hasKey("targetSelfPos") && compound.getBoolean("targetSelfPos")) tooltip.add(TextFormatting.YELLOW + "Your Position");
            if(compound.hasKey("targetFocus") && compound.getBoolean("targetFocus")) tooltip.add(TextFormatting.YELLOW + "Targeted Entity");
            if(compound.hasKey("targetFocusPos") && compound.getBoolean("targetFocusPos")) tooltip.add(TextFormatting.YELLOW + "Targeted Position");
        } else tooltip.add(TextFormatting.YELLOW + "Press Shift for more Information");
        if(GuiScreen.isCtrlKeyDown())
        {
            if(compound.hasKey("manaCosts")) tooltip.add(TextFormatting.BLUE + "Consumes " + compound.getInteger("manaCosts") + " Mana");
            else tooltip.add(TextFormatting.BLUE + "Consumes " + ConfigHandler.spellBaseManaCost + " Mana");
            if(compound.hasKey("tier")) tooltip.add(TextFormatting.BLUE + "Requires Skill Tier " + compound.getInteger("tier"));
        } else tooltip.add(TextFormatting.BLUE + "Press Ctrl for Mana Information");
    }

    private void castSpell(NBTTagCompound compound, EntityPlayer caster)
    {
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

        for(ArrayList<ISpellPart> s : spellParts)
        {
            for(int i = 0; i < s.size(); i++)
            {
                if(s.get(i) instanceof ISpellModifier && i > 0)
                {
                    s.set(i - 1, ((ISpellModifier) s.get(i)).modifyPart(s.get(i - 1)));
                    s.remove(i);
                    i--;
                }
            }
        }

        new CastHelper().findAndCastNextSpellType(spellParts, caster.getPosition(), caster);
    }

    private BlockPos getFocusedBlock(EntityPlayer player, int range)
    {
        Vec3d vec3d = player.getPositionEyes(1F);
        Vec3d vec3d1 = player.getLook(1F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * range, vec3d1.y * range, vec3d1.z * range);
        RayTraceResult result = player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        assert result != null;
        if(result.typeOfHit == RayTraceResult.Type.MISS) return null;
        return result.getBlockPos();
    }
}
