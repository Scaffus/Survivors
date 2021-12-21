package com.scaffus.survivors.Commands.Tp;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TpaAcceptCommand implements CommandExecutor {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private final HashMap<Player, Player> tpa;

    public TpaAcceptCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        tpa = TpaCommand.tpa;
        plugin.getCommand("tpaccept").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
        }
        Player target = (Player) sender;
        if (!(target.hasPermission("survivors.tpa.tpaccept"))) {
            target.sendMessage(sUtils.no_perm);
        }

        // Si le joueur a recu une requete
        if (tpa.containsKey(target)) {
            Player p = tpa.get(target);
            target.sendMessage(sUtils.tpa_accept_target.replace("%sender", p.getName()));
            p.sendMessage(sUtils.tpa_accept_sender.replace("%target", target.getName()));
            Location target_location = target.getLocation().toBlockLocation();
            p.teleport(target_location);
            tpa.remove(target);
        }
        return false;
    }
}
