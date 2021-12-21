package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;

    public ReloadConfigCommand(Survivors plugin) {
        this.plugin = plugin;
        plugin.getCommand("reloadconfig").setExecutor(this);
        this.sUtils = this.plugin.sUtils;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.reloadconfig"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }
            plugin.reloadConfig();
            p.sendMessage(sUtils.prefix + ChatColor.GOLD + " La config a été reload.");

        return false;
    }
}
