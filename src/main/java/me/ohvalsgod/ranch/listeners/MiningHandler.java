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
                return;
            }

            int found = oresNear(block.getLocation());

            if (block.getType() == Material.DIAMOND_ORE) {
                if (block.getMetadata("DIAMOND_FOUND").isEmpty()) {
                    Bukkit.broadcastMessage("[FD] " + ChatColor.AQUA + event.getPlayer().getName() + " found " + found + " diamonds.");
                    mdata.setDiamonds(mdata.getDiamonds() + found);
                }
            } else if (block.getType() == Material.GOLD_ORE) {
                if (block.getMetadata("GOLD_FOUND").isEmpty()) {
                    mdata.setGold(mdata.getGold() + found);
                }
            } else if (block.getType() == Material.IRON_ORE) {
                if (block.getMetadata("IRON_FOUND").isEmpty()) {
                    mdata.setIron(mdata.getIron() + found);
                }
            } else if (block.getType() == Material.LAPIS_ORE) {
                if (block.getMetadata("LAPIS_FOUND").isEmpty()) {
                    mdata.setLapis(mdata.getLapis() + found);
                }
            } else if (block.getType() == Material.REDSTONE_ORE) {
                if (block.getMetadata("REDSTONE_FOUND").isEmpty()) {
                    mdata.setRedstone(mdata.getRedstone() + found);
                }
            } else if (block.getType() == Material.COAL_ORE) {
                if (block.getMetadata("COAL_FOUND").isEmpty()) {
                    mdata.setCoal(mdata.getCoal() + found);
                }
            } else if (block.getType() == Material.NETHER_QUARTZ_ORE) {
                if (block.getMetadata("NETHER_FOUND").isEmpty()) {
                    mdata.setQuartz(mdata.getQuartz() + found);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        block.setMetadata("NOT_NATURALLY_GENERATED", new FixedMetadataValue(Ranch.getInstance(), "this block was not naturally generated"));
    }

    private int oresNear(Location location) {
        int amount = 0;

        if (location.getBlock().getType().name().contains("_ORE")) {
            String ore = location.getBlock().getType().name().split("_")[0];

            for (int x = -3; x < 3; x++) {
                for (int y = -3; y < 3; y++) {
                    for (int z = -3; z < 3; z++) {
                        Location location1 = location.clone().add(x, y, z);
                        if (location1.getBlock() != null) {
                            if (location1.getBlock().getType().name().contains(ore)) {
                                Block block = location1.getBlock();
                                block.setMetadata(ore + "_FOUND", new FixedMetadataValue(Ranch.getInstance(), "this ore has already been found"));

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
