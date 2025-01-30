package org.example.handler;

import java.util.UUID;


import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.example.Main.*;



public class SEventHandler implements Listener {
    private final Set<UUID> playersOnDangerousBlocks = new HashSet<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location loc = e.getPlayer().getLocation().clone().subtract(0, 0.5, 0);
        Block b = loc.getBlock();
        Player player = e.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        UUID playerId = player.getUniqueId();
        if (b.getType() == Material.NETHERRACK || b.getType() == Material.CRIMSON_NYLIUM || b.getType() == Material.NETHER_BRICK) {

            if (boots != null && boots.getType() != Material.AIR) {
                if (!playersOnDangerousBlocks.contains(playerId)) {
                    player.sendMessage(ChatColor.RED + "Твои ботинки плавятся!");
                    playersOnDangerousBlocks.add(playerId);
                }

                ItemMeta meta = boots.getItemMeta();
                Damageable damageable = (Damageable) meta;
                int maxDurability = boots.getType().getMaxDurability();
                int currentDamage = damageable.getDamage();
                damageable.setDamage(currentDamage + 1);
                boots.setItemMeta(meta);
                player.getInventory().setBoots(boots);
                if (damageable.getDamage() >= maxDurability) {
                    player.getInventory().setBoots(null);
                }
            } else {
                e.getPlayer().damage(instance.getConfig().getInt("blocks.ndamage"));
                if (instance.getConfig().getBoolean("blocks.fire")) {
                    player.setFireTicks(400);

                }
            }
        } else {
            if (b.getType() != Material.AIR) {
                if (playersOnDangerousBlocks.contains(playerId)) {
                    playersOnDangerousBlocks.remove(playerId);
                }
            }
            if (instance.getConfig().getBoolean("blocks.doSoulSandWitherEffect")) {
                if (b.getType() == Material.SOUL_SAND || b.getType() == Material.SOUL_SOIL) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2));
                }
            }
        }
    }
}
    //todo make list from config yml
    //todo damageble on sm blcks
    //if (b.getType() == Material.valueOf(instance.getConfig().getString("blocks.nether"))) {}