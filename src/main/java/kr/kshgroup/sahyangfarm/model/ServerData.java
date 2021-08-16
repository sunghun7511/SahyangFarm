package kr.kshgroup.sahyangfarm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerData implements SFStorableData {
    private final List<Farm> farms = new ArrayList<>();

    private final List<FarmOffset> reusableOffset = new ArrayList<>();
    private int lastOffset;

    public ServerData(Map<String, Object> map) {
        farms.addAll((List<Farm>) map.getOrDefault("farms", new ArrayList<>()));

        reusableOffset.addAll((List<FarmOffset>) map.getOrDefault("offset.reusable", new ArrayList<>()));
        lastOffset = (int) map.getOrDefault("offset.last", -1);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("farms", farms);
        map.put("offset.reusable", reusableOffset);
        map.put("offset.last", lastOffset);
        return map;
    }

    public List<Farm> getFarms() {
        return farms;
    }

    public List<FarmOffset> getReusableOffset() {
        return reusableOffset;
    }

    public int getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(int lastOffset) {
        this.lastOffset = lastOffset;
    }
}
