package com.scaffus.survivors.Commands.Tp;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TpaCommand implements CommandExecutor {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    public static HashMap<Player, Player> tpa;

    public TpaCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
        }
        Player p = (Player) sender;

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null) {
                target.sendMessage(sUtils.tpa_view_target.replace("%sender", p.getName()));
                p.sendMessage(sUtils.tpa_view_sender.replace("%target", target.getName()));
                tpa.put(target, p);
            } else {
                p.sendMessage(sUtils.player_not_found);
            }
        } else {
            p.sendMessage(sUtils.tpa_usage);
        }
        return false;
    }
}
