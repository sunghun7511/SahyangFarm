package kr.kshgroup.sahyangfarm.listener.protection;

import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.listener.SFListener;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import kr.kshgroup.sahyangfarm.story.farm.StoryWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class FarmDamageListener extends SFListener {
    private final StoryManager storyManager;

    public FarmDamageListener() {
        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFarmDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getDamager();
        if (player.isOp()) {
            return;
        }

        Location target = player.getLocation();
        StoryWorld storyWorld = storyManager.getStory(StoryWorld.class);
        if (!storyWorld.getWorld().getUID().equals(target.getWorld().getUID())) {
            return;
        }

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        Farm farm = storyFarm.getFarmFromLocation(target);
        if (Objects.nonNull(farm) && (farm.isIn(player) || farm.getPartTimes().contains(player.getUniqueId()))) {
            return;
        }

        event.setCancelled(true);
    }
}
