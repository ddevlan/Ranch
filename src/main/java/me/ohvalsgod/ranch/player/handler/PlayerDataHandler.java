package me.ohvalsgod.ranch.player.handler;

import lombok.Getter;
import me.ohvalsgod.ranch.Ranch;
import me.ohvalsgod.ranch.player.PlayerData;
import me.ohvalsgod.ranch.player.data.PlayerMiningData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class PlayerDataHandler {

    private Ranch ranch;
    private Set<PlayerData> data;
    private File dataFolder;

    public PlayerDataHandler(Ranch ranch) {
        this.ranch = ranch;
        data = new HashSet<PlayerData>();
        dataFolder = new File(ranch.getDataFolder() + "/data/");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public void saveData(PlayerData data) throws IOException {
        File dataFile = new File(dataFolder + "/" + data.getUuid().toString() + ".yml");

        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
        PlayerMiningData mdata = data.getMiningData();

        config.set("ores.diamond", mdata.getDiamonds());
        config.set("ores.gold", mdata.getGold());
        config.set("ores.iron", mdata.getIron());
        config.set("ores.lapis", mdata.getLapis());
        config.set("ores.redstone", mdata.getRedstone());
        config.set("ores.coal", mdata.getCoal());
        config.set("ores.quartz", mdata.getQuartz());
        config.set("ores.stone", mdata.getStone());

        config.save(dataFile);
    }

    public void loadData(UUID uuid) {
        if (ranch.getDataFetcher().getDataFromUUID(uuid) == null) {
            PlayerData data = new PlayerData(uuid);
            data.setLoaded(true);
            this.data.add(data);

            File dataFile = new File(dataFolder + "/" + data.getUuid().toString() + ".yml");

            if (!dataFile.exists()) {
                return;
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            PlayerMiningData mdata = data.getMiningData();

            mdata.setDiamonds(config.getInt("ores.diamond"));
            mdata.setGold(config.getInt("ores.gold"));
            mdata.setIron(config.getInt("ores.iron"));
            mdata.setLapis(config.getInt("ores.lapis"));
            mdata.setRedstone(config.getInt("ores.redstone"));
            mdata.setCoal(config.getInt("ores.coal"));
            mdata.setQuartz(config.getInt("ores.quartz"));
            mdata.setStone(config.getInt("ores.stone"));
        }
    }

    public void loadLocalData() {
        for (File file : dataFolder.listFiles()) {
            if (file.getName().contains(".yml")) {
                UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));
                loadData(uuid);
            }
        }
    }

    public void saveLocalData() {
        for (PlayerData data : this.data) {
            try {
                saveData(data);
            } catch (IOException e) {
                Ranch.getInstance().getLogger().severe("Could not save data for: " + data.getUuid());
            }
        }
    }

}
