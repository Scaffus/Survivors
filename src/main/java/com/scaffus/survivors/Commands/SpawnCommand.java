package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
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

        // location_type: spawn -> 1
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("set")) {
                if (p.hasPermission("survivors.spawn.set")) {
                    data.setLocation(p.getLocation(), 1);
                    p.sendMessage(sUtils.spawn_set + p.getLocation());
                }
            }
        } else {
            HashMap<String, Integer> coords = data.getLocation(p.getLocation(), 1);
            if (coords != null) {
                int spawn_x = coords.get("location_x");
                int spawn_y = coords.get("location_y");
                int spawn_z = coords.get("location_z");
                Location pLoc = p.getLocation();
                pLoc.setYaw(0);
                pLoc.setPitch(0);
                pLoc.set(spawn_x, spawn_y, spawn_z);
                p.teleport(pLoc);
            } else {
                p.sendMessage("not set");
            }
//        else if (plugin.getConfig().getLocation("Spawn.location") != null) {
//                if (p.hasPermission("survivors.spawn")) {
//                    p.teleport((Location) Objects.requireNonNull(plugin.getConfig().getLocation("Spawn.location")));
//                    p.sendMessage(sUtils.spawn_tped);
//                }
        }
        return false;
    }
}
