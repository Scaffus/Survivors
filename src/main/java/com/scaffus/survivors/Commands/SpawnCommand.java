package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    private final Survivors plugin;
    private final SurvivorsUtils sUtils;
    private final SQLGetter data;

    public SpawnCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        this.data = plugin.data;
        plugin.getCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.spawn.use"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("set")) {
                if (p.hasPermission("survivors.spawn.set")) {
                    data.setSpawn(p.getLocation());
                    p.sendMessage(sUtils.spawn_set + p.getLocation());
                }
            }
        } else if (data.getSpawn(p.getLocation()) != null) {
            
//        else if (plugin.getConfig().getLocation("Spawn.location") != null) {
//                if (p.hasPermission("survivors.spawn")) {
//                    p.teleport((Location) Objects.requireNonNull(plugin.getConfig().getLocation("Spawn.location")));
//                    p.sendMessage(sUtils.spawn_tped);
//                }
        } else {
            p.sendMessage("else");
        }

        return false;
    }
}
