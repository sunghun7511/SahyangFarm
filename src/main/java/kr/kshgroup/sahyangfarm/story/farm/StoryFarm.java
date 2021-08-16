package kr.kshgroup.sahyangfarm.story.farm;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.data.DataManager;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.model.FarmOffset;
import kr.kshgroup.sahyangfarm.story.SFStoryBase;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.util.WorldEditUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class StoryFarm implements SFStoryBase {
    private final DataManager dataManager;
    private final StoryManager storyManager;

    public StoryFarm() {
        dataManager = (DataManager) SahyangFarm.getManager(DataManager.class);
        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    public Farm getFarm(Player player) {
        return dataManager.getServerData().getFarms().stream()
                .filter(farm -> farm.isIn(player))
                .findFirst()
                .orElse(null);
    }

    public void teleportFarm(Player player) {
        teleportFarm(player, player);
    }

    public void teleportFarm(Player player, Player targetFarm) {
        StoryWorld storyWorld = storyManager.getStory(StoryWorld.class);
        World farmWorld = storyWorld.getWorld();

        Farm farm = getFarm(targetFarm);
        if (Objects.isNull(farm)) {
            if (!player.getUniqueId().equals(targetFarm.getUniqueId())) {
                player.sendMessage(Reference.PREFIX + "아직 팜을 갖고있지 않은 유저입니다.");
                return;
            }

            farm = createFarm(farmWorld, player);
            if (Objects.isNull(farm)) {
                return;
            }
        }

        player.teleport(farm.getCenter().clone());
    }

    private Farm createFarm(World world, Player player) {
        player.sendMessage(Reference.PREFIX + "팜 생성을 시작합니다.");
        player.sendMessage(Reference.PREFIX + "잠시만 기다려주세요");

        StoryFarmOffset storyFarmOffset = storyManager.getStory(StoryFarmOffset.class);
        FarmOffset offset = storyFarmOffset.getAvailableOffset();

        try {
            File file = new File(SahyangFarm.getInstance().getDataFolder(), Reference.SCHEMATIC_FILENAME);
            BukkitWorld bukkitWorld = new BukkitWorld(world);
            Clipboard clipboard = WorldEditUtil.getClipboardFromFile(file, bukkitWorld);

            int minY = Math.min(clipboard.getMaximumPoint().getBlockY(), clipboard.getMinimumPoint().getBlockY());
            int offsetY = clipboard.getOrigin().getBlockY() - minY;
            int distance = Reference.FARM_PADDING * 2 + Reference.FARM_MARGIN + 1;
            Location center = new Location(world, offset.getX() * distance, offsetY, offset.getZ() * distance);
            WorldEditUtil.pasteClipboard(clipboard, bukkitWorld, center);

            Farm farm = new Farm(player, center, offset, Reference.DEFAULT_MAX_USERS);
            dataManager.getServerData().getFarms().add(farm);

            return farm;
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(Reference.PREFIX + "팜 생성에 실패했습니다.");
            storyFarmOffset.freeOffset(offset);
            return null;
        }
    }
}
