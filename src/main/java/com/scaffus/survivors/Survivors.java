package com.scaffus.survivors;

import com.scaffus.survivors.Commands.*;
import com.scaffus.survivors.Commands.Admin.KillCommand;
import com.scaffus.survivors.Commands.Admin.MuteCommand;
import com.scaffus.survivors.Commands.Admin.TestCommand;
import com.scaffus.survivors.Commands.Admin.UnmuteCommand;
import com.scaffus.survivors.Commands.Message.MessageCommand;
import com.scaffus.survivors.Commands.Message.ReplyCommand;
import com.scaffus.survivors.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Survivors extends JavaPlugin {

    public MySQL SQL;
    public SurvivorsUtils sUtils;

    @Override
    public void onEnable() {
        // if (this.getConfig().get("test") != null) { saveConfig(); } else { saveDefaultConfig();
        saveDefaultConfig();
        this.sUtils = new SurvivorsUtils(this);
        this.SQL = new MySQL();

        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.GOLD + "[Survivors] Database not connected");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info(ChatColor.GREEN + "[Survivors] Database is connected!");
        }

        Bukkit.getPluginManager().registerEvents(new SurvivorsEvents(this), this);
        new SpawnCommand(this);
        new TestCommand(this);
        new ReloadConfigCommand(this);
        new MessageCommand(this);
        new ReplyCommand(this);
        new KillCommand(this);
        new MuteCommand(this);
        new UnmuteCommand(this);
        this.getLogger().info(ChatColor.GREEN + "[Survivors] Le plugin demarre");
    }

    @Override
    public void onDisable() {

    }

}
