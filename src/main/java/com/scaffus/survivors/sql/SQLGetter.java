package com.scaffus.survivors.sql;

import com.scaffus.survivors.Survivors;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = pS("CREATE TABLE IF NOT EXISTS survivors (NAME VARCHAR(100), UUID VARCHAR(100), MONEY INT(255), PRIMARY KEY (NAME))");
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

    public void emptyTable() {
        try {
            PreparedStatement ps = pS("TRUNCATE survivors");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
