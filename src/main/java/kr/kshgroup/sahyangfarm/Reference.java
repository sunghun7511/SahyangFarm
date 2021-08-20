package kr.kshgroup.sahyangfarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Reference {
    public static final String PREFIX = "§a[ §e으내듀밸리 §a] §f";
    public static final String WORLD_NAME = "farm";

    public static final String SCHEMATIC_FILENAME = "farm.schematic";
    public static final int FARM_PADDING = 100;
    public static final int FARM_MARGIN = 200;

    public static final int MAX_ENTITY_IN_FARM = 20;

    public static final int DEFAULT_MAX_USERS = 1;
    public static final List<String> COMMAND_AVAILABLE_WORLDS = new ArrayList<>(Arrays.asList(
            "world",
            "mine",
            "mine_streamer",
            "farm"
    ));
}
