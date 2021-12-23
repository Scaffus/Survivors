package com.scaffus.survivors.Commands.Admin;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class SetWarpCommand implements CommandExecutor {
    private Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public SetWarpCommand(Survivors plugin) {
        this.plugin = plugin;
        this.data = plugin.data;
        this.sUtils = plugin.sUtils;
        plugin.getCommand("setwarp").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.warp.setwarp"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        if (args.length == 1) {
            data.setLocation(p.getLocation(), args[0], 2);
            p.sendMessage(sUtils.setwarp_set.replace("%warp", args[0]));
        } else {
            p.sendMessage(sUtils.setwarp_usage);
        }

        return false;
    }
}
