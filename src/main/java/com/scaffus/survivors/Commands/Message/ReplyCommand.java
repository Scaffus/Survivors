package com.scaffus.survivors.Commands.Message;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ReplyCommand implements CommandExecutor {
    private final Survivors plugin;
    private final SurvivorsUtils sUtils;
    private final HashMap<Player, Player> lastMessage;

    public ReplyCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        lastMessage = MessageCommand.lastMessage;
        plugin.getCommand("r").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.reply.use"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        Player target = lastMessage.get(p);

        StringBuilder message = new StringBuilder();

        for (String s : args) {
            message.append(s).append(" ");
        }

        target.sendMessage(sUtils.msg_view_receiver.replace("%sender", sender.getName()).replace("%message", message));
        p.sendMessage(sUtils.msg_view_sender.replace("%target", target.getName()).replace("%message", message));
        lastMessage.put(p, target);

        return false;
    }
}
