package kr.kshgroup.sahyangfarm.command;

import kr.kshgroup.sahyangfarm.Reference;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SFCommandExecutor implements SFCommand {
    private final String label;
    private final List<String> aliases = new ArrayList<>();

    public SFCommandExecutor(String label, String... aliases) {
        this.label = label.toLowerCase();
        for (String alias : aliases) {
            this.aliases.add(alias.toLowerCase());
        }
    }

    @Override
    public final String getLabel() {
        return this.label;
    }

    @Override
    public final List<String> getAliasList() {
        return aliases;
    }

    protected boolean isNotPlayer(CommandSender sender) {
        if (sender instanceof Player)
            return false;

        sender.sendMessage(Reference.PREFIX + "플레이어만 입력 가능한 명령어예요.");
        return true;
    }

    @Override
    public boolean hasResponsibility(String arg) {
        if (Objects.isNull(arg)) {
            return false;
        }
        if (label.equalsIgnoreCase(arg)) {
            return true;
        }
        if (aliases.contains(arg.toLowerCase())) {
            return true;
        }
        return false;
    }
}
