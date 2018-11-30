package me.ohvalsgod.ranch;

import lombok.Getter;
import me.ohvalsgod.ranch.listeners.*;
import me.ohvalsgod.ranch.player.handler.PlayerDataFetcher;
import me.ohvalsgod.ranch.player.handler.PlayerDataHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Ranch extends JavaPlugin {

    @Getter private static Ranch instance;
    @Getter private PlayerDataHandler dataHandler;
    @Getter private PlayerDataFetcher dataFetcher;

    @Override
    public void onEnable() {
        instance = this;

        dataHandler = new PlayerDataHandler(instance);
        dataFetcher = new PlayerDataFetcher(dataHandler);

        dataHandler.loadLocalData();

        SleepHandler sleep = new SleepHandler();
        LoginListener login = new LoginListener();

        instance.getServer().getPluginManager().registerEvents(sleep, instance);
        instance.getServer().getPluginManager().registerEvents(login, instance);
        instance.getServer().getPluginManager().registerEvents(new LoginListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new InteractListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new MiningHandler(), instance);
        instance.getServer().getPluginManager().registerEvents(new EntityHandler(), instance);
    }

    @Override
    public void onDisable() {
        dataHandler.saveLocalData();
        instance = null;
    }
}
