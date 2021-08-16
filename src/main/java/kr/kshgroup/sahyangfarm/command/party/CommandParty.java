package kr.kshgroup.sahyangfarm.command.party;

import kr.kshgroup.sahyangfarm.command.SFCommand;
import kr.kshgroup.sahyangfarm.command.SFCommandGroup;
import kr.kshgroup.sahyangfarm.command.admin.CommandMaxUserSet;
import org.bukkit.command.CommandSender;

public class CommandParty extends SFCommandGroup {
    public CommandParty() {
        super("파티", "party", "p");

        // 관리자용 명령어
        registerCommand(new CommandMaxUserSet());

        // 유저용 명령어
        registerCommand(new CommandPartyDetail());
    }

    @Override
    public boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result) {
        return false;
    }

    @Override
    public boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception) {
        return false;
    }
}
