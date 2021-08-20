package kr.kshgroup.sahyangfarm.story.data;

import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.data.DataManager;
import kr.kshgroup.sahyangfarm.story.SFStoryBase;
import org.bukkit.Bukkit;

public class StoryAutoSaveData implements SFStoryBase {
    private final DataManager dataManager;

    public StoryAutoSaveData() {
        dataManager = (DataManager) SahyangFarm.getManager(DataManager.class);
    }

    @Override
    public void onRun() {
        long delay = 10 * 60 * 20;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SahyangFarm.getInstance(), dataManager::saveData, delay, delay);
    }
}
