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

public class CommandMaxUserSet extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandMaxUserSet() {
        super("인원설정", "maxuser");

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

        if (args.length != 2) {
            player.sendMessage(Reference.PREFIX + "유저의 닉네임 또는 최대 인원을 입력해주세요.");
            player.sendMessage(Reference.PREFIX + "/" + label + " <닉네임> <최대 인원>");
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

        int maxUser = -1;
        try {
            maxUser = Integer.parseInt(args[1]);
        } catch (Exception e) {
        }

        if (maxUser < 0) {
            player.sendMessage(Reference.PREFIX + "최대 추가 가능한 인원은 0명부터 설정 가능합니다.");
            return true;
        }

        if (maxUser < farm.getUsers().size()) {
            player.sendMessage(Reference.PREFIX + "[주의] 이미 현재 파티는 주인을 제외하고 " + farm.getUsers().size() + "명 입니다.");
            player.sendMessage(Reference.PREFIX + "[주의] 이미 파티에 포함된 유저는 추방되지 않습니다.");
        }
        farm.setMaxUser(maxUser);
        player.sendMessage(Reference.PREFIX + maxUser + "명으로 변경되었습니다.");
        return false;
    }
}
