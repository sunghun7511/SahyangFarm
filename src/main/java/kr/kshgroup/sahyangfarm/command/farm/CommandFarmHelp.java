package kr.kshgroup.sahyangfarm.command.farm;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandFarmHelp extends SFCommandExecutor {
    private final int PER_PAGE = 4;

    public CommandFarmHelp() {
        super("도움말", "help");
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        int page = 1;
        if (args.length != 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (Exception ignored) {
            }
        }

        List<Help> helps = Arrays.stream(Help.values())
                .filter(help -> !help.requiredOp || sender.isOp())
                .collect(Collectors.toList());

        int maxPage = helps.size() / PER_PAGE + (helps.size() % PER_PAGE != 0 ? 1 : 0);
        sender.sendMessage(Reference.PREFIX + "=-=-=-=[ 도움말 (" + page + " / " + maxPage + ")]=-=-=-=");
        for (int i = (page - 1) * PER_PAGE; i < Math.min(helps.size(), page * PER_PAGE); i++) {
            Help help = helps.get(i);
            sender.sendMessage(Reference.PREFIX + help.usage + (help.requiredOp ? " (OP 필요)" : ""));
            for (String line : help.description)
                sender.sendMessage(Reference.PREFIX + " - " + line);
        }
        return true;
    }

    private enum Help {
        FARM("/팜", "나의 팜으로 이동합니다.", "단, 나의 팜이 없는 경우 새로 생성합니다."),
        FARM_HELP("/팜 도움말", "명령어 도움말을 확인할 수 있습니다."),
        FARM_REMOVE("/팜 제거", "내 팜을 제거합니다.", "단, 파티에 아무도 없어야 합니다."),
        FARM_PARTY("파티", "파티 시스템과 관련된 명령어를 확인합니다."),
        FARM_PARTY_DETAIL("/팜 파티 확인", "내 팜의 파티를 확인할 수 있습니다."),
        FARM_INVITE("/팜 파티 초대 <닉네임>", "내 팜의 파티에 유저를 초대합니다."),
        FARM_INVITE_ACCEPT("/팜 수락", "파티 초대를 수락합니다."),
        FARM_INVITE_REJECT("/팜 거절", "파티 초대를 거절합니다."),
        FARM_PARTY_KICK("/팜 파티 추방 <닉네임>", "내 팜의 파티에서 유저를 추방합니다."),
        FARM_PARTY_LEAVE("/팜 파티 나가기", "소속된 파티에서 나갑니다.", "초대받은 경우만 사용이 가능합니다."),
        FARM_TRANSFER("/팜 파티 양도 <닉네임>", "내 팜의 소유권을 양도합니다."),
        FARM_OP_MOVE("/팜 이동 <닉네임>", true, "특정 유저의 팜으로 이동합니다."),
        FARM_OP_INCREASE("/팜 증설", true, "스크립트 연동용 명령어입니다.", "자신의 팜의 최대 파티 인원을 1명 증설합니다."),
        FARM_OP_SET_MAX_USER("/팜 파티 인원설정 <닉네임> <최대 인원>", true, "특정 유저의 최대 인원을 설정합니다.");

        private final boolean requiredOp;
        private final String usage;
        private final String[] description;

        Help(String usage, String... description) {
            this(usage, false, description);
        }

        Help(String usage, boolean requiredOp, String... description) {
            this.usage = usage;
            this.description = description;
            this.requiredOp = requiredOp;
        }
    }
}
