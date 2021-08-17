package kr.kshgroup.sahyangfarm.command.party;

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

public class CommandPartyTransfer extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartyTransfer() {
        super("양도", "이전", "transfer");

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
            player.sendMessage(Reference.PREFIX + "추방할 유저의 닉네임을 입력해주세요.");
            player.sendMessage(Reference.PREFIX + "/" + label + " <닉네임>");
            return true;
        }

        String nickname = args[0];
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nickname);
        if (Objects.isNull(offlinePlayer)) {
            player.sendMessage(Reference.PREFIX + "존재하지 않는 플레이어입니다.");
            return true;
        }

        if (!farm.getUsers().contains(offlinePlayer.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "당신의 팜 멤버가 아닙니다.");
            return true;
        }

        farm.setOwner(offlinePlayer);
        farm.getUsers().remove(offlinePlayer.getUniqueId());
        farm.getUsers().add(player.getUniqueId());

        player.sendMessage(Reference.PREFIX + offlinePlayer.getName() + "님에게 팜을 양도하였습니다.");
        if (offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(Reference.PREFIX + "당신은 팜을 위임받아 이제 팜의 주인입니다.");
        }
        return true;
    }
}