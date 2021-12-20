package com.scaffus.survivors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Back {
    public static Map<Player, Location> back = new HashMap();

    public Back() {
    }

    public static boolean back(Player p) {
        if (back.containsKey(p)) {
            p.teleport((Location)back.get(p));
            back.remove(p);
            return true;
        } else {
            return false;
        }
    }
}
