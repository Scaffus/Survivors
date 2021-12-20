package com.scaffus.survivors;

import org.bukkit.ChatColor;

public class SurvivorsUtils {

    private Survivors plugin;
    public final String prefix;
    public final String error;
    public final String no_perm;
    public final String usage_spawn;
    public final String spawn_tped;
    public final String spawn_set;
    public final String test;
    public final String back_back;
    public final String back_noback;
    public final String only_player_can_exec;

    public SurvivorsUtils(Survivors plugin) {
        this.plugin = plugin;
        this.test = plugin.getConfig().get("test").toString();
        this.prefix = plugin.getConfig().get("Prefix").toString().replace("%&", "§");
        this.error = plugin.getConfig().get("Error").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.no_perm = plugin.getConfig().get("NoPerm").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.usage_spawn = plugin.getConfig().get("Spawn.usage").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.spawn_tped = plugin.getConfig().get("Spawn.tped").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.spawn_set = plugin.getConfig().get("Spawn.set").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.back_back = plugin.getConfig().get("Back.back").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.back_noback = plugin.getConfig().get("Back.boback").toString().replace("%&", "§").replace("%prefix", this.prefix);
        this.only_player_can_exec = ChatColor.RED + "Seul un joueur peut executer cette commande.";
    }
}
