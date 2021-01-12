package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.commands.Teleport;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.IHasModel;
import com.fi0x.deepmagic.util.IMagicItem;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class TeleportationCrystal extends Item implements IHasModel, IMagicItem
{

    public TeleportationCrystal(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(DeepMagicTab.ITEMS);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
    @Override
    public void addInformation(@Nonnull ItemStack stack, World worldIn, List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.WHITE + "Can teleport the player to the insanity dimension and back");
        if(GuiScreen.isCtrlKeyDown())
        {
            tooltip.add(TextFormatting.BLUE + "Consumes " + ConfigHandler.teleportationCrystalManaCost + " Mana");
        } else tooltip.add(TextFormatting.BLUE + "Press Ctrl for Mana Information");
    }
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote)
        {
            PlayerMana playerMana = playerIn.getCapability(PlayerProperties.PLAYER_MANA, null);
            assert playerMana != null;

            int manaCosts;
            if(playerIn.isSneaking()) manaCosts = ConfigHandler.teleportationCrystalManaCostDepth;
            else manaCosts = ConfigHandler.teleportationCrystalManaCost;

            if(playerMana.removeMana(manaCosts))
            {
                playerMana.addSkillXP(playerIn, ConfigHandler.teleportationCrystalSkillXP);
                int x = (int) playerIn.posX;
                int z = (int) playerIn.posZ;
                if(playerIn.dimension == 0)
                {
                    if(playerIn.isSneaking())
                    {
                        x *= 0.05;
                        z *= 0.05;
                        playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You went below hell"));
                        return teleportEntityTo(playerIn, ConfigHandler.dimensionIdDepthID, x, z, stack);
                    } else
                    {
                        x *= 10;
                        z *= 10;
                        playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You entered a strange dimension..."));
                        return teleportEntityTo(playerIn, ConfigHandler.dimensionIdInsanityID, x, z, stack);
                    }
                } else if(playerIn.dimension == -1)
                {
                    x *= 8;
                    z *= 8;
                    return teleportEntityTo(playerIn, 0, x, z, stack);
                } else if(playerIn.dimension == ConfigHandler.dimensionIdInsanityID)
                {
                    x *= 0.1;
                    z *= 0.1;
                    playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "Your mind clears as you return to the overworld"));
                    return teleportEntityTo(playerIn, 0, x, z, stack);
                } else if(playerIn.dimension == ConfigHandler.dimensionIdDepthID && playerIn.isSneaking())
                {
                    x *= 20;
                    z *= 20;
                    playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You feel the pressure dropping as you return to the surface"));
                    return teleportEntityTo(playerIn, 0, x, z, stack);
                }

                return teleportEntityTo(playerIn, 0, x, z, stack);
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    private ActionResult<ItemStack> teleportEntityTo(EntityPlayer playerIn, int dimensionID, int x, int z, ItemStack stack)
    {
        MinecraftServer server = playerIn.getEntityWorld().getMinecraftServer();
        assert server != null;

        double y;
        if(dimensionID == ConfigHandler.dimensionIdDepthID) y = findGoodY(server.getWorld(dimensionID), x, (int) playerIn.posY, z);
        else y = findSurface(server.getWorld(dimensionID), x, z);

        server = server.getWorld(dimensionID).getMinecraftServer();
        assert server != null;

        Teleport teleport = new Teleport(playerIn.getEntityWorld().getMinecraftServer().getWorld(dimensionID), x + 0.5, y, z + 0.5);

        server.getPlayerList().transferPlayerToDimension((EntityPlayerMP) playerIn, dimensionID, teleport);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private double findSurface(World world, int x, int z)
    {
        int y = world.getHeight();
        boolean foundGround = false;

        while(!foundGround && y-- >= 0)
        {
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            if(block != Blocks.AIR)
            {
                y += 2;
                foundGround = true;
            }
        }
        return y;
    }

    private double findGoodY(World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) != null && world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) != null)
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
        }
        return y;
    }
}