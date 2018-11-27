package me.ohvalsgod.ranch.listeners;

import me.ohvalsgod.ranch.Ranch;
import me.ohvalsgod.ranch.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer().getName() + ChatColor.YELLOW + " has joined the server!");

        if (Ranch.getInstance().getDataFetcher().getDataFromUUID(event.getPlayer().getUniqueId()) == null) {
            Ranch.getInstance().getDataHandler().getData().add(new PlayerData(event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(event.getPlayer().getName() + ChatColor.YELLOW + " has left the server!");

        try {
            Ranch.getInstance().getDataHandler().saveData(Ranch.getInstance().getDataFetcher().getDataFromUUID(event.getPlayer().getUniqueId()));
        } catch (IOException e) {
            Ranch.getInstance().getLogger().severe("Could not save data for: " + event.getPlayer().getName() + " with exception: " + e);
        }
    }

}
