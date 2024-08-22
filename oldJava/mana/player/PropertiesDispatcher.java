package com.fi0x.deepmagic.mana.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;

public class PropertiesDispatcher implements ICapabilitySerializable<NBTTagCompound>
{
    private final PlayerMana playerMana = new PlayerMana();
    
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing)
    {
        return capability == PlayerProperties.PLAYER_MANA;
    }
    @SuppressWarnings("unchecked")
	@Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing)
    {
        if (capability == PlayerProperties.PLAYER_MANA)
        {
            return (T) playerMana;
        }
        return null;
    }
    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        playerMana.saveNBTData(nbt);
        return nbt;
    }
    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        playerMana.loadNBTData(nbt);
    }
}