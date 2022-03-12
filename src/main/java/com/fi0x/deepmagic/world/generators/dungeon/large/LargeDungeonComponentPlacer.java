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
        MapGenStructureIO.registerStructureComponent(LargeDungeonBaseComponent.class, "ldtb");
    }

    public static void generate(TemplateManager templateManager, BlockPos position, Rotation rot, Random random, List<LargeDungeonComponent> pieces)
    {
        Mirror mirror = Mirror.values()[random.nextInt(Mirror.values().length)];
        BlockPos.MutableBlockPos currentRoomCenter = new BlockPos.MutableBlockPos(position);

        BlockPos centerSize = generateCenter(templateManager, currentRoomCenter, rot, mirror, random, pieces);
        for(EnumFacing side : EnumFacing.HORIZONTALS)
            generateRoom(templateManager, currentRoomCenter, centerSize, rot, side, mirror, random, pieces);
    }

    private static BlockPos generateCenter(TemplateManager templateManager, BlockPos.MutableBlockPos currentDungeonCenter, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        BlockPos original = currentDungeonCenter.toImmutable();
        LargeDungeonComponent center = new LargeDungeonBaseComponent(templateManager, pickTemplate(BASE, random), false, original, rot, mirror);
        pieces.add(center);

        return center.getTemplate().getSize();
    }

    private static void generateConnector(TemplateManager templateManager, BlockPos.MutableBlockPos lastRoomCenter, BlockPos lastRoomSize, Rotation rot, EnumFacing lastRoomDoorSide, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
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

    @Deprecated
    public static void generateGroundFloor(TemplateManager templateManager, BlockPos.MutableBlockPos current, BlockPos baseSize, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        String tName = pickTemplate(GROUND_FLOOR, random);
        assert tName != null;
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
        BlockPos original = current.toImmutable();

        assert template != null;
        BlockPos add = transformOffset((baseSize.getX() - template.getSize().getX()) / 2, (baseSize.getZ() - template.getSize().getZ()) / 2, rot, mirror);
        current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
        LargeDungeonComponent floor = new LargeDungeonComponent(template, tName, true, current.toImmutable(), rot, mirror);
        pieces.add(floor);

        current.setPos(original.up(floor.getTemplate().getSize().getY()));
    }

    @Deprecated
    public static void generateFirstFloor(TemplateManager templateManager, BlockPos.MutableBlockPos current, BlockPos baseSize, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        String tName = pickTemplate(FIRST_FLOOR, random);
        assert tName != null;
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
        BlockPos original = current.toImmutable();
        assert template != null;
        BlockPos add = transformOffset((baseSize.getX() - template.getSize().getX()) / 2, (baseSize.getZ() - template.getSize().getZ()) / 2, rot, mirror);
        current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
        LargeDungeonComponent floor = new LargeDungeonComponent(template, tName, true, current.toImmutable(), rot, mirror);
        pieces.add(floor);

        String cName = pickTemplate(CONNECTOR_MAZE, random);
        assert cName != null;
        Template cTemplate = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, cName));
        assert cTemplate != null;
        BlockPos backup = current.add(transformOffset(0, template.getSize().getZ() + cTemplate.getSize().getZ() + 1, rot, mirror));
        add = transformOffset(template.getSize().getX() / 2, template.getSize().getZ(), rot, mirror);
        current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
        add = transformOffset(cTemplate.getSize().getX() / 2, cTemplate.getSize().getZ() - 1, rot, mirror);
        current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
        Rotation cRot = mirror == Mirror.LEFT_RIGHT ? rot.add(Rotation.CLOCKWISE_180) : rot;
        pieces.add(new LargeDungeonComponent(cTemplate, cName, false, current.toImmutable(), cRot.add(fromFacing(mirror.mirror(EnumFacing.NORTH))), mirror));

        current.setPos(backup);

        current.setPos(original.up(floor.getTemplate().getSize().getY()));
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

    protected static final String CENTER = "dungeon/large/dungeon_center";
    protected static final String ROOM = "dungeon/large/dungeon_room";
    protected static final String CONNECTOR = "dungeon/large/dungeon_connector";
    //TODO: Use own structures
    protected static final String BASE = "spire/base_";
    protected static final String STAIR = "spire/stairs_";
    protected static final String GROUND_FLOOR = "spire/ground_floor_";
    protected static final String FIRST_FLOOR = "spire/first_floor_";
    protected static final String CONNECTOR_MAZE = "spire/connector_maze_";

    protected static final ImmutableSet<String> TEMPLATE_PATHS = ImmutableSet.<String>builder().add(
            CENTER, ROOM, CONNECTOR
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
