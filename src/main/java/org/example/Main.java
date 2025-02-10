package org.example;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.handler.LocaleManager;
import org.example.handler.SEventHandler;

import java.net.http.WebSocket;


public class Main extends JavaPlugin implements Listener {
    private final double CONFIG_VERSION = 1.2;

    public static Main instance;
    private SEventHandler eventHandler;

    @Override
    public void onEnable() {

        instance = this;
        LocaleManager localeManager = new LocaleManager(this);
        SEventHandler eventHandler = new SEventHandler(this, localeManager);
        getServer().getPluginManager().registerEvents(eventHandler, this);
        getServer().getLogger().info("[NetherComplication] Hello, world!");
        saveDefaultConfig();
        updateConfig();
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    private void updateConfig() {
        FileConfiguration config = getConfig();


        double currentConfigVersion = config.getDouble("config-version", 1.0);

        if (currentConfigVersion < CONFIG_VERSION) {
            getLogger().info("Updated config upto " + CONFIG_VERSION);

            config.set("config-version", CONFIG_VERSION);
            saveConfig();
        }
        }
    @Override
    public void onDisable() {
        getServer().getLogger().info("[NetherComplication] Bye, world!");
    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }

    public static Main getInstance() {
        return instance;
    }
}