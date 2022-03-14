package com.fi0x.deepmagic.world.generators.dungeon.large;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class LargeDungeonComponent extends StructureComponentTemplate
{
    //TODO: Compare with EldritchSpire from ThaumicAugmentation

    protected String name;
    protected boolean fillBlocks;

    public LargeDungeonComponent()
    {
        super(0);
    }

    public LargeDungeonComponent(Template template, String templateName, boolean fillBelow, BlockPos position, Rotation rot, Mirror mi)
    {
        name = templateName;
        fillBlocks = fillBelow;
        templatePosition = position;
        PlacementSettings settings = new PlacementSettings().setIgnoreEntities(true).setRotation(rot).setMirror(mi);
        setup(template, templatePosition, settings);
    }

    public LargeDungeonComponent(TemplateManager templateManager, String templateName, boolean fillBelow, BlockPos position, Rotation rot, Mirror mi)
    {
        this(templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, templateName)), templateName, fillBelow, position, rot, mi);
    }

    @Override
    public boolean addComponentParts(@Nonnull World world, @Nonnull Random random, @Nonnull StructureBoundingBox bb)
    {
        placeSettings.setBoundingBox(bb);
        template.addBlocksToWorld(world, templatePosition, new TemplateProcessor(templatePosition, placeSettings), placeSettings, 18);
        Map<BlockPos, String> map = template.getDataBlocks(templatePosition, placeSettings);
        for(Entry<BlockPos, String> entry : map.entrySet())
        {
            String s = entry.getValue();
            handleDataMarker(s, entry.getKey(), world, random, bb);
        }

        return true;
    }

    public Template getTemplate()
    {
        return template;
    }

    @Override
    protected void writeStructureToNBT(@Nonnull NBTTagCompound tag)
    {
        super.writeStructureToNBT(tag);
        tag.setString("tn", name);
        tag.setString("rot", placeSettings.getRotation().name());
        tag.setString("mi", placeSettings.getMirror().name());
    }

    @Override
    protected void readStructureFromNBT(@Nonnull NBTTagCompound tag, @Nonnull TemplateManager templateManager)
    {
        super.readStructureFromNBT(tag, templateManager);
        name = tag.getString("tn");
        Rotation rot = Rotation.valueOf(tag.getString("rot"));
        Mirror mi = Mirror.valueOf(tag.getString("mi"));
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, name));
        PlacementSettings settings = new PlacementSettings().setIgnoreEntities(true).setRotation(rot).setMirror(mi);
        assert template != null;
        setup(template, templatePosition, settings);
    }

    @Override
    protected void handleDataMarker(@Nullable String function, @Nullable BlockPos pos, @Nullable World world, @Nullable Random rand, @Nullable StructureBoundingBox sbb)
    {
        //TODO: Replace structure-blocks with correct block
    }

    public void onPostGeneration(World world, StructureBoundingBox structurebb)
    {
        //TODO: Find out what needs to be here
    }

    public static class TemplateProcessor implements ITemplateProcessor
    {
        protected float chance;
        protected Random rand;

        public TemplateProcessor(BlockPos pos, PlacementSettings settings)
        {
            chance = settings.getIntegrity();
            rand = settings.getRandom(pos);
        }

        @Override
        @Nullable
        public BlockInfo processBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockInfo blockInfo)
        {
            return chance < 1.0F && rand.nextFloat() > chance ? null : blockInfo;
        }
    }
}
