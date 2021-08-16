package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFarm extends SFCommandGroup implements CommandExecutor {
    private final StoryManager storyManager;

    public CommandFarm() {
        super("팜", "farm");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result) {
        if (executed != null) {
            return true;
        }
        return true;
    }

    @Override
    public boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception) {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        storyFarm.teleportFarm(player);
        return true;
    }
}
