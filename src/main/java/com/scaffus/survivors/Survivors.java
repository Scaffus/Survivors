package com.scaffus.survivors;

import com.scaffus.survivors.Commands.*;
import com.scaffus.survivors.Commands.Admin.*;
import com.scaffus.survivors.Commands.Message.MessageCommand;
import com.scaffus.survivors.Commands.Message.ReplyCommand;
import com.scaffus.survivors.Commands.Money.MoneyCommand;
import com.scaffus.survivors.Commands.Money.PayCommand;
import com.scaffus.survivors.Commands.Tp.Tpa.TpaAcceptCommand;
import com.scaffus.survivors.Commands.Tp.Tpa.TpaCommand;
import com.scaffus.survivors.Commands.Tp.Tpa.TpaDenyCommand;
import com.scaffus.survivors.events.ChatEvent;
import com.scaffus.survivors.events.GuiEvents;
import com.scaffus.survivors.events.JoinQuitEvent;
import com.scaffus.survivors.sql.MySQL;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Survivors extends JavaPlugin {

    public MySQL SQL;
    public SurvivorsUtils sUtils;
    public SQLGetter data;

    @Override
    public void onEnable() {
        // if (this.getConfig().get("test") != null) { saveConfig(); } else { saveDefaultConfig();
        saveDefaultConfig();
        this.sUtils = new SurvivorsUtils(this);
        this.SQL = new MySQL();
        this.data = new SQLGetter(this);

        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
            this.getLogger().info(ChatColor.RED + "Database not connected");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (SQL.isConnected()) {
            this.getLogger().info(ChatColor.GREEN + "Database is connected!");
            try {
                data.createTable("survivors", "NAME VARCHAR(100), UUID VARCHAR(100), MONEY INT(255), isMuted BOOL", "NAME");
                data.createTable("locations",
     "LOCATION_X INT(100), LOCATION_Y INT(100), LOCATION_Z INT(100), LOCATION_WORLD VARCHAR(100), LOCATION_TYPE INT(100), LOCATION_NAME VARCHAR(100)"
                , "LOCATION_TYPE");
                this.getLogger().info(ChatColor.GREEN + "Table created successfuly in db");
            } catch (Exception e) {
                this.getLogger().info(ChatColor.RED + "Failed to create table in db");
            }
        }

        Bukkit.getPluginManager().registerEvents(new SurvivorsEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new GuiEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvent(this), this);

        new SpawnCommand(this);
        new TestCommand(this);
        new ReloadConfigCommand(this);
        new MessageCommand(this);
        new ReplyCommand(this);
        new KillCommand(this);

        new MuteCommand(this);
        new UnmuteCommand(this);

        new TpaCommand(this);
        new TpaAcceptCommand(this);
        new TpaDenyCommand(this);

        new MoneyCommand(this);
        new PayCommand(this);

        new ShopCommand(this);

        new SetWarpCommand(this);
        new WarpCommand(this);

        new InvseeCommand(this);
        this.getLogger().info(ChatColor.GREEN + "Plugin active avec succes");
    }

    @Override
    public void onDisable() {

    }

}
