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

public class PayCommand implements CommandExecutor {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public PayCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
        plugin.getCommand("pay").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String arg, String[] args) {
        if (!( sender instanceof Player )) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.money.pay"))) {
            p.sendMessage(sUtils.no_perm);
        }
        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null & target != p) {

                int p_money = data.getMoney(p.getUniqueId());
                int target_money = data.getMoney(target.getUniqueId());

                try {
                    int amount = Integer.parseInt(args[1]);
                    if (data.getMoney(p.getUniqueId()) < amount) {
                        p.sendMessage(sUtils.money_pay_notenoughmoney);
                        return true;
                    }
                    if (!(amount > 0)) {
                        p.sendMessage(sUtils.money_pay_needpositiveammount);
                        return true;
                    }
                    // Soustrait le montant au joueur qui paie
                    data.addMoney(p.getUniqueId(), -amount);
                    // Ajoute le montant au joueur payé
                    data.addMoney(target.getUniqueId(), amount);
                    p.sendMessage(sUtils.money_pay_view_sender.replace("%target", target.getName()).replace("%amount", String.valueOf(amount)));
                    target.sendMessage(sUtils.money_pay_view_target.replace("%sender", p.getName()).replace("%amount", String.valueOf(amount)));
                } catch (Exception e) {
                    plugin.getLogger().info(ChatColor.RED + e.toString());
                }
            }

        } else {
            p.sendMessage(sUtils.money_pay_usage);
        }
        return false;
    }
}
