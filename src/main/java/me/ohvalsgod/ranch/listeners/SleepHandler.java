package me.ohvalsgod.ranch.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

public class SleepHandler implements Listener {

    private Set<String> inbed;

    public SleepHandler() {
        inbed = new HashSet<String>();
    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {
        if (Bukkit.getWorld("smol_pp").getTime() > 13000) {
            inbed.add(event.getPlayer().getName());

            if (inbed.size() >= neededToChangeDay()) {
                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Time changed to" + ChatColor.WHITE + " day" + ChatColor.YELLOW + ".");
                Bukkit.getServer().getWorld("smol_pp").setTime(0);
                event.setCancelled(true);
                event.getPlayer().setBedSpawnLocation(event.getPlayer().getLocation());

                for (String string : inbed) {
                    Player player = Bukkit.getPlayer(string);
                    player.setBedSpawnLocation(player.getLocation());
                }
            } else {
                Bukkit.getServer().broadcastMessage(event.getPlayer().getName() + " " + ChatColor.YELLOW + "would like to sleep. " + ChatColor.WHITE + (neededToChangeDay() - inbed.size()) + ChatColor.YELLOW + " more player(s) needed.");
            }
        }
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent event) {
        inbed.remove(event.getPlayer().getName());
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        if (inbed.contains(event.getPlayer().getName())) {
            inbed.remove(event.getPlayer().getName());
        }
    }

    private int neededToChangeDay() {
        return (Bukkit.getOnlinePlayers().size() / 2);
    }

}
