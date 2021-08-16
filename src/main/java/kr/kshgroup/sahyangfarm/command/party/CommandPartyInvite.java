package kr.kshgroup.sahyangfarm.command.party;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import kr.kshgroup.sahyangfarm.story.farm.StoryInvite;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandPartyInvite extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartyInvite() {
        super("초대", "invite");

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
        Player target = Bukkit.getPlayerExact(nickname);
        if (Objects.isNull(target)) {
            player.sendMessage(Reference.PREFIX + "존재하지 않는 플레이어이거나 온라인 상태가 아닙니다.");
            return true;
        }

        if (farm.getUsers().contains(target.getUniqueId())) {
            player.sendMessage(Reference.PREFIX + "이미 당신의 팜 멤버입니다.");
            return true;
        }

        if (Objects.nonNull(storyFarm.getFarm(target))) {
            player.sendMessage(Reference.PREFIX + "이미 팜에 가입되어 있는 유저입니다.");
            return true;
        }

        StoryInvite storyInvite = storyManager.getStory(StoryInvite.class);
        if (!storyInvite.sendInvite(player, target, farm)) {
            return true;
        }

        player.sendMessage(Reference.PREFIX + target.getName() + "님께 파티 초대 요청을 보냈습니다.");
        return true;
    }
}
