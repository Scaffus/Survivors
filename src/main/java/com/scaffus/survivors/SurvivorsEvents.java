package com.scaffus.survivors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SurvivorsEvents implements Listener {

    public SurvivorsEvents() {}

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
}
