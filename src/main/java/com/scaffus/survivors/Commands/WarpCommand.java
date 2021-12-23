package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WarpCommand implements CommandExecutor {
    private Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public WarpCommand(Survivors plugin) {
        this.plugin = plugin;
        this.data = plugin.data;
        this.sUtils = plugin.sUtils;
        plugin.getCommand("warp").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.warp.tp"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        if (args.length == 1) {
            Location pLoc = p.getLocation();
            HashMap<String, Integer> coords = data.getLocation(pLoc, args[0], 2);
            int warp_x = coords.get("location_x");
            int warp_y = coords.get("location_y");
            int warp_z = coords.get("location_z");
            pLoc.setPitch(0);
            pLoc.setYaw(0);
            pLoc.set(warp_x, warp_y, warp_z);
            p.teleport(pLoc);
            p.sendMessage(sUtils.warp_tped.replace("%warp", args[0]));
        } else {
            p.sendMessage(sUtils.warp_usage);
            return true;
        }

        return false;
    }
}