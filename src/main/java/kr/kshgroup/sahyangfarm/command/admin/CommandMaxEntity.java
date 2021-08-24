package kr.kshgroup.sahyangfarm.command.admin;

import kr.kshgroup.sahyangfarm.Reference;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.command.SFCommandExecutor;
import kr.kshgroup.sahyangfarm.data.DataManager;
import kr.kshgroup.sahyangfarm.model.ServerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMaxEntity extends SFCommandExecutor {
    private final DataManager dataManager;

    public CommandMaxEntity() {
        super("엔티티제한", "maxentity", "entity");

        dataManager = (DataManager) SahyangFarm.getManager(DataManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (isNotPlayer(sender)) return true;
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(Reference.PREFIX + "이 명령어를 사용할 권한이 없습니다.");
            return true;
        }

        ServerData serverData = dataManager.getServerData();
        if (args.length == 0) {
            player.sendMessage(Reference.PREFIX + "현재 최대 엔티티 제한은 " + serverData.getMaxEntityInFarm() + "마리입니다.");
            return true;
        }

        int maxEntityInFarm = -1;
        try {
            maxEntityInFarm = Integer.parseInt(args[0]);
        } catch (Exception ignored) {
        }

        if (maxEntityInFarm < 0) {
            player.sendMessage(Reference.PREFIX + "최대 엔티티 수는 0보다 작을 수 없습니다.");
            return true;
        }

        serverData.setMaxEntityInFarm(maxEntityInFarm);
        player.sendMessage(Reference.PREFIX + "최대 엔티티 수가 " + maxEntityInFarm + "마리로 변경되었습니다.");
        return false;
    }
}
