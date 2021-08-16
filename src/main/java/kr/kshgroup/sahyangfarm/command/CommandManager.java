package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.ManagerBase;
import kr.kshgroup.sahyangfarm.command.admin.CommandForceMove;
import kr.kshgroup.sahyangfarm.command.admin.CommandMaxUserIncrease;
import kr.kshgroup.sahyangfarm.command.farm.CommandFarmRemove;
import kr.kshgroup.sahyangfarm.command.party.CommandParty;
import kr.kshgroup.sahyangfarm.command.shortcut.CommandPartyAccept;
import kr.kshgroup.sahyangfarm.command.shortcut.CommandPartyReject;
import org.bukkit.Bukkit;

public class CommandManager extends ManagerBase {
    private CommandFarm mainCommand;

    public CommandFarm getMainCommand() {
        return mainCommand;
    }

    @Override
    public void onRun() {
        mainCommand = new CommandFarm();
        mainCommand.registerCommand(new CommandForceMove());
        mainCommand.registerCommand(new CommandMaxUserIncrease());

        mainCommand.registerCommand(new CommandFarmRemove());
        mainCommand.registerCommand(new CommandParty());

        mainCommand.registerCommand(new CommandPartyAccept());
        mainCommand.registerCommand(new CommandPartyReject());

        Bukkit.getPluginCommand("팜").setExecutor(mainCommand);
        Bukkit.getPluginCommand("farm").setExecutor(mainCommand);
    }
}
