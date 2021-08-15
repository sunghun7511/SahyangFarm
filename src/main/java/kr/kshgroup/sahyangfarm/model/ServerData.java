package kr.kshgroup.sahyangfarm.model;

import java.util.HashMap;
import java.util.Map;

public class ServerData implements SFStorableData {
    public ServerData(Map<String, Object> map) {

    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
