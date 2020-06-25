package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.gui.GuiManaRenderOverlay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnPlayerMana implements IMessage
{
    private boolean messageValid;

    private double currentMana;
    private double maxMana;
    private int skillpoints;
    private double manaRegenRate;
    private double manaEfficiency;
    private int addedHP;
    private int hpRegeneration;
    private int spellTier;
    private int spellCastSkill;

    public PacketReturnPlayerMana()
    {
        this.messageValid = false;
    }
    public PacketReturnPlayerMana(double currentMana, double maxMana, int skillpoints, double manaRegenRate, double manaEfficiency, int addedHP, int hpRegeneration, int spellTier, int spellCastSkill)
    {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.skillpoints = skillpoints;
        this.manaRegenRate = manaRegenRate;
        this.manaEfficiency = manaEfficiency;
        this.addedHP = addedHP;
        this.hpRegeneration = hpRegeneration;
        this.spellTier = spellTier;
        this.spellCastSkill = spellCastSkill;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            currentMana = buf.readDouble();
            maxMana = buf.readDouble();
            skillpoints = buf.readInt();
            manaRegenRate = buf.readDouble();
            manaEfficiency = buf.readDouble();
            addedHP = buf.readInt();
            hpRegeneration = buf.readInt();
            spellTier = buf.readInt();
            spellCastSkill = buf.readInt();
        } catch(IndexOutOfBoundsException exception)
        {
            Main.getLogger().catching(exception);
            return;
        }
        messageValid = true;
    }
    @Override
    public void toBytes(ByteBuf buf)
    {
        if(!messageValid) return;
        buf.writeDouble(currentMana);
        buf.writeDouble(maxMana);
        buf.writeInt(skillpoints);
        buf.writeDouble(manaRegenRate);
        buf.writeDouble(manaEfficiency);
        buf.writeInt(addedHP);
        buf.writeInt(hpRegeneration);
        buf.writeInt(spellTier);
        buf.writeInt(spellCastSkill);
    }

    public static class Handler implements IMessageHandler<PacketReturnPlayerMana, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReturnPlayerMana message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketReturnPlayerMana message)
        {
            try
            {
                GuiManaRenderOverlay.instance.setValues(message.currentMana, message.maxMana);
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}