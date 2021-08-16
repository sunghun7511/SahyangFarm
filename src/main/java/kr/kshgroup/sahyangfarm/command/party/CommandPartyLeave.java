package kr.kshgroup.sahyangfarm.command.party;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandPartyLeave extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartyLeave() {
        super("나가기", "leave");

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

        if (farm.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "팜의 주인은 나갈 수 없습니다.");
            return true;
        }

        farm.getUsers().remove(player.getUniqueId());
        player.sendMessage(Reference.PREFIX + "팜에서 나왔습니다.");
        if (farm.getOwnerPlayer().isOnline()) {
            farm.getOwnerPlayer().getPlayer().sendMessage(Reference.PREFIX + player.getName() + "님이 팜에서 나가셨습니다.");
        }
        return true;
    }
}