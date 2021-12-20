package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;

    public TestCommand(Survivors plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
        this.sUtils = new SurvivorsUtils(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd,String label,String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true; }
        Player p = (Player) sender;
        if (p.hasPermission("survivors.test")) {
            p.sendMessage(sUtils.test);
        }
        return false;
    }
}
