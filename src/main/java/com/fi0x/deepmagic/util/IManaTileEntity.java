package com.fi0x.deepmagic.util;

public interface IManaTileEntity
{
    double getSpaceForMana();

    /**
     * @param amount
     * @return the amount of mana that could not be sent
     */
    double addManaToStorage(double amount);
}
