package me.ohvalsgod.ranch.listeners;

import me.ohvalsgod.ranch.Ranch;
import me.ohvalsgod.ranch.player.PlayerData;
import me.ohvalsgod.ranch.player.data.PlayerMiningData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MiningHandler implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        PlayerData data = Ranch.getInstance().getDataFetcher().getDataFromUUID(event.getPlayer().getUniqueId());
        PlayerMiningData mdata = data.getMiningData();
        Block block = event.getBlock();

        if (block.getMetadata("NOT_NATURALLY_GENERATED").isEmpty()) {

            if (block.getType() == Material.STONE) {
                mdata.setStone(mdata.getStone() + 1);
            } else if (block.getType() == Material.GOLD_ORE) {
                mdata.setGold(mdata.getGold() + 1);
            } else if (block.getType() == Material.IRON_ORE) {
                mdata.setIron(mdata.getIron() + 1);
            } else if (block.getType() == Material.LAPIS_ORE) {
                mdata.setLapis(mdata.getLapis() + 1);
            } else if (block.getType() == Material.REDSTONE_ORE) {
                mdata.setRedstone(mdata.getRedstone() + 1);
            } else if (block.getType() == Material.COAL_ORE) {
                mdata.setCoal(mdata.getCoal() + 1);
            } else if (block.getType() == Material.NETHER_QUARTZ_ORE) {
                mdata.setQuartz(mdata.getQuartz() + 1);
            } else if (block.getType() == Material.DIAMOND_ORE) {
                if (block.getMetadata("DIAMOND_FOUND").isEmpty()) {
                    int found = diamondsNear(block.getLocation());
                    Bukkit.broadcastMessage("[FD] " + ChatColor.AQUA + event.getPlayer().getName() + " found " + found + " diamonds.");
                    mdata.setDiamonds(mdata.getDiamonds() + found);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        block.setMetadata("NOT_NATURALLY_GENERATED", new FixedMetadataValue(Ranch.getInstance(), "this block was not naturally generated"));
    }

    private int diamondsNear(Location location) {
        int amount = 0;

        if (location.getBlock().getType() == Material.DIAMOND_ORE) {

            for (int x = -3; x < 3; x++) {
                for (int y = -3; y < 3; y++) {
                    for (int z = -3; z < 3; z++) {
                        Location location1 = location.clone().add(x, y, z);
                        if (location1.getBlock() != null) {
                            if (location1.getBlock().getType() == Material.DIAMOND_ORE) {
                                Block block = location1.getBlock();
                                block.setMetadata("DIAMOND_FOUND", new FixedMetadataValue(Ranch.getInstance(), "this ore has already been found"));

                                amount++;
                            }
                        }
                    }
                }
            }
        }
        return amount;
    }

}
