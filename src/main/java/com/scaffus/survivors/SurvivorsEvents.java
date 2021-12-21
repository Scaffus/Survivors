package com.scaffus.survivors;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.awt.*;

public class SurvivorsEvents implements Listener {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;

    public SurvivorsEvents(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = new SurvivorsUtils(plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p.hasPermission("survivors.back")) {
            Back.back.put(p, p.getLocation());
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("survivors.back")) {
            Back.back.put(p, p.getLocation());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (plugin.getConfig().getBoolean("Mute.muted_players." + p.getUniqueId().toString())) {
            event.setCancelled(true);
            p.sendMessage(sUtils.mute_muted);
        } else {
            event.setCancelled(true);
            String message_content = event.getMessage();
            String message = sUtils.chat_message_format.replace("%player", p.getName()).replace("%content", message_content).replace("%rank", "§7[Gamer]");
            Bukkit.broadcast(TextComponentBuilder.createTextComponent(message));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        event.setJoinMessage(sUtils.chat_player_join.replace("%player", p.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage(sUtils.chat_player_quit.replace("%player", p.getName()));
    }
}
