package org.example.handler;

import java.util.UUID;


import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.handler.LocaleManager;
import org.yaml.snakeyaml.Yaml;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.example.Main.*;



public class SEventHandler implements Listener {
    private final Set<UUID> playersOnDangerousBlocks = new HashSet<>();
    private final Set<UUID> playersOnBasaltDeltas = new HashSet<>();
    private JavaPlugin plugin;
    private LocaleManager localeManager;

    public SEventHandler(JavaPlugin plugin, LocaleManager localeManager) {
        this.localeManager = localeManager;
        this.plugin = plugin;
    }
    @EventHandler
        public void onMove (PlayerMoveEvent e){
            Location loc = e.getPlayer().getLocation().clone().subtract(0, 0.5, 0);
            Block b = loc.getBlock();
            Player player = e.getPlayer();
            ItemStack boots = player.getInventory().getBoots();
            UUID playerId = player.getUniqueId();
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                if (b.getType() == Material.NETHERRACK || b.getType() == Material.CRIMSON_NYLIUM || b.getType() == Material.NETHER_BRICK || b.getType() == Material.NETHER_GOLD_ORE || b.getType() == Material.NETHER_QUARTZ_ORE || b.getType() == Material.NETHER_BRICKS) {
                    if (boots != null && boots.getType() != Material.AIR) {
                        if (boots != null && boots.getType() != Material.NETHERITE_BOOTS) {
                            if (!playersOnDangerousBlocks.contains(playerId)) {
                                player.sendMessage(localeManager.getMessage(player, "BootsMelting"));
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
                        }
                    } else {
                        e.getPlayer().damage(plugin.getConfig().getInt("config.damage"));
                        if (instance.getConfig().getBoolean("config.fire")) {
                            player.setFireTicks(400);

                        }
                    }

                } else {
                    if (b.getType() != Material.AIR) {
                        if (playersOnDangerousBlocks.contains(playerId)) {
                            playersOnDangerousBlocks.remove(playerId);
                        }
                    }
                    if (instance.getConfig().getBoolean("config.doSoulSandWitherEffect")) {
                        if (b.getType() == Material.SOUL_SAND || b.getType() == Material.SOUL_SOIL) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 0));
                        }
                    }
                    if (instance.getConfig().getBoolean("config.doWarpedNyliumLevitation")) {
                        if (b.getType() == Material.WARPED_NYLIUM) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1));
                        }
                    }
                    if (instance.getConfig().getBoolean("config.doDustInBasalDeltas")) {
                        if (player.getLocation().getBlock().getBiome() == Biome.BASALT_DELTAS) {
                            if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType() != Material.CARVED_PUMPKIN) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, -1, 1));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 1));
                                if (!playersOnBasaltDeltas.contains(playerId)) {
                                    player.sendMessage(localeManager.getMessage(player, "BasaltDeltasDust"));
                                    playersOnBasaltDeltas.add(playerId);
                                }
                            } else if (player.getInventory().getHelmet().getType() == Material.CARVED_PUMPKIN) {
                                player.removePotionEffect(PotionEffectType.BLINDNESS);
                                player.removePotionEffect(PotionEffectType.SLOWNESS);
                                playersOnBasaltDeltas.remove(playerId);
                            }
                        } else {
                            if (playersOnBasaltDeltas.contains(playerId)) {
                                player.removePotionEffect(PotionEffectType.BLINDNESS);
                                player.removePotionEffect(PotionEffectType.SLOWNESS);
                                playersOnBasaltDeltas.remove(playerId);
                            }
                        }
                    }
                }
            }
        }
    }

    //todo make list from config yml
    //todo damageble on sm blcks
    //if (b.getType() == Material.valueOf(instance.getConfig().getString("blocks.nether"))) {}