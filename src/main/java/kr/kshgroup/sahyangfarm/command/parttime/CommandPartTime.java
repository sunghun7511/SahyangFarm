package kr.kshgroup.sahyangfarm.command.parttime;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.command.SFCommand;
import kr.kshgroup.sahyangfarm.command.SFCommandGroup;
import kr.kshgroup.sahyangfarm.command.admin.CommandMaxUserSet;
import kr.kshgroup.sahyangfarm.command.party.*;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class CommandPartTime extends SFCommandGroup {
    public CommandPartTime() {
        super("알바", "part", "parttime");

        registerCommand(new CommandPartTimeAdd());
        registerCommand(new CommandPartTimeRemove());
    }

    @Override
    public boolean onCommandAfter(CommandSender sender, String label, String[] args, SFCommand executed, boolean result) {
        if (Objects.isNull(executed)) {
            sender.sendMessage(Reference.PREFIX + "팜 알바와 관련된 명령어입니다.");
            sender.sendMessage(Reference.PREFIX + "'/팜 도움말 <페이지>' 명령어를 통해 도움말을 확인하세요.");
        }
        return true;
    }

    @Override
    public boolean onCommandError(CommandSender sender, String label, String[] args, SFCommand executor, Exception exception) {
        return false;
    }
}
