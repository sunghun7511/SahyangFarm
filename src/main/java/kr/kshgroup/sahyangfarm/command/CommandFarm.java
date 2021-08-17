package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.story.farm.StoryFarm;
import kr.kshgroup.sahyangfarm.story.StoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandFarm extends SFCommandGroup implements CommandExecutor {
    private final StoryManager storyManager;

    public CommandFarm() {
        super("팜", "farm");

        storyManager = (StoryManager) SahyangFarm.getManager(StoryManager.class);
    }

    @Override
    public boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result) {
        if (Objects.isNull(executed)) {
            sender.sendMessage(Reference.PREFIX + "알 수 없는 명령어입니다.");
            sender.sendMessage(Reference.PREFIX + "'/팜 도움말 <페이지>' 명령어를 통해 도움말을 확인하세요.");
        }
        return true;
    }

    @Override
    public boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception) {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            return super.onCommand(sender, label, args);
        }

        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

        StoryFarm storyFarm = storyManager.getStory(StoryFarm.class);
        storyFarm.teleportFarm(player);
        return true;
    }
}
