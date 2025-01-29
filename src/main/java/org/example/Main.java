package org.example;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.handler.SEventHandler;

import java.net.http.WebSocket;


public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getLogger().info("[NetherDamageFire] Hello, world!");
        getServer().getPluginManager().registerEvents(new SEventHandler(), this);
        saveDefaultConfig();
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