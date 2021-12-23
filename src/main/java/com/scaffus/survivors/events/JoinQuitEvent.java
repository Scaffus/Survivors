package com.scaffus.survivors.events;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvent implements Listener {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public JoinQuitEvent(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        data.createPlayer(p);
        event.setJoinMessage(sUtils.chat_player_join.replace("%player", p.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage(sUtils.chat_player_quit.replace("%player", p.getName()));
    }
}
