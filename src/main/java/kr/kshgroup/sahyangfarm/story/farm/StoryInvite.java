package kr.kshgroup.sahyangfarm.story.farm;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.model.Farm;
import kr.kshgroup.sahyangfarm.story.SFStoryBase;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;

public class StoryInvite implements SFStoryBase {
    private final StoryManager storyManager;
    private final HashMap<Player, Invitation> invitations = new HashMap<>();

    public StoryInvite() {
        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    public boolean sendInvite(Player player, Player target, Farm farm) {
        if (!isExpired(target)) {
            player.sendMessage(Reference.PREFIX + "해당 유저는 아직 응답하지 않은 초대가 있습니다.");
            player.sendMessage(Reference.PREFIX + "잠시만 기다려주세요.");
            return false;
        }

        if (farm.getMaxUser() <= farm.getUsers().size()) {
            player.sendMessage(Reference.PREFIX + "파티에 초대할 수 있는 최대 인원에 도달했습니다.");
            return false;
        }

        invitations.put(target, new Invitation(farm, System.currentTimeMillis()));

        target.sendMessage(Reference.PREFIX + farm.getOwnerName() + "님의 팜에 초대되었습니다.");
        target.sendMessage(Reference.PREFIX + "5분 내에 '/팜 수락' 또는 '/팜 거절' 으로 응답해주세요.");
        return true;
    }

    public void replyInvite(Player player, boolean accept) {
        if (isExpired(player)) {
            invitations.remove(player);
            player.sendMessage(Reference.PREFIX + "받은 초대가 없거나 만료되었습니다.");
            return;
        }
        Invitation invitation = invitations.remove(player);

        Farm farm = invitation.farm;
        if (!accept) {
            player.sendMessage(Reference.PREFIX + "초대를 거절했습니다.");
            if (farm.getOwnerPlayer().isOnline()) {
                farm.getOwnerPlayer().getPlayer().sendMessage(Reference.PREFIX + player.getName() + "님이 초대를 거절했습니다.");
            }
            return;
        }

        if (farm.getUsers().size() >= farm.getMaxUser()) {
            player.sendMessage(Reference.PREFIX + "참가하려는 팜의 인원이 가득 찼습니다.");
            return;
        }

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        if (Objects.nonNull(storyFarm.getFarm(player))) {
            player.sendMessage(Reference.PREFIX + "이미 팜에 가입된 상태입니다.");
            return;
        }

        farm.getUsers().add(player.getUniqueId());
        player.sendMessage(Reference.PREFIX + "팜 파티에 가입했습니다!");
        if (farm.getOwnerPlayer().isOnline()) {
            farm.getOwnerPlayer().getPlayer().sendMessage(Reference.PREFIX + player.getName() + "님이 파티에 가입했습니다!");
        }
    }

    private boolean isExpired(Player player) {
        return !invitations.containsKey(player) || (System.currentTimeMillis() - invitations.get(player).issued) < 300000L;
    }

    public static class Invitation {
        private final Farm farm;
        private final long issued;

        public Invitation(Farm farm, long issued) {
            this.farm = farm;
            this.issued = issued;
        }
    }
}
