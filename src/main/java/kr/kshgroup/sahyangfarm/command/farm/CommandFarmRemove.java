package kr.kshgroup.sahyangfarm.command.farm;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandFarmRemove extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandFarmRemove() {
        super("제거", "삭제", "remove", "delete");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        Farm farm = storyFarm.getFarm(player);
        if (Objects.isNull(farm)) {
            player.sendMessage(Reference.PREFIX + "소속된 팜이 없습니다.");
            return true;
        }

        if (!farm.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "팜의 주인이 아닙니다.");
            return true;
        }

        if (farm.getUsers().size() != 0) {
            player.sendMessage(Reference.PREFIX + "아직 팜 멤버가 남아있습니다.");
            return true;
        }

        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        storyFarm.removeFarm(farm);
        player.sendMessage(Reference.PREFIX + "성공적으로 팜을 제거하였습니다.");
        return true;
    }
}
