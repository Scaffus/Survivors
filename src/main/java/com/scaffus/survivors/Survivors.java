package com.scaffus.survivors;

import com.scaffus.survivors.Commands.ReloadConfigCommand;
import com.scaffus.survivors.Commands.SpawnCommand;
import com.scaffus.survivors.Commands.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Survivors extends JavaPlugin {

    @Override
    public void onEnable() {
        saveConfig();
        Bukkit.getPluginManager().registerEvents(new SurvivorsListener(), this);
        new SpawnCommand(this);
        new TestCommand(this);
        new ReloadConfigCommand(this);
        System.out.println("[Survivors] Le plugin demarre");
    }

    @Override
    public void onDisable() {

    }

}
