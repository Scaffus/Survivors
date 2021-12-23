package com.scaffus.survivors.Commands.Tp.Tpa;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TpaDenyCommand implements CommandExecutor {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private final HashMap<Player, Player> tpa;

    public TpaDenyCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        tpa = TpaCommand.tpa;
        plugin.getCommand("tpadeny").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
        }
        Player target = (Player) sender;

        if (!(target.hasPermission("survivors.tpa.tpadeny"))) {
            target.sendMessage(sUtils.no_perm);
            return true;
        }

        if (tpa.containsKey(target)) {
            Player p = tpa.get(target);
            p.sendMessage(sUtils.tpa_view_sender.replace("%target", target.getName()));
            target.sendMessage(sUtils.tpa_view_target.replace("%sender", p.getName()));
            tpa.remove(target);
        }

        return false;
    }
}
