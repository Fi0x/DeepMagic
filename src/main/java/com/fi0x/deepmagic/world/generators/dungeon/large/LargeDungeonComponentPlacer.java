package com.fi0x.deepmagic.world.generators.dungeon.large;

import com.fi0x.deepmagic.util.Reference;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;
import java.util.Random;

public class LargeDungeonComponentPlacer
{
    public static void register()
    {
        MapGenStructureIO.registerStructureComponent(LargeDungeonComponent.class, "ldt");
    }

    public static void generate(TemplateManager templateManager, BlockPos position, Rotation rot, Random random, List<LargeDungeonComponent> pieces)
    {
        Mirror mirror = Mirror.values()[random.nextInt(Mirror.values().length)];
        BlockPos.MutableBlockPos currentRoomCenter = new BlockPos.MutableBlockPos(position);

        System.out.println("Generating at " + position.getX() + ", " + position.getY() + ", " + position.getZ() + " with rotation " + rot + " and mirror " + mirror);

        BlockPos centerSize = generateCenter(templateManager, currentRoomCenter, rot, mirror, random, pieces);
        for(EnumFacing side : EnumFacing.HORIZONTALS)
            generateConnector(templateManager, currentRoomCenter, centerSize, rot, side, mirror, random, pieces);
    }

    private static BlockPos generateCenter(TemplateManager templateManager, BlockPos.MutableBlockPos currentDungeonCenter, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        BlockPos original = currentDungeonCenter.toImmutable();
        LargeDungeonComponent center = new LargeDungeonComponent(templateManager, pickTemplate(ROOM, random), false, original, rot, mirror);

        BlockPos size = center.getTemplate().getSize();

        BlockPos offset = getGenerationOffset(rot, mirror, size);
        center.offset(offset.getX(), offset.getY(), offset.getZ());

        pieces.add(center);

        return size;
    }

    private static void generateConnector(TemplateManager templateManager, BlockPos.MutableBlockPos lastRoomCenter, BlockPos lastRoomSize, Rotation rot, EnumFacing lastRoomDoorSide, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        //TODO: maybe place stairs or edges instead at random
        String tName = pickTemplate(CONNECTOR, random);
        assert tName != null;
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
        BlockPos previousCenter = lastRoomCenter.toImmutable();
        BlockPos placementPosition = getTemplateCenterShift(previousCenter.getX(), previousCenter.getZ(), rot, mirror, lastRoomDoorSide, lastRoomSize);
        LargeDungeonComponent connector = new LargeDungeonComponent(template, tName, false, placementPosition, rot, mirror);
        pieces.add(connector);

        assert template != null;
        BlockPos add = getGenerationOffset(rot, mirror, lastRoomSize);
        BlockPos.MutableBlockPos thisPos = new BlockPos.MutableBlockPos(placementPosition.add(add));

        generateRoom(templateManager, thisPos, connector.getTemplate().getSize(), rot, lastRoomDoorSide, mirror, random, pieces);
        //TODO: Check if position is correct
    }

    private static void generateRoom(TemplateManager templateManager, BlockPos.MutableBlockPos lastRoomCenter, BlockPos lastRoomSize, Rotation rot, EnumFacing lastRoomDoorSide, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        //TODO: Implement this; might need to generateConnector recursive for open paths
    }

    protected static BlockPos getTemplateCenterShift(int x, int z, Rotation rot, Mirror mi, EnumFacing offsetDirection, BlockPos lastRoomSize)
    {
        int xOffset = 0;
        int zOffset = 0;

        switch (offsetDirection)
        {
            case NORTH:
                zOffset = -lastRoomSize.getZ() / 2;
                break;
            case EAST:
                xOffset = lastRoomSize.getX() / 2;
                break;
            case SOUTH:
                zOffset = lastRoomSize.getZ() / 2;
                break;
            case WEST:
                xOffset = -lastRoomSize.getX() / 2;
                break;
        }
        //TODO: Check if rotation and mirror values are correct
        return new BlockPos(xOffset, 0, zOffset);
    }
    protected static BlockPos getGenerationOffset(Rotation rotation, Mirror mi, BlockPos templateSize)
    {
        int xOffset = -templateSize.getX() / 2;
        int zOffset = -templateSize.getZ() / 2;

        if(mi == Mirror.FRONT_BACK)
            xOffset *= -1;
        else if(mi == Mirror.LEFT_RIGHT)
            zOffset *= -1;

        return new BlockPos(xOffset, 0, zOffset).rotate(rotation);
    }

    protected static Rotation fromFacing(EnumFacing face)
    {
        switch(face)
        {
            case WEST:
                return Rotation.CLOCKWISE_90;
            case NORTH:
                return Rotation.CLOCKWISE_180;
            case EAST:
                return Rotation.COUNTERCLOCKWISE_90;
            default:
                return Rotation.NONE;
        }
    }

    protected static String pickTemplate(String type, Random rand)
    {
        int c = templateCounts.get(type);
        return c > 0 ? type + rand.nextInt(c) : null;
    }

    protected static final String ROOM = "dungeon/large/room";
    protected static final String CONNECTOR = "dungeon/large/connector";
    protected static final String STAIRS = "dungeon/large/stairs";
    protected static final String CORNER = "dungeon/large/corner";
    protected static final String CROSSING = "dungeon/large/crossing";

    protected static final ImmutableSet<String> TEMPLATE_PATHS = ImmutableSet.<String>builder().add(
            ROOM, CONNECTOR, STAIRS, CORNER, CROSSING
    ).build();

    protected static ImmutableMap<String, Integer> templateCounts = ImmutableMap.of();

    public static void findTemplateVariants(TemplateManager manager)
    {
        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        for(String s : TEMPLATE_PATHS)
        {
            int i = 0;
            while(manager.get(server, new ResourceLocation(Reference.MOD_ID, s + i)) != null)
                i++;

            builder.put(s, i);
        }

        templateCounts = builder.build();
    }
}
