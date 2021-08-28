package kr.kshgroup.sahyangfarm.command.parttime;

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

public class CommandPartTimeAdd extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartTimeAdd() {
        super("고용", "추가", "add");

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

        if (args.length != 1) {
            player.sendMessage(Reference.PREFIX + "알바로 고용할 유저의 닉네임을 입력해주세요.");
            player.sendMessage(Reference.PREFIX + "/" + label + " <닉네임>");
            return true;
        }

        String nickname = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(nickname);
        if (Objects.isNull(target)) {
            player.sendMessage(Reference.PREFIX + "존재하지 않는 플레이어이거나 온라인 상태가 아닙니다.");
            return true;
        }

        if (farm.getPartTimes().contains(target.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "이미 알바로 고용된 유저입니다.");
            return true;
        }

        farm.getPartTimes().add(target.getUniqueId());
        player.sendMessage(Reference.PREFIX + target.getName() + "님을 알바로 고용했습니다.");
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Reference.PREFIX + player.getName() + "님이 당신을 알바로 고용했습니다.");
            target.getPlayer().sendMessage(Reference.PREFIX + "'/팜 이동 " + player.getName() + "'을 통해 이동하세요.");
        }
        return true;
    }
}
