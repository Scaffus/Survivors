package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Back;
import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BackCommand implements CommandExecutor {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;

    public BackCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = this.plugin.sUtils;
        plugin.getCommand("back").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,String label,String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true; }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.back"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        if (Back.back(p)) {
            p.sendMessage(sUtils.back_back);
        } else {
            p.sendMessage(sUtils.back_noback);
        }
        return false;
    }
}
