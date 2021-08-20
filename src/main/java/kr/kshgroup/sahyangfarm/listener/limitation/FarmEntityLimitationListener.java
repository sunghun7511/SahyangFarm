package kr.kshgroup.sahyangfarm.listener.limitation;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.listener.SFListener;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Objects;

public class FarmEntityLimitationListener extends SFListener {
    private final StoryManager storyManager;

    public FarmEntityLimitationListener() {
        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity) || entity instanceof Player) {
            return;
        }

        Location location = entity.getLocation();
        World world = location.getWorld();
        if (!world.getName().equals(Reference.WORLD_NAME)) {
            return;
        }

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        Farm farm = storyFarm.getFarmFromLocation(location);
        if (Objects.isNull(farm)) {
            return;
        }

        int maxDistance = Reference.FARM_PADDING;
        long count = world.getNearbyEntities(farm.getCenter(), maxDistance, 255, maxDistance).stream()
                .filter(LivingEntity.class::isInstance)
                .count();
        if (count > Reference.MAX_ENTITY_IN_FARM) {
            event.setCancelled(true);
        }
    }
}
