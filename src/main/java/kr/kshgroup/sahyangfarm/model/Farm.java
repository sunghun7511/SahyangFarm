package kr.kshgroup.sahyangfarm.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class Farm implements SFStorableData {
    private OfflinePlayer owner;
    private final List<UUID> users = new ArrayList<>();
    private final List<UUID> parttimes = new ArrayList<>();

    private final FarmOffset farmOffset;
    private final WorldlessLocation center;

    private WorldlessLocation spawn;
    private int maxUser;
    private boolean allowMove;

    public Farm(OfflinePlayer owner, Location center, FarmOffset farmOffset, int maxUser) {
        this.owner = owner;
        this.farmOffset = farmOffset;
        this.center = new WorldlessLocation(center.clone());
        this.spawn = new WorldlessLocation(center.clone());
        this.maxUser = maxUser;
        this.allowMove = true;
    }

    public Farm(Map<String, Object> map) {
        this.owner = Bukkit.getOfflinePlayer(UUID.fromString((String) map.get("owner")));
        this.farmOffset = (FarmOffset) map.get("offset");
        this.center = ((WorldlessLocation) map.get("center"));
        this.spawn = ((WorldlessLocation) map.getOrDefault("spawn", center.clone()));
        this.maxUser = (int) map.get("max");
        this.allowMove = (boolean) map.getOrDefault("allow-move", true);

        this.users.addAll(((List<String>) map.getOrDefault("users", new ArrayList<>())).stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));
        this.parttimes.addAll(((List<String>) map.getOrDefault("part-time", new ArrayList<>())).stream()
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

    public List<UUID> getPartTimes() {
        return parttimes;
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
        return this.center.getLocation();
    }

    public Location getSpawn() {
        return spawn.getLocation();
    }

    public void setSpawn(Location spawn) {
        this.spawn = new WorldlessLocation(spawn);
    }

    public void setAllowMove(boolean allowMove) {
        this.allowMove = allowMove;
    }

    public boolean isAllowMove() {
        return allowMove;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", this.owner.getUniqueId().toString());
        map.put("offset", this.farmOffset);
        map.put("center", this.center);
        map.put("spawn", this.spawn);
        map.put("max", this.maxUser);
        map.put("allow-move", this.allowMove);
        map.put("users", this.users.stream().map(UUID::toString).collect(Collectors.toList()));
        map.put("part-time", this.parttimes.stream().map(UUID::toString).collect(Collectors.toList()));
        return map;
    }
}
