package com.scaffus.survivors.events;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.gui.ShopGUI;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiEvents implements Listener {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;
    private ShopGUI shop;

    public GuiEvents(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
        this.shop = new ShopGUI(plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem().getType() == Material.BARRIER && e.getCurrentItem().getI18NDisplayName().equalsIgnoreCase("Fermer")) {
            p.closeInventory();
        }
        if (e.getView().getTitle().startsWith(ChatColor.GOLD + "Shop")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.IRON_ORE) {
                p.openInventory(shop.shop_ore(p, 54));
            }
        }
    }
}
