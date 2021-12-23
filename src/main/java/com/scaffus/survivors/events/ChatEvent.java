package com.scaffus.survivors.events;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.TextComponentBuilder;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public ChatEvent(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        event.setCancelled(true);
        if (data.isMuted(p.getUniqueId())) {
            p.sendMessage(sUtils.mute_muted);
        } else {
            // Formatting message based on the config
            String message_content = event.getMessage();
            String message = sUtils.chat_message_format.replace("%player", p.getName()).replace("%content", message_content).replace("%rank", "§7[Gamer]");
            // Formatting (colors and italics/bold/underlined/etc)
            if (p.hasPermission("survivors.chat.color")) {
                String letters = "o n m l k a e b d f c";
                for (int i = 0; i < 9; i++) {
                    message = message.replace("&" + i, "§" +i);
                }
                for (String character : letters.split(" ")) {
                    message = message.replace("&" + character, "§" + character);
                }
            }
            Bukkit.broadcast(TextComponentBuilder.createTextComponent(message));
        }
    }
}
