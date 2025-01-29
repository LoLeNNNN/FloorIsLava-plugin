package org.example.handler;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class SEventHandler implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location loc = e.getPlayer().getLocation().clone().subtract(0, 1, 0);
        Block b = loc.getBlock();

        //List<Material> NrMaterial = new ArrayList<>();
        //NrMaterial.add(Material.NETHERRACK);
        //NrMaterial.add(Material.CRIMSON_NYLIUM);
        //NrMaterial.add(Material.NETHER_BRICK);


        if (b.getType() == Material.NETHERRACK){
            e.getPlayer().damage(2);
            Player player = e.getPlayer();
            //player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_ON_FIRE, 1f,1f);
            //comented bc im rt and dk h2 m delay
            player.setFireTicks(400);
            }
        if (b.getType() == Material.CRIMSON_NYLIUM){
            e.getPlayer().damage(2);
            Player player = e.getPlayer();
            player.setFireTicks(400);
        }
        if (b.getType() == Material.NETHER_BRICK){
            e.getPlayer().damage(2);
            Player player = e.getPlayer();
            player.setFireTicks(400);
        }
        if (b.getType() == Material.SOUL_SAND) {
            Player player = e.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2));
        }
        }
    }
    //todo make list from config yml
    //btw sry for bad codding