package com.scaffus.survivors.Commands.Money;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand implements CommandExecutor {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public MoneyCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
        plugin.getCommand("money").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.money.money"))) {
            p.sendMessage(sUtils.no_perm);
        }

        if (p.hasPermission("survivors.money.add")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("add")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        try {
                            int ammount = Integer.parseInt(args[2]);
                            data.addMoney(target.getUniqueId(), ammount);
                            p.sendMessage(sUtils.money_add_message.replace("%amount", String.valueOf(ammount)).replace("%target", target.getName()));
                            target.sendMessage(sUtils.money_pay_view_target.replace("%amount", String.valueOf(ammount)).replace("%sender", p.getName()));
                        } catch (Exception e) {
                            plugin.getLogger().info(ChatColor.RED + e.toString());
                            p.sendMessage(sUtils.error);
                        }
                    } else {
                        p.sendMessage(sUtils.player_not_found.replace("%player", args[1]));
                    }
                } else {
                    p.sendMessage(sUtils.money_add_usage);
                }
                return true;
            }
        } else {
            p.sendMessage(sUtils.no_perm);
        }

        int player_money = data.getMoney(p.getUniqueId());
        p.sendMessage(sUtils.money_money_message.replace("%balance", String.valueOf(player_money)));

        return false;
    }
}
