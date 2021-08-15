package kr.kshgroup.sahyangfarm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandFarm extends SFCommandGroup implements CommandExecutor {
    public CommandFarm() {
        super("팜", "farm");
    }

    @Override
    public boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result) {
        if (executed != null) {
            return true;
        }
        sendHelpMessage(sender, label);
        return true;
    }

    @Override
    public boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception) {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.onCommand(sender, label, args);
    }
}

