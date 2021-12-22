package com.scaffus.survivors.sql;

import com.scaffus.survivors.Survivors;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLGetter {

    private Survivors plugin;

    public SQLGetter(Survivors plugin) {
        this.plugin = plugin;
        plugin.SQL.getConnection();
    }

    // Pour éviter les longues lignes illisibles
    public PreparedStatement pS(String statement) {
        PreparedStatement ps = null;
        try {
            ps = plugin.SQL.getConnection().prepareStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public void createTable(String table, String table_content, String primary_key) {
        PreparedStatement ps;
        try {
            ps = pS("CREATE TABLE IF NOT EXISTS" + table + "(" + table_content + "), PRIMARY KEY (" + primary_key + "))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Créer un joueur dans la bdd
    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            // Création du joueur s'il n'existe pas
            if (!exists(uuid)) {
                PreparedStatement ps2 = pS("INSERT IGNORE INTO survivors (NAME,UUID) VALUES (?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();

                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check si un joueur existe dans la bdd
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = pS("SELECT * FROM survivors WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                // Le joueur existe
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addMoney(UUID uuid, int amount) {
        try {
           PreparedStatement ps = pS("UPDATE survivors SET MONEY=? WHERE UUID=?");
           ps.setInt(1, (getMoney(uuid) + amount));
           ps.setString(2, uuid.toString());
           ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMoney(UUID uuid) {
        try {
            PreparedStatement ps = pS("SELECT MONEY FROM survivors WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int money = 0;
            if (rs.next()) {
                money = rs.getInt("MONEY");
                return money;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setSpawn(Location location) {
        try {
            int spawn_x = location.getBlockX();
            int spawn_y = location.getBlockY();
            int spawn_z = location.getBlockZ();
            String spawn_world = location.getWorld().toString();
            PreparedStatement ps = pS("UPDATE locations spawn SET SPAWN_X=? SPAWN_Y=? SPAWN_Z=? WORLD=?");
            ps.setInt(1, spawn_x);
            ps.setInt(2, spawn_y);
            ps.setInt(3, spawn_z);
            ps.setString(4, spawn_world);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getSpawn(Location location) {
        List<Integer> spawncoords = new ArrayList<Integer>();
        try {
            PreparedStatement ps = pS("SELECT SPAWN_X SPAWN_Y SPAWN_Z FROM locations spawns WHERE SPAWN_WORLD=?");
            ps.setString(1, location.getWorld().toString());
            int spawn_x = 0;
            int spawn_y = 0;
            int spawn_z = 0;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                spawn_x = rs.getInt("SPAWN_X");
                spawn_y = rs.getInt("SPAWN_Y");
                spawn_z = rs.getInt("SPAWN_Z");
                spawncoords.add(spawn_x);
                spawncoords.add(spawn_y);
                spawncoords.add(spawn_z);
                return spawncoords;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void emptyTable(String table) {
        try {
            PreparedStatement ps = pS("TRUNCATE " + table);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
