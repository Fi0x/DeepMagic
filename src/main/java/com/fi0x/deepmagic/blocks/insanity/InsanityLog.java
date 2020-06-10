package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

public class InsanityLog extends BlockLog
{
    public InsanityLog(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(DeepMagicTab.BLOCKS);

        setSoundType(SoundType.WOOD);
        setHardness(2.1F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
}