package me.ohvalsgod.ranch.player.handler;

import me.ohvalsgod.ranch.player.PlayerData;

import java.util.UUID;

public class PlayerDataFetcher {

    private PlayerDataHandler handler;

    public PlayerDataFetcher(PlayerDataHandler handler) {
        this.handler = handler;
    }

    public PlayerData getDataFromUUID(UUID uuid) {
        for (PlayerData data : handler.getData()) {
            if (data.getUuid().equals(uuid)) {
                return data;
            }
        }
        return null;
    }

}
