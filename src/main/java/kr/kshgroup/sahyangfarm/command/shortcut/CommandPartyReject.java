package kr.kshgroup.sahyangfarm.command.shortcut;

import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import kr.kshgroup.sahyangfarm.story.farm.StoryInvite;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPartyReject extends SFCommandExecutor {
    private final StoryManager storyManager;

    public CommandPartyReject() {
        super("거절", "reject");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

        StoryInvite storyInvite = storyManager.getStory(StoryInvite.class);
        storyInvite.replyInvite(player, true);
        return true;
    }
}
