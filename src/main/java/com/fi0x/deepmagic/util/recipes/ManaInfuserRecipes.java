package com.fi0x.deepmagic.util.recipes;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ManaInfuserRecipes
{
    private static final ManaInfuserRecipes INSTANCE = new ManaInfuserRecipes();
    private final Map<ItemStack, ItemStack> infusionList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static ManaInfuserRecipes instance()
    {
        return INSTANCE;
    }
    private ManaInfuserRecipes()
    {
        addManaInfuserRecipe(new ItemStack(ModItems.DEEP_CRYSTAL_POWDER), new ItemStack(ModItems.MAGIC_POWDER), 1);
        addManaInfuserRecipe(new ItemStack(ModBlocks.DEEP_CRYSTAL_BLOCK), new ItemStack(ModBlocks.DEMON_CRYSTAL_BLOCK), 1);
    }

    public void addManaInfuserRecipe(ItemStack input, ItemStack result, float xp)
    {
        if(getInfuserResult(input) != ItemStack.EMPTY) return;
        infusionList.put(input, result);
        experienceList.put(result, xp);
    }

    public ItemStack getInfuserResult(ItemStack input)
    {
        for (Map.Entry<ItemStack, ItemStack> entry : infusionList.entrySet())
        {
            if (this.compareItemStacks(input, entry.getKey())) return entry.getValue();
        }

        return ItemStack.EMPTY;
    }
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
    public Map<ItemStack, ItemStack> getInfusionList()
    {
        return infusionList;
    }
    public float getInfusionExperience(ItemStack stack)
    {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;

        for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return 0.0F;
    }
}