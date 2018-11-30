package me.ohvalsgod.ranch.listeners;

import me.ohvalsgod.ranch.Ranch;
import me.ohvalsgod.ranch.player.PlayerData;
import me.ohvalsgod.ranch.player.data.PlayerMiningData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData data = Ranch.getInstance().getDataFetcher().getDataFromUUID(player.getUniqueId());
        PlayerMiningData mdata = data.getMiningData();

        if (player.isSneaking()) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.DARK_GRAY + "*******" + ChatColor.GRAY +  " Mining Stats " + ChatColor.DARK_GRAY + "*******");
                    player.sendMessage(ChatColor.AQUA + "Diamonds" + ChatColor.WHITE + ": " + mdata.getDiamonds());
                    player.sendMessage(ChatColor.GOLD + "Gold" + ChatColor.WHITE + ": " + mdata.getGold());
                    player.sendMessage(ChatColor.GRAY + "Iron" + ChatColor.WHITE + ": " + mdata.getIron());
                    player.sendMessage(ChatColor.BLUE + "Lapis" + ChatColor.WHITE + ": " + mdata.getLapis());
                    player.sendMessage(ChatColor.RED + "Redstone" + ChatColor.WHITE + ": " + mdata.getRedstone());
                    player.sendMessage(ChatColor.GRAY + "Stone" + ChatColor.WHITE + ": " + mdata.getStone());
                }
            }
        }

    }

}
