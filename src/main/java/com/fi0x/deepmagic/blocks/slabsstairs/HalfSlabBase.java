package com.fi0x.deepmagic.blocks.slabsstairs;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.util.IHasModel;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class HalfSlabBase extends SlabBase implements IHasModel
{
    public HalfSlabBase(String name, Material materialIn)
    {
        super(name, materialIn);
        setCreativeTab(DeepMagicTab.BLOCKS);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }
}
