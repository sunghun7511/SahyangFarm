package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.Reference;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class SFCommandGroup extends SFCommandExecutor {
    public SFCommandGroup(String label, String... aliases) {
        super(label, aliases);
    }

    private final List<SFCommand> commandList = new ArrayList<>();

    protected final void registerCommand(SFCommand command) {
        if (Objects.isNull(command)) {
            return;
        }
        if (this.commandList.contains(command)) {
            return;
        }
        this.commandList.add(command);
    }

    protected final void unregisterCommand(SFCommand command) {
        if (Objects.isNull(command)) {
            return;
        }
        if (!this.commandList.contains(command)) {
            return;
        }
        this.commandList.remove(command);
    }

    protected final List<SFCommand> getCommandList() {
        return this.commandList;
    }

    protected void sendHelpMessage(CommandSender sender, String label) {
        sender.sendMessage(Reference.PREFIX + "=-=-=-=[ 명령어 목록 ]=-=-=-=");
        commandList.forEach(command -> sender.sendMessage(Reference.PREFIX + "/" + label + " " + command.getLabel()));
        sender.sendMessage(Reference.PREFIX + "=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    @Override
    public final boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!onCommandBefore(sender, label, args)) {
            return false;
        }

        SFCommand executed = null;
        boolean result = true;
        if (args.length != 0) {
            for (SFCommand command : getCommandList()) {
                if (!command.hasResponsibility(args[0])) {
                    continue;
                }

                String newLabel = label + " " + args[0];
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                try {
                    result = command.onCommand(sender, newLabel, newArgs);
                } catch (Exception ex) {
                    return onCommandError(sender, label, args, command, ex);
                }
                executed = command;
            }
        }

        return onCommandAfter(sender, label, args, executed, result);
    }

    public boolean onCommandBefore(CommandSender sender, String label, String[] args) {
        return true;
    }

    public abstract boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result);

    public abstract boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception);
}
