package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.blocks.tileentity.BlockTileEntity;
import com.fi0x.deepmagic.blocks.tileentity.TileEntitySpellStone;
import com.fi0x.deepmagic.items.spells.Spell;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class SpellStone extends BlockTileEntity<TileEntitySpellStone>
{
    public SpellStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return false;
        ItemStack stack = playerIn.getHeldItem(hand);
        Item item = stack.getItem();
        TileEntitySpellStone tile = getTileEntity(worldIn, pos);

        if(item instanceof Spell) return chargeSpell(playerIn, stack, tile);

        return false;
    }
    @Override
    public Class<TileEntitySpellStone> getTileEntityClass()
    {
        return TileEntitySpellStone.class;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntitySpellStone();
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        if(rand.nextInt(100) + 1 > ConfigHandler.spellStoneParticles) return;

        TileEntitySpellStone tile = getTileEntity(worldIn, pos);

        int particles = tile.getPartCount();
        switch(Minecraft.getMinecraft().gameSettings.particleSetting)
        {
            case 0:
                particles *= 1;
                break;
            case 1:
                particles *= 2;
                break;
            default:
                System.out.println(Minecraft.getMinecraft().gameSettings.particleSetting);
                particles = 0;
        }

        for(int i = 0; i < particles; i++)
        {
            double x = pos.getX() + Math.random();
            double y = pos.getY() + 1;
            double z = pos.getZ() + Math.random();

            ParticleSpawner.spawnParticle(ParticleEnum.SPELL_STONE, x, y, z);
        }
    }
    private boolean chargeSpell(EntityPlayer playerIn, ItemStack stack, TileEntitySpellStone tile)
    {
        playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Spell Stone used"));
        NBTTagCompound compound;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        compound = stack.getTagCompound();
        assert compound != null;

        int sectionNumber = 0;
        while(compound.hasKey("section" + sectionNumber)) sectionNumber++;
        compound.setString("section" + sectionNumber, tile.getSpellParts());
        tile.resetParts();

        int manaBase = ConfigHandler.spellBaseManaCost;
        if(compound.hasKey("manaBase")) manaBase = compound.getInteger("manaBase");
        manaBase += tile.getManaAdder();
        compound.setInteger("manaBase", manaBase);
        tile.resetManaAdder();

        double manaMult = 1;
        if(compound.hasKey("manaMultiplier")) manaMult = compound.getDouble("manaMultiplier");
        manaMult += tile.getManaMultiplier();
        compound.setDouble("manaMultiplier", manaMult);
        tile.resetManaMultiplier();

        int manaCosts = (int) (manaBase * manaMult);

        compound.setInteger("manaCosts", manaCosts);
        double tier = Math.min(Math.log(Math.pow(manaCosts, 2.4)), 0.01 * Math.pow(manaCosts, 0.7));
        compound.setInteger("tier", (int) tier);
        compound.setDouble("skillXP", Math.pow(manaCosts, 0.3));
        return true;
    }
}