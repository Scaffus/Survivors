package com.scaffus.survivors.Commands.Admin;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnmuteCommand implements CommandExecutor {
    private final Survivors plugin;
    private final SurvivorsUtils sUtils;
    private final SQLGetter data;

    public UnmuteCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        this.data = plugin.data;
        plugin.getCommand("unmute").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { sender.sendMessage(sUtils.only_player_can_exec); return true; }
        Player p = (Player) sender;
        if (!(p.hasPermission("survivors.unmute"))) { p.sendMessage(sUtils.no_perm); return true; }
        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                int state = data.toggleMute(p, false);
                if (state == 2) {
                    p.sendMessage(sUtils.mute_unmute.replace("%target", target.getName()));
                    target.sendMessage(sUtils.mute_unmuted);
                } else if (state == 3) {
                    p.sendMessage(sUtils.mute_wasnotmuted.replace("%target", target.getName()));
                }
            } else {
                p.sendMessage(sUtils.player_not_found.replace("%player", args[0]));
            }
        } else {
            p.sendMessage(sUtils.mute_usage.replace("mute", "unmute"));
        }
        return false;
    }
}
