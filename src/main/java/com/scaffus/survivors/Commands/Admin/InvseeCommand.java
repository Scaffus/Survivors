package com.scaffus.survivors.Commands.Admin;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.command.BufferedCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {
    private Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public InvseeCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
        plugin.getCommand("invsee").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.invsee"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                Inventory target_inv = target.getInventory();
                p.openInventory(target_inv);
                return true;
            } else {
                p.sendMessage(sUtils.player_not_found);
            }
        } else {
            p.sendMessage(sUtils.invsee_usage);
        }

        return false;
    }
}
