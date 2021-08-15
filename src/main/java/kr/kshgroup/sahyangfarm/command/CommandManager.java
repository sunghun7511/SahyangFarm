package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.ManagerBase;
import org.bukkit.Bukkit;

public class CommandManager extends ManagerBase {
    private final CommandFarm mainCommand;

    public CommandManager() {
        mainCommand = new CommandFarm();
    }

    public CommandFarm getMainCommand() {
        return mainCommand;
    }

    @Override
    public void onRun() {
//        mainCommand.registerCommand();

        Bukkit.getPluginCommand("팜").setExecutor(mainCommand);
        Bukkit.getPluginCommand("farm").setExecutor(mainCommand);
    }
}
