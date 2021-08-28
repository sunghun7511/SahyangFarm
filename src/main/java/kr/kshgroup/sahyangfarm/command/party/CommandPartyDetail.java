package kr.kshgroup.sahyangfarm.command.party;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CommandPartyDetail extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartyDetail() {
        super("확인", "상세", "detail", "check");

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

        player.sendMessage(Reference.PREFIX + "=-=-=-=[ 팜 정보 ]=-=-=-=");
        player.sendMessage(Reference.PREFIX + "주인 : " + farm.getOwnerName());

        List<UUID> users = farm.getUsers();
        if (users.size() != 0) {
            player.sendMessage(Reference.PREFIX + "소속된 유저 (" + users.size() + " / " + farm.getMaxUser() + "):");
            for (UUID user : users) {
                player.sendMessage(Reference.PREFIX + " - " + Bukkit.getOfflinePlayer(user).getName());
            }
        }

        List<UUID> parttimes = farm.getUsers();
        if (parttimes.size() != 0) {
            player.sendMessage(Reference.PREFIX + "고용된 알바 (" + parttimes.size() + "명):");
            for (UUID user : parttimes) {
                player.sendMessage(Reference.PREFIX + " - " + Bukkit.getOfflinePlayer(user).getName());
            }
        }

        player.sendMessage(Reference.PREFIX + "상태 : " + (farm.isAllowMove() ? "열림" : "닫힘 (이동 불가)"));

        Location center = farm.getCenter();
        player.sendMessage(Reference.PREFIX + "위치 : X=" + center.getBlockX() + ", Y=" + center.getBlockY() + ", Z=" + center.getBlockZ());
        return true;
    }
}
