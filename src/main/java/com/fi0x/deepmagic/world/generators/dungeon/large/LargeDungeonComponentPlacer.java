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

        BlockPos centerSize = generateCenter(templateManager, currentRoomCenter, rot, mirror, random, pieces);
        for(EnumFacing side : EnumFacing.HORIZONTALS)
            generateConnector(templateManager, currentRoomCenter, centerSize, rot, side, mirror, random, pieces);
    }

    private static BlockPos generateCenter(TemplateManager templateManager, BlockPos.MutableBlockPos currentDungeonCenter, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        BlockPos original = currentDungeonCenter.toImmutable();
        LargeDungeonComponent center = new LargeDungeonComponent(templateManager, pickTemplate(ROOM, random), false, original, rot, mirror);
        pieces.add(center);

        return center.getTemplate().getSize();
    }

    private static void generateConnector(TemplateManager templateManager, BlockPos.MutableBlockPos lastRoomCenter, BlockPos lastRoomSize, Rotation rot, EnumFacing lastRoomDoorSide, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        //TODO: Find out why these are not placed or if they are placed at the wrong location
        String tName = pickTemplate(CONNECTOR, random);
        assert tName != null;
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
        BlockPos original = lastRoomCenter.toImmutable();
        LargeDungeonComponent connector = new LargeDungeonComponent(template, tName, false, original, rot, mirror);
        pieces.add(connector);

        assert template != null;
        BlockPos add = transformOffset((lastRoomSize.getX() - template.getSize().getX()) / 2, (lastRoomSize.getZ() - template.getSize().getZ()) / 2, rot, mirror);
        BlockPos.MutableBlockPos thisPos = new BlockPos.MutableBlockPos(original.add(add));

        generateRoom(templateManager, thisPos, connector.getTemplate().getSize(), rot, lastRoomDoorSide, mirror, random, pieces);
        //TODO: Check if position is correct
    }

    private static void generateRoom(TemplateManager templateManager, BlockPos.MutableBlockPos lastRoomCenter, BlockPos lastRoomSize, Rotation rot, EnumFacing lastRoomDoorSide, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        //TODO: Implement this; might need to generateConnector recursive for open paths
    }

    protected static BlockPos transformOffset(int x, int z, Rotation rot, Mirror mi)
    {
        return new BlockPos(mi == Mirror.FRONT_BACK ? -x : x, 0, mi == Mirror.LEFT_RIGHT ? -z : z).rotate(rot);
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
