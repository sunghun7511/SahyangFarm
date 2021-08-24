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

public class CommandPartySetSpawn extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartySetSpawn() {
        super("스폰설정", "setspawn");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;
        OfflinePlayer target = player;

        if (args.length != 0) {
            if (!player.isOp()) {
                player.sendMessage(Reference.PREFIX + "이 명령어를 사용할 권한이 없습니다.");
                return true;
            }

            String nickname = args[0];
            target = Bukkit.getOfflinePlayer(nickname);
            if (Objects.isNull(target)) {
                player.sendMessage(Reference.PREFIX + "존재하지 않는 플레이어입니다.");
                return true;
            }
        }

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        Farm farm = storyFarm.getFarm(target);
        if (Objects.isNull(farm)) {
            player.sendMessage(Reference.PREFIX + "소속된 팜이 없습니다.");
            return true;
        }

        if (!player.isOp() && !farm.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "팜의 주인만 스폰 위치를 설정할 수 있습니다.");
            return true;
        }

        farm.setSpawn(player.getLocation());
        player.sendMessage(Reference.PREFIX + "팜 스폰 위치를 설정하였습니다.");
        return true;
    }
}