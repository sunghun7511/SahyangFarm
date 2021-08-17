package kr.kshgroup.sahyangfarm.story.farm;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.story.SFStoryBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.util.Optional;

public class StoryWorld implements SFStoryBase {
    public World getWorld() {
        return Optional.ofNullable(Bukkit.getWorld(Reference.WORLD_NAME))
                .orElseGet(this::createNewWorld);
    }

    private World createNewWorld() {
        WorldCreator wc = new WorldCreator(Reference.WORLD_NAME);

        wc.type(WorldType.FLAT);
        wc.generatorSettings("3;minecraft:air;127;decoration;2;");

        return wc.createWorld();
    }
}
