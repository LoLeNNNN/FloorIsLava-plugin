package org.example;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.handler.SEventHandler;

import java.net.http.WebSocket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getLogger().info("[NetherDamageFire] Hello, world!");
        getServer().getPluginManager().registerEvents(new SEventHandler(), this);
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("[NetherDamageFire] Bye, world!");
    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }
}