package kr.kshgroup.sahyangfarm.command.admin;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandMaxUserIncrease extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandMaxUserIncrease() {
        super("증설", "increase");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(Reference.PREFIX + "이 명령어를 사용할 권한이 없습니다.");
            return true;
        }

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        Farm farm = storyFarm.getFarm(player);
        if (Objects.isNull(farm)) {
            player.sendMessage(Reference.PREFIX + "팜을 생성하지 않았습니다.");
            player.sendMessage(Reference.PREFIX + "/팜 명령어를 통해 팜을 생성해주세요.");
            return true;
        }

        farm.setMaxUser(farm.getMaxUser() + 1);
        return false;
    }
}
