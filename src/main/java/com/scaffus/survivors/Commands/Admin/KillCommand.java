package com.scaffus.survivors.Commands.Admin;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillCommand implements CommandExecutor {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;

    public KillCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        plugin.getCommand("kill").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("surivors.kill"))) { p.sendMessage(sUtils.no_perm); return true; }

        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                target.setHealth(0);
                p.sendMessage(sUtils.kill_kill.replace("%player", target.getName()));
            } else {
                p.sendMessage(sUtils.player_not_found);
            }
        } else {
            p.sendMessage(sUtils.kill_usage);
        }
        return false;
    }
}
