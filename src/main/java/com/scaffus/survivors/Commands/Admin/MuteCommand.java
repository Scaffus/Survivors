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

public class MuteCommand implements CommandExecutor {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public MuteCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        this.data = plugin.data;
        plugin.getCommand("mute").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { sender.sendMessage(sUtils.only_player_can_exec); return true; }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.mute"))) { p.sendMessage(sUtils.no_perm); return true; }

        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                int state = data.toggleMute(p, true);
                if (state == 0) {
                    p.sendMessage(sUtils.mute_mute.replace("%target", target.getName()));
                    target.sendMessage(sUtils.mute_muted);
                } else if (state == 1) {
                    p.sendMessage(sUtils.mute_alreadymuted.replace("%target", target.getName()));
                }
            } else {
                p.sendMessage(sUtils.player_not_found.replace("%player", args[0]));
            }
        } else {
            p.sendMessage(sUtils.mute_usage);
        }
        return false;
    }
}
