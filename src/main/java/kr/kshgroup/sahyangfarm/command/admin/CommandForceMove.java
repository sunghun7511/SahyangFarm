package kr.kshgroup.sahyangfarm.command.admin;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandForceMove extends SFCommandExecutor {
    private final StoryManager storyManager;
    public CommandForceMove() {
        super("이동", "move");

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

        if (args.length == 0) {
            player.sendMessage(Reference.PREFIX + "이동할 유저의 닉네임을 입력해주세요.");
            player.sendMessage(Reference.PREFIX + "/" + label + " <닉네임>");
            return true;
        }

        String nickname = args[0];
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nickname);
        if (Objects.isNull(offlinePlayer)) {
            player.sendMessage(Reference.PREFIX + "존재하지 않는 플레이어입니다.");
            return true;
        }

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        Farm farm = storyFarm.getFarm(offlinePlayer);
        if (Objects.isNull(farm)) {
            player.sendMessage(Reference.PREFIX + "플레이어의 팜이 없습니다.");
            return true;
        }

        storyFarm.teleportFarm(player, offlinePlayer);
        return false;
    }
}
