package kr.kshgroup.sahyangfarm.command.party;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.command.SFCommand;
import kr.kshgroup.sahyangfarm.command.SFCommandGroup;
import kr.kshgroup.sahyangfarm.command.admin.CommandMaxUserSet;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class CommandParty extends SFCommandGroup {
    public CommandParty() {
        super("파티", "party", "p");

        // 관리자용 명령어
        registerCommand(new CommandMaxUserSet());

        // 유저용 명령어
        registerCommand(new CommandPartyDetail());
        registerCommand(new CommandPartyInvite());
        registerCommand(new CommandPartyKick());
        registerCommand(new CommandPartyLeave());
        registerCommand(new CommandPartyTransfer());
    }

    @Override
    public boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result) {
        if (Objects.isNull(executed)) {
            sender.sendMessage(Reference.PREFIX + "알 수 없는 명령어입니다.");
            sender.sendMessage(Reference.PREFIX + "'/팜 도움말' 명령어를 통해 도움말을 확인하세요.");
        }
        return true;
    }

    @Override
    public boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception) {
        return false;
    }
}
