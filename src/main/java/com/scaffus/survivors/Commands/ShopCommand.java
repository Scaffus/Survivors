package com.scaffus.survivors.Commands;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.gui.ShopGUI;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;
    private ShopGUI shopgui;

    public ShopCommand(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
        this.shopgui = new ShopGUI(plugin);
        plugin.getCommand("shop").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,String arg,String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(sUtils.only_player_can_exec);
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("survivors.shop.open"))) {
            p.sendMessage(sUtils.no_perm);
            return true;
        }

        p.openInventory(shopgui.shop(p));

        return false;
    }
}
