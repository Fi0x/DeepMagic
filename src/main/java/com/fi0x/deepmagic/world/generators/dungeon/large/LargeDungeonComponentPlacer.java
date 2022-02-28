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
        MapGenStructureIO.registerStructureComponent(LargeDungeonComponent.class, "est");
        MapGenStructureIO.registerStructureComponent(LargeDungeonBaseComponent.class, "estb");
        MapGenStructureIO.registerStructureComponent(LargeDungeonMazeComponent.class, "estm");
        MapGenStructureIO.registerStructureComponent(LargeDungeonPillarComponent.class, "estp");
    }

    public static void generate(TemplateManager templateManager, BlockPos position, Rotation rot, Random random, List<LargeDungeonComponent> pieces)
    {
        Mirror mirror = Mirror.values()[random.nextInt(Mirror.values().length)];
        BlockPos.MutableBlockPos current = new BlockPos.MutableBlockPos(position);

        //TODO: Remake to fit own dungeon-design
        BlockPos baseSize = generateBase(templateManager, current, rot, mirror, random, pieces);
        generateGroundFloor(templateManager, current, baseSize, rot, mirror, random, pieces);
        generateFirstFloor(templateManager, current, baseSize, rot, mirror, random, pieces);
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
        if(c == 1)
            return type + "0";
        else
            return type + rand.nextInt(c);
    }

    public static BlockPos generateBase(TemplateManager templateManager, BlockPos.MutableBlockPos current, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        BlockPos original = current.toImmutable();
        LargeDungeonComponent base = new LargeDungeonBaseComponent(templateManager, pickTemplate(BASE, random), true, original, rot, mirror);
        pieces.add(base);
        BlockPos offset = current.add(transformOffset(base.getTemplate().getSize().getX() / 2, base.getTemplate().getSize().getZ() / 2, rot, mirror));
        current.setPos(offset);
        for(EnumFacing f : EnumFacing.HORIZONTALS)
        {
            EnumFacing face = rot.rotate(mirror.mirror(f));
            String tName = pickTemplate(STAIR, random);
            Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
            BlockPos add = transformOffset(base.getTemplate().getSize().getX() / 2 + 1, base.getTemplate().getSize().getZ() / 2 + 1, rot.add(fromFacing(face)), mirror);
            current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());

            assert template != null;
            add = transformOffset((-template.getSize().getX() - base.getTemplate().getSize().getX()) / 2, 0, rot.add(fromFacing(face)), mirror);
            current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
            pieces.add(new LargeDungeonBaseComponent(template, tName, true, current.toImmutable(), rot.add(fromFacing(face)), mirror));
            current.setPos(offset);
        }

        current.setPos(original.up(base.getTemplate().getSize().getY()));
        return base.getTemplate().getSize();
    }

    public static void generateGroundFloor(TemplateManager templateManager, BlockPos.MutableBlockPos current, BlockPos baseSize, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {

        String tName = pickTemplate(GROUND_FLOOR, random);
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
        BlockPos original = current.toImmutable();
        assert template != null;
        BlockPos add = transformOffset((baseSize.getX() - template.getSize().getX()) / 2, (baseSize.getZ() - template.getSize().getZ()) / 2, rot, mirror);
        current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
        LargeDungeonComponent floor = new LargeDungeonComponent(template, tName, true, current.toImmutable(), rot, mirror);
        pieces.add(floor);

        current.setPos(original.up(floor.getTemplate().getSize().getY()));
    }

    public static void generateFirstFloor(TemplateManager templateManager, BlockPos.MutableBlockPos current, BlockPos baseSize, Rotation rot, Mirror mirror, Random random, List<LargeDungeonComponent> pieces)
    {
        String tName = pickTemplate(FIRST_FLOOR, random);
        Template template = templateManager.get(FMLCommonHandler.instance().getMinecraftServerInstance(), new ResourceLocation(Reference.MOD_ID, tName));
        BlockPos original = current.toImmutable();
        assert template != null;
        BlockPos add = transformOffset((baseSize.getX() - template.getSize().getX()) / 2, (baseSize.getZ() - template.getSize().getZ()) / 2, rot, mirror);
        current.setPos(current.getX() + add.getX(), current.getY(), current.getZ() + add.getZ());
        LargeDungeonComponent floor = new LargeDungeonComponent(template, tName, true, current.toImmutable(), rot, mirror);
        pieces.add(floor);

        String cName = pickTemplate(CONNECTOR_MAZE, random);
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

    protected static final String BASE = "spire/base_";
    protected static final String STAIR = "spire/stairs_";
    protected static final String GROUND_FLOOR = "spire/ground_floor_";
    protected static final String FIRST_FLOOR = "spire/first_floor_";
    protected static final String SECOND_FLOOR = "spire/second_floor_";
    protected static final String THIRD_FLOOR = "spire/third_floor_";
    protected static final String FOURTH_FLOOR = "spire/fourth_floor_";
    protected static final String PILLAR = "spire/pillar_";
    protected static final String PILLAR_BASE = "spire/pillar_b_";
    protected static final String CONNECTOR_MAZE = "spire/connector_maze_";
    protected static final String CONNECTOR_PRISON = "spire/connector_prison_";
    protected static final String CONNECTOR_LIBRARY = "spire/connector_library_";
    protected static final String CONNECTOR_BOSS = "spire/connector_boss_";

    protected static final String MAZE_SHELL_BL = "spire/maze/shell_bl_";
    protected static final String MAZE_SHELL_BR = "spire/maze/shell_br_";
    protected static final String MAZE_SHELL_FL = "spire/maze/shell_fl_";
    protected static final String MAZE_SHELL_FR = "spire/maze/shell_fr_";
    protected static final String MAZE_PILLAR = "spire/maze/pillar_";
    protected static final String MAZE_PILLAR_BASE = "spire/maze/pillar_b_";
    protected static final String MAZE_CELL_HALL_CROSS = "spire/maze/cell_hall_cross_";
    protected static final String MAZE_CELL_HALL_T = "spire/maze/cell_hall_t_";
    protected static final String MAZE_CELL_HALL_CORNER = "spire/maze/cell_hall_corner_";
    protected static final String MAZE_CELL_HALL_STRAIGHT = "spire/maze/cell_hall_straight_";
    protected static final String MAZE_CELL_HALL_END = "spire/maze/cell_hall_end_";
    protected static final String MAZE_CELL_ROOM_CROSS = "spire/maze/cell_room_cross_";
    protected static final String MAZE_CELL_ROOM_T = "spire/maze/cell_room_t_";
    protected static final String MAZE_CELL_ROOM_CORNER = "spire/maze/cell_room_corner_";
    protected static final String MAZE_CELL_ROOM_STRAIGHT = "spire/maze/cell_room_straight_";
    protected static final String MAZE_CELL_ROOM_END = "spire/maze/cell_room_end_";
    protected static final String MAZE_CELL_ENTRANCE_CROSS = "spire/maze/cell_entrance_cross_";
    protected static final String MAZE_CELL_ENTRANCE_T = "spire/maze/cell_entrance_t_";
    protected static final String MAZE_CELL_ENTRANCE_CORNER = "spire/maze/cell_entrance_corner_";
    protected static final String MAZE_CELL_ENTRANCE_STRAIGHT = "spire/maze/cell_entrance_straight_";
    protected static final String MAZE_CELL_ENTRANCE_END = "spire/maze/cell_entrance_end_";
    protected static final String MAZE_CELL_ROOM_END_CRAB = "spire/maze/cell_room_end_crab_";
    protected static final String MAZE_OVERLAY_ROOM = "spire/maze/cell_overlay_room_";
    protected static final String MAZE_OVERLAY_HALL = "spire/maze/cell_overlay_hall_";
    protected static final String MAZE_OVERLAY_WEB = "spire/maze/cell_overlay_web_";
    protected static final String MAZE_OVERLAY_KEY = "spire/maze/cell_overlay_key_";

    protected static final String PRISON_ENTRANCE = "spire/prison/entrance_";
    protected static final String PRISON_0 = "spire/prison/0_";
    protected static final String PRISON_0_CORNER = "spire/prison/0_cell_corner_";
    protected static final String PRISON_0_SIDE = "spire/prison/0_cell_side_";
    protected static final String PRISON_BLOCK_CONNECTOR = "spire/prison/connector_";
    protected static final String PRISON_1 = "spire/prison/1_";
    protected static final String PRISON_1_CORNER = "spire/prison/1_cell_corner_";
    protected static final String PRISON_1_SIDE = "spire/prison/1_cell_side_";
    protected static final String PRISON_EDGE = "spire/prison/cell_edge_";

    protected static final String LIBRARY_GROUND_FLOOR_FRONT = "spire/library/ground0_";
    protected static final String LIBRARY_GROUND_FLOOR_BACK = "spire/library/ground1_";
    protected static final String LIBRARY_FIRST_FLOOR_FRONT = "spire/library/first0_";
    protected static final String LIBRARY_FIRST_FLOOR_BACK = "spire/library/first1_";
    protected static final String LIBRARY_SECOND_FLOOR_FRONT = "spire/library/second0_";
    protected static final String LIBRARY_SECOND_FLOOR_BACK = "spire/library/second1_";
    protected static final String LIBRARY_BACK = "spire/library/back_";
    protected static final String LIBRARY_CORNER = "spire/library/corner_";
    protected static final String LIBRARY_SIDE_FRONT = "spire/library/side0_";
    protected static final String LIBRARY_SIDE_BACK = "spire/library/side1_";
    protected static final String LIBRARY_HALL_LOWER = "spire/library/lower_hall_";
    protected static final String LIBRARY_HALL_LOWER_BACK = "spire/library/lower_hall_back_";
    protected static final String LIBRARY_HALL_UPPER = "spire/library/upper_hall_";
    protected static final String LIBRARY_ALCOVE = "spire/library/alcove_";
    protected static final String LIBRARY_ALCOVE_KEY = "spire/library/alcove_key_";
    protected static final String LIBRARY_PILLAR = "spire/library/pillar_";
    protected static final String LIBRARY_PILLAR_BASE = "spire/library/pillar_b_";

    protected static final String BOSS = "spire/boss/boss_";

    // so we don't need to have an individual loop for every single path
    protected static final ImmutableSet<String> TEMPLATE_PATHS = ImmutableSet.<String>builder().add(
            BASE, STAIR, GROUND_FLOOR, FIRST_FLOOR, SECOND_FLOOR, THIRD_FLOOR, FOURTH_FLOOR, CONNECTOR_MAZE, PILLAR, PILLAR_BASE,
            CONNECTOR_PRISON, CONNECTOR_LIBRARY, CONNECTOR_BOSS, MAZE_SHELL_BL, MAZE_SHELL_BR, MAZE_SHELL_FL, MAZE_SHELL_FR, MAZE_PILLAR,
            MAZE_PILLAR_BASE, MAZE_CELL_HALL_CROSS, MAZE_CELL_HALL_T, MAZE_CELL_HALL_CORNER, MAZE_CELL_HALL_STRAIGHT, MAZE_CELL_HALL_END,
            MAZE_CELL_ROOM_CROSS, MAZE_CELL_ROOM_T, MAZE_CELL_ROOM_CORNER, MAZE_CELL_ROOM_STRAIGHT, MAZE_CELL_ROOM_END,
            MAZE_CELL_ENTRANCE_CROSS, MAZE_CELL_ENTRANCE_T, MAZE_CELL_ENTRANCE_CORNER, MAZE_CELL_ENTRANCE_STRAIGHT, MAZE_CELL_ENTRANCE_END,
            MAZE_CELL_ROOM_END_CRAB, MAZE_OVERLAY_ROOM, MAZE_OVERLAY_HALL, MAZE_OVERLAY_WEB, MAZE_OVERLAY_KEY,
            PRISON_ENTRANCE, PRISON_0, PRISON_0_CORNER, PRISON_0_SIDE, PRISON_BLOCK_CONNECTOR, PRISON_1, PRISON_1_CORNER,
            PRISON_1_SIDE, PRISON_EDGE, LIBRARY_GROUND_FLOOR_FRONT, LIBRARY_GROUND_FLOOR_BACK, LIBRARY_FIRST_FLOOR_FRONT,
            LIBRARY_FIRST_FLOOR_BACK, LIBRARY_SECOND_FLOOR_FRONT, LIBRARY_SECOND_FLOOR_BACK, LIBRARY_BACK,
            LIBRARY_CORNER, LIBRARY_SIDE_FRONT, LIBRARY_SIDE_BACK, LIBRARY_HALL_LOWER, LIBRARY_HALL_LOWER_BACK,
            LIBRARY_HALL_UPPER, LIBRARY_ALCOVE, LIBRARY_ALCOVE_KEY, LIBRARY_PILLAR, LIBRARY_PILLAR_BASE, BOSS
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
