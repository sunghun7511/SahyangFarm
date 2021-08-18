package kr.kshgroup.sahyangfarm.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class WorldlessLocation implements SFStorableData {
    private final String worldName;

    private double x;
    private double y;
    private double z;

    private float pitch;
    private float yaw;

    public WorldlessLocation(Location location) {
        worldName = location.getWorld().getName();
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        pitch = location.getPitch();
        yaw = location.getYaw();
    }

    public WorldlessLocation(Map<String, Object> map) {
        worldName = (String) map.get("world");
        x = (double) map.get("x");
        y = (double) map.get("y");
        z = (double) map.get("z");
        pitch = (float) map.get("pitch");
        yaw = (float) map.get("yaw");
    }

    public Location getLocation() {
        return new Location(getWorld(), x, y, z, yaw, pitch);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", worldName);
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        map.put("yaw", yaw);
        map.put("pitch", pitch);
        return map;
    }
}
