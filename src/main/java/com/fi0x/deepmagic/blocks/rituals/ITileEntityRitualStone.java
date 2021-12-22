package com.fi0x.deepmagic.blocks.rituals;

public interface ITileEntityRitualStone
{
    String getPacketData();
    void setDataFromPacket(String parts);
    void syncedUpdate();
}