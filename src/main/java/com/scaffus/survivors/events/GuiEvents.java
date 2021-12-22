package com.scaffus.survivors.events;

import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class GuiEvents implements Listener {
    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public GuiEvents(Survivors plugin) {
        this.plugin = plugin;
        this.sUtils = plugin.sUtils;
        this.data = plugin.data;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        ItemStack event_item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();

        if (event_item.getItemMeta().hasItemFlag(ItemFlag.valueOf("unmovable"))) {
            e.setCancelled(true);
        }
    }
}
