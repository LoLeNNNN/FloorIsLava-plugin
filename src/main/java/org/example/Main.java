package org.example;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.handler.LocaleManager;
import org.example.handler.SEventHandler;
import java.net.http.WebSocket;


public class Main extends JavaPlugin implements Listener {

    public static Main instance;
    private SEventHandler eventHandler;

    @Override
    public void onEnable() {
        instance = this;
        LocaleManager localeManager = new LocaleManager(this);
        SEventHandler eventHandler = new SEventHandler(this, localeManager);
        getServer().getPluginManager().registerEvents(eventHandler, this);
        getServer().getLogger().info("[NetherDamageFire] Hello, world!");
        saveDefaultConfig();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("[NetherDamageFire] Bye, world!");
    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }

    public static Main getInstance() {return instance; }
}