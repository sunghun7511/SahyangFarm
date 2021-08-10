package kr.kshgroup.sahyangfarm.model;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Farm {
    private final OfflinePlayer owner;
    private final List<UUID> users = new ArrayList<>();

    private int maxUser;

    private final Location center;

    public Farm(OfflinePlayer owner, Location center) {
        this.owner = owner;
        this.center = center;
    }

    public String getOwnerName() {
        return this.owner.getName();
    }

    public OfflinePlayer getOwnerPlayer() {
        return this.owner;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public Location getCenter() {
        return getCenter(true);
    }

    public Location getCenter(boolean clone) {
        return clone ? center.clone() : center;
    }
}
