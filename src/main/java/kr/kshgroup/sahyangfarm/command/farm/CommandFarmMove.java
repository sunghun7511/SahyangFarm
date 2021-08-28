package kr.kshgroup.sahyangfarm.command.farm;

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

public class CommandFarmMove extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandFarmMove() {
        super("이동", "놀러가기", "move");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

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

        if (!player.isOp()
                && !farm.isAllowMove()
                && !farm.getUsers().contains(player.getUniqueId())
                && !farm.getPartTimes().contains(player.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "팜 주인이 이동을 원하지 않습니다.");
            return true;
        }

        storyFarm.teleportFarm(player, offlinePlayer);
        return false;
    }
}
