package kr.kshgroup.sahyangfarm.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class Farm implements SFStorableData {
    private OfflinePlayer owner;
    private final List<UUID> users = new ArrayList<>();

    private int maxUser;

    private final FarmOffset farmOffset;
    private final Location center;

    public Farm(OfflinePlayer owner, Location center, FarmOffset farmOffset, int maxUser) {
        this.owner = owner;
        this.farmOffset = farmOffset;
        this.center = center;
        this.maxUser = maxUser;
    }

    public Farm(Map<String, Object> map) {
        this.owner = Bukkit.getOfflinePlayer(UUID.fromString((String) map.get("owner")));
        this.farmOffset = (FarmOffset) map.get("offset");
        this.center = (Location) map.get("center");
        this.maxUser = (int) map.get("max");

        this.users.addAll(((List<String>) map.getOrDefault("users", new ArrayList<>())).stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));
    }

    public String getOwnerName() {
        return this.owner.getName();
    }

    public OfflinePlayer getOwnerPlayer() {
        return this.owner;
    }

    public UUID getOwner() {
        return this.owner.getUniqueId();
    }

    public void setOwner(OfflinePlayer owner) {
        this.owner = owner;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public boolean isIn(OfflinePlayer player) {
        return getOwner().equals(player.getUniqueId()) || this.users.contains(player.getUniqueId());
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public FarmOffset getFarmOffset() {
        return farmOffset;
    }

    public Location getCenter() {
        return getCenter(true);
    }

    public Location getCenter(boolean clone) {
        return clone ? center.clone() : center;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", this.owner.getUniqueId().toString());
        map.put("offset", this.farmOffset);
        map.put("center", this.center);
        map.put("max", this.maxUser);
        map.put("users", this.users.stream().map(UUID::toString).collect(Collectors.toList()));
        return map;
    }
}
