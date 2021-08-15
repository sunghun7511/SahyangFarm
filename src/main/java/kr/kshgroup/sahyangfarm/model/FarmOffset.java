package kr.kshgroup.sahyangfarm.model;

import java.util.HashMap;
import java.util.Map;

public class FarmOffset implements SFStorableData {
    private final int x;
    private final int z;

    public FarmOffset(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public FarmOffset(Map<String, Object> map) {
        x = (int) map.getOrDefault("x", 0);
        z = (int) map.getOrDefault("z", 0);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", this.x);
        map.put("z", this.z);
        return map;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
