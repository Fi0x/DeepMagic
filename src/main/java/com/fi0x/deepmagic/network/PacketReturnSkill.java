package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.gui.GuiManaRenderOverlay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnSkill implements IMessage
{
    private boolean messageValid;

    private int maxManaMultiplier;
    private double skillXP;
    private int skillpoints;
    private double manaRegenRate;
    private double manaEfficiency;
    private int addedHP;
    private int hpRegeneration;
    private int spellTier;
    private int spellCastSkill;

    public PacketReturnSkill()
    {
        this.messageValid = false;
    }
    public PacketReturnSkill(int maxManaMultiplier, double skillXP, int skillpoints, double manaRegenRate, double manaEfficiency, int addedHP, int hpRegeneration, int spellTier)
    {
        this.maxManaMultiplier = maxManaMultiplier;
        this.skillXP = skillXP;
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
            maxManaMultiplier = buf.readInt();
            skillXP = buf.readDouble();
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
        buf.writeInt(maxManaMultiplier);
        buf.writeDouble(skillXP);
        buf.writeInt(skillpoints);
        buf.writeDouble(manaRegenRate);
        buf.writeDouble(manaEfficiency);
        buf.writeInt(addedHP);
        buf.writeInt(hpRegeneration);
        buf.writeInt(spellTier);
        buf.writeInt(spellCastSkill);
    }

    public static class Handler implements IMessageHandler<PacketReturnSkill, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReturnSkill message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketReturnSkill message)
        {
            try
            {
                GuiManaRenderOverlay.instance.setMaxManaMultiplier(message.maxManaMultiplier);
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}