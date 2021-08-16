package kr.kshgroup.sahyangfarm.story.farm;

import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.data.DataManager;
import kr.kshgroup.sahyangfarm.model.FarmOffset;
import kr.kshgroup.sahyangfarm.model.ServerData;
import kr.kshgroup.sahyangfarm.story.SFStoryBase;

public class StoryFarmOffset implements SFStoryBase {
    private final DataManager dataManager;

    public StoryFarmOffset() {
        dataManager = (DataManager) SahyangFarm.getManager(DataManager.class);
    }

    public FarmOffset getAvailableOffset() {
        ServerData serverData = dataManager.getServerData();
        if (serverData.getReusableOffset().size() > 0)
            return serverData.getReusableOffset().remove(0);

        int offset = serverData.getLastOffset() + 1;
        serverData.setLastOffset(offset);

        return new FarmOffset(offset / 10, offset % 10);
    }

    public void freeOffset(FarmOffset offset) {
        ServerData serverData = dataManager.getServerData();
        serverData.getReusableOffset().add(offset);
    }
}
