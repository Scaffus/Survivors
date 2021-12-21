package com.scaffus.survivors.Commands.Message;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class MessageCommand implements CommandExecutor {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    public static HashMap<Player, Player> lastMessage;

    public MessageCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = new SurvivorsUtils(plugin);
        plugin.getCommand("msg").setExecutor(this);
        lastMessage = new HashMap<Player, Player>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;
        p.sendMessage("lessafefef");

        if (p.hasPermission("survivors.msg.use")) {
            if (args.length >= 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    StringBuilder message = new StringBuilder();

                    for (String s : args) {
                        if (!s.equalsIgnoreCase(target.getName())) { message.append(s).append(" "); }
                    }

                    p.sendMessage(sUtils.msg_view_sender.replace("%target", target.getName()).replace("%message", message));
                    target.sendMessage(sUtils.msg_view_receiver.replace("%sender", sender.getName()).replace("%message", message));

                    lastMessage.put(p, target);
                    lastMessage.put(target, p);
                } else {
                    p.sendMessage(sUtils.player_not_found.replace("%player", args[0]));
                    return true;
                }
            } else {
                p.sendMessage(sUtils.msg_msg_usage);
                return true;
            }
        } else {
            p.sendMessage(sUtils.no_perm);
            return true;
        }
        return false;
    }
}
