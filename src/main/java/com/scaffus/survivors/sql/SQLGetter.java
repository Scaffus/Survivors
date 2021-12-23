package com.scaffus.survivors.sql;

import com.scaffus.survivors.Survivors;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.sound.sampled.FloatControl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
            ps = pS("CREATE TABLE IF NOT EXISTS " + table + " (" + table_content + ", PRIMARY KEY (" + primary_key + "))");
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
            if (!playerExists(uuid)) {
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
    public boolean playerExists(UUID uuid) {
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

    // Créer une position dans la bdd si elle n'existe pas
    public void createLocation(Location location, String location_name, int location_type) {
        try {
            String location_world = location.getWorld().getName();
            // Création de la position s'il n'existe pas
            if (!locationExists(location, location_name, location_type)) {
                PreparedStatement ps = pS("INSERT IGNORE INTO locations (LOCATION_WORLD, LOCATION_TYPE, LOCATION_NAME) VALUES (?, ?, ?)");
                ps.setString(1, location_world);
                ps.setInt(2, location_type);
                ps.setString(3, location_name);
                ps.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check si la position exites deja
    public boolean locationExists(Location location, String location_name, int location_type) {
        try {
            PreparedStatement ps = pS("SELECT * FROM locations WHERE LOCATION_WORLD=? AND LOCATION_TYPE=? AND LOCATION_NAME=?");
            ps.setString(1, location.getWorld().getName());
            ps.setInt(2, location_type);
            ps.setString(3, location_name);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                // Le spawn existe
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* Types de positions:
        1 -> Spawn
        2 -> Warp
    */

    // Set les coorsd de la position et son type
    public void setLocation(Location location, String location_name, int location_type) {
        try {
            createLocation(location, location_name, location_type);
            int location_x = location.getBlockX();
            int location_y = location.getBlockY();
            int location_z = location.getBlockZ();
            String location_world = location.getWorld().getName();
            PreparedStatement ps = pS(
"UPDATE locations SET LOCATION_X=?, LOCATION_Y=?, LOCATION_Z=?, LOCATION_NAME=? WHERE LOCATION_WORLD=? AND LOCATION_TYPE=?"
            );
            ps.setInt(1, location_x);
            ps.setInt(2, location_y);
            ps.setInt(3, location_z);
            ps.setString(4, location_name);
            ps.setString(5, location_world);
            ps.setInt(6, location_type);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupere les valeurs x, y, z et le type de position
    public HashMap<String, Integer> getLocation(Location location, String location_name, int location_type) {
        HashMap<String, Integer> location_coords = new HashMap<>();
        try {
            PreparedStatement ps = pS(
                    "SELECT LOCATION_X, LOCATION_Y, LOCATION_Z FROM locations WHERE LOCATION_WORLD=? AND LOCATION_NAME=? AND LOCATION_TYPE=?"
            );
            ps.setString(1, location.getWorld().getName());
            ps.setString(2, location_name);
            ps.setInt(3, location_type);
            int location_x = 0;
            int location_y = 0;
            int location_z = 0;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                location_x = rs.getInt("LOCATION_X");
                location_y = rs.getInt("LOCATION_Y");
                location_z = rs.getInt("LOCATION_Z");
                location_coords.put("location_x", location_x);
                location_coords.put("location_y", location_y);
                location_coords.put("location_z", location_z);
                return location_coords;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int toggleMute(Player player, boolean mute) {
        UUID uuid = player.getUniqueId();
        try {
            if (mute) {
                if (!isMuted(uuid)) {
                    PreparedStatement ps = pS("UPDATE survivors SET isMuted=? WHERE UUID=?");
                    ps.setBoolean(1, true);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                    return 0;
                } else {
                    PreparedStatement ps = pS("UPDATE survivors SET isMuted=? WHERE UUID=?");
                    ps.setBoolean(1, false);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                    return 1;
                }
            } else {
                if (isMuted(uuid)) {
                    PreparedStatement ps = pS("UPDATE survivors SET isMuted=? WHERE UUID=?");
                    ps.setBoolean(1, false);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                    return 2;
                } else {
                    PreparedStatement ps = pS("UPDATE survivors SET isMuted=? WHERE UUID=?");
                    ps.setBoolean(1, false);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                    return 3;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 4;
    }

    public boolean isMuted(UUID uuid) {
        try {
            PreparedStatement ps = pS("SELECT * FROM survivors WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isMuted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//            try {
//        PreparedStatement ps = pS("SELECT * FROM survivors WHERE UUID=?");
//        ps.setString(1, uuid.toString());
//
//        ResultSet results = ps.executeQuery();
//        if (results.next()) {
//            // Le joueur existe
//            return true;
//        }
//        return false;
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//        return false;

    public void emptyTable(String table) {
        try {
            PreparedStatement ps = pS("TRUNCATE " + table);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
