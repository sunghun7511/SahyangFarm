package kr.kshgroup.sahyangfarm.data;

import kr.kshgroup.sahyangfarm.ManagerBase;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.model.ServerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager extends ManagerBase {
    private final File serverDataFile;

    private ServerData serverData;

    public DataManager() throws IOException {
        if (!SahyangFarm.getInstance().getDataFolder().exists())
            SahyangFarm.getInstance().getDataFolder().mkdirs();

        serverDataFile = new File(SahyangFarm.getInstance().getDataFolder(), "server-data.yml");
        if (!serverDataFile.exists()) {
            serverDataFile.createNewFile();
        }
    }

    @Override
    public void onEnable() {
        this.loadData();
    }

    @Override
    public void onDisable() {
        this.saveData();
    }

    public void saveData() {
        FileConfiguration config = new YamlConfiguration();
        serverData.serialize().forEach(config::set);
        try {
            config.save(serverDataFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void loadData() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(serverDataFile);
        this.serverData = new ServerData(config.getValues(true));
        saveData();
    }

    public ServerData getServerData() {
        return this.serverData;
    }
}
