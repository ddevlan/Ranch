package me.ohvalsgod.ranch.listeners;

import me.ohvalsgod.ranch.Ranch;
import me.ohvalsgod.ranch.player.PlayerData;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityHandler implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof Phantom) {
                Phantom phantom = (Phantom) event.getEntity();
                if (event.getTarget() instanceof Player) {
                    Player player = (Player) event.getTarget();
                    PlayerData data = Ranch.getInstance().getDataFetcher().getDataFromUUID(player.getUniqueId());
                    if (System.currentTimeMillis() - data.getLastSleep() < (60 * 60)*1000) {
                        event.setCancelled(true);
                        event.setTarget(null);
                        phantom.setFireTicks(20 * 10);
                        phantom.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 10, 2));
                    }
                }
            }
        }
    }

}
