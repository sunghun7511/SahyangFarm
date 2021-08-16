package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.ManagerBase;
import org.bukkit.Bukkit;

public class CommandManager extends ManagerBase {
    private CommandFarm mainCommand;

    public CommandFarm getMainCommand() {
        return mainCommand;
    }

    @Override
    public void onRun() {
        mainCommand = new CommandFarm();
//        mainCommand.registerCommand();

        Bukkit.getPluginCommand("팜").setExecutor(mainCommand);
        Bukkit.getPluginCommand("farm").setExecutor(mainCommand);
    }
}
