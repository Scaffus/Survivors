package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
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

    private Survivors plugin;
    private final SurvivorsUtils sUtils;

    public SpawnCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        plugin.getCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("set")) {
                if (p.hasPermission("survivors.setspawn")) {
                    Location pLoc = p.getLocation();
                    pLoc.setPitch(0);
                    pLoc.setYaw(0);
                    plugin.getConfig().set("Spawn.location", pLoc);
                    plugin.saveConfig();
                    p.sendMessage(sUtils.spawn_set + pLoc.getBlock());
                }
            }
        } else if (plugin.getConfig().getLocation("Spawn.location") != null) {
                if (p.hasPermission("survivors.spawn")) {
                    p.teleport((Location) Objects.requireNonNull(plugin.getConfig().getLocation("Spawn.location")));
                    p.sendMessage(sUtils.spawn_tped);
                }
        } else {
            p.sendMessage("else");
        }

        return false;
    }
}
