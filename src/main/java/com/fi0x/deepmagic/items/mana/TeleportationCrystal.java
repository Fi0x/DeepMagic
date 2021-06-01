package com.fi0x.deepmagic.items.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.depth.DepthDirt;
import com.fi0x.deepmagic.blocks.depth.DepthStone;
import com.fi0x.deepmagic.commands.Teleport;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
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
            if(playerIn.isSneaking() && playerIn.dimension != ConfigHandler.dimensionIdDepthID) manaCosts = ConfigHandler.teleportationCrystalManaCostDepth;
            else manaCosts = ConfigHandler.teleportationCrystalManaCost;

            if(playerMana.removeMana(playerIn, manaCosts, true))
            {
                playerMana.addSkillXP(playerIn, ConfigHandler.teleportationCrystalSkillXP);
                int x = (int) playerIn.posX;
                int z = (int) playerIn.posZ;
                if(playerIn.isSneaking() && ConfigHandler.allowDepthTeleport)
                {
                    if(playerIn.dimension == 0)
                    {
                        x *= 0.05;
                        z *= 0.05;
                        playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You went below hell"));
                        return teleportEntityTo(playerIn, ConfigHandler.dimensionIdDepthID, x, z, stack);
                    } else if(playerIn.dimension == ConfigHandler.dimensionIdInsanityID)
                    {
                        x *= 0.005;
                        z *= 0.005;
                        playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You went below hell"));
                        return teleportEntityTo(playerIn, ConfigHandler.dimensionIdDepthID, x, z, stack);
                    } else if(playerIn.dimension == -1)
                    {
                        x *= 0.4;
                        z *= 0.4;
                        playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You went below hell"));
                        return teleportEntityTo(playerIn, ConfigHandler.dimensionIdDepthID, x, z, stack);
                    } else
                    {
                        playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You went below hell"));
                        return teleportEntityTo(playerIn, ConfigHandler.dimensionIdDepthID, x, z, stack);
                    }
                } else if(playerIn.dimension == 0)
                {
                    if(!ConfigHandler.allowInsanityTeleport) return new ActionResult<>(EnumActionResult.SUCCESS, stack);

                    x *= 10;
                    z *= 10;
                    playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "You entered a strange dimension..."));
                    return teleportEntityTo(playerIn, ConfigHandler.dimensionIdInsanityID, x, z, stack);

                } else if(playerIn.dimension == -1)
                {
                    x *= 8;
                    z *= 8;
                } else if(playerIn.dimension == ConfigHandler.dimensionIdInsanityID)
                {
                    x *= 0.1;
                    z *= 0.1;
                    playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "Your mind clears as you return to the overworld"));
                } else if(playerIn.dimension == ConfigHandler.dimensionIdDepthID)
                {
                    x *= 20;
                    z *= 20;
                    playerIn.sendMessage(new TextComponentString(TextFormatting.BOLD + "The pressure around you drops as you return to the overworld"));
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
        if(dimensionID == ConfigHandler.dimensionIdDepthID)
        {
            y = findGoodY(server.getWorld(dimensionID), x, (int) playerIn.posY, z);
            playerIn.setSpawnDimension(ConfigHandler.dimensionIdDepthID);
            playerIn.setSpawnPoint(new BlockPos(x, y, z), true);
        } else y = findSurface(server.getWorld(dimensionID), x, z);

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

    private double findGoodY(World world, int x, double y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        Block block = world.getBlockState(pos).getBlock();
        Block top = world.getBlockState(pos.up()).getBlock();
        if((block instanceof DepthStone || block instanceof DepthDirt || world.isAirBlock(pos)) && (top instanceof DepthStone || top instanceof DepthDirt || world.isAirBlock(pos.up())))
        {
            world.setBlockToAir(pos);
            world.setBlockToAir(pos.up());
        } else
        {
            y++;
            if(y > 230)
            {
                pos = new BlockPos(x, y, z);
                world.setBlockToAir(pos);
                world.setBlockToAir(pos.up());
            } else
            {
                y = findGoodY(world, x, y, z);
                pos = new BlockPos(x, y, z);
            }
        }
        if(world.getBlockState(pos.down()).getCollisionBoundingBox(world, pos.down()) == null) world.setBlockState(pos.down(), ModBlocks.DEPTH_STONE.getDefaultState());
        return y;
    }
}