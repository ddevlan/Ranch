package me.ohvalsgod.ranch.player;

import lombok.Getter;
import lombok.Setter;
import me.ohvalsgod.ranch.player.data.PlayerMiningData;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class PlayerData {

    private UUID uuid;
    private PlayerMiningData miningData;
    @Setter private boolean loaded;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        miningData = new PlayerMiningData();
        loaded = false;
    }

    public PlayerData(Player player) {
        this(player.getUniqueId());
    }

}
