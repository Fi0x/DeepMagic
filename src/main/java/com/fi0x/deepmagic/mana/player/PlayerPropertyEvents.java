package com.fi0x.deepmagic.mana.player;

import com.fi0x.deepmagic.network.PacketGetPlayerMana;
import com.fi0x.deepmagic.network.PacketGetSkill;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerPropertyEvents
{
    public static PlayerPropertyEvents instance = new PlayerPropertyEvents();
    private static int playerSync = 0;
    
    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            if (!event.getObject().hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                event.addCapability(new ResourceLocation(Reference.MOD_ID, "Mana"), new PropertiesDispatcher());
            }
        }
    }
    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            if (event.getOriginal().hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana oldStore = event.getOriginal().getCapability(PlayerProperties.PLAYER_MANA, null);
                PlayerMana newStore = PlayerProperties.getPlayerMana(event.getEntityPlayer());
                assert oldStore != null;
                newStore.copyFrom(oldStore);
                newStore.updatePlayerHP(event.getEntityPlayer());
                if(event.getEntityPlayer().world.isRemote)
                    PacketHandler.INSTANCE.sendToServer(new PacketGetSkill(event.getEntityPlayer().getName(), newStore.getMaxMana(), newStore.getSkillXP(), newStore.getSkillpoints(), newStore.getManaRegenRate(), newStore.getManaEfficiencyValue(), newStore.addedHP, newStore.hpRegeneration, newStore.getSpellTier()));
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        double amount = 0.1;
        if(event.player.dimension == ConfigHandler.dimensionIdInsanityID) amount = 0.05;
        else if(event.player.dimension == ConfigHandler.dimensionIdDepthID) amount = 0.3;
        else if(event.player.dimension == -1 || event.player.dimension == 1) amount = 0.2;

        PlayerMana playermana = event.player.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playermana != null;
        playermana.addMana(amount);

        if(event.side == Side.CLIENT)
        {
            playerSync++;
            playerSync %= 10;
            if(playerSync == 0)
            {
                PlayerMana playerMana = event.player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                PacketHandler.INSTANCE.sendToServer(new PacketGetPlayerMana(event.player.getName(), playerMana.getMana()));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerSwapDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event)
    {
        PlayerMana playerMana = event.player.getCapability(PlayerProperties.PLAYER_MANA, null);
        assert playerMana != null;
        playerMana.updatePlayerHP(event.player);
        if(event.player.world.isRemote)
            PacketHandler.INSTANCE.sendToServer(new PacketGetSkill(event.player.getName(), playerMana.getMaxMana(), playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiencyValue(), playerMana.addedHP, playerMana.hpRegeneration, playerMana.getSpellTier()));
    }
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            PlayerMana playerMana = event.getEntity().getCapability(PlayerProperties.PLAYER_MANA, null);
            assert playerMana != null;
            playerMana.updatePlayerHP((EntityPlayer) event.getEntity());
            if(event.getWorld().isRemote)
                PacketHandler.INSTANCE.sendToServer(new PacketGetSkill(event.getEntity().getName(), playerMana.getMaxMana(), playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiencyValue(), playerMana.addedHP, playerMana.hpRegeneration, playerMana.getSpellTier()));
        }
    }
    @SubscribeEvent
    public void onPlayerHeal(LivingHealEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            PlayerMana playerMana = event.getEntity().getCapability(PlayerProperties.PLAYER_MANA, null);
            assert playerMana != null;
            event.setAmount(event.getAmount() + playerMana.getHpRegenerationAmount());
        }
    }
}