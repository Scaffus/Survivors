package com.scaffus.survivors.gui;

import com.scaffus.survivors.Commands.ShopCommand;
import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopGUI {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public ShopGUI(Survivors plugin) {
        this.plugin = plugin;
        this.data = plugin.data;
        this.sUtils = plugin.sUtils;
    }

    public Inventory shop(Player p) {

        Inventory gui = Bukkit.createInventory(p, 54, ChatColor.GOLD + "Shop");

        ItemStack ores = new ItemStack(Material.IRON_ORE);
        ItemStack redstone = new ItemStack(Material.PISTON);
        ItemStack eggs = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemStack blocks = new ItemStack(Material.STONE);
        ItemStack miscellaneous = new ItemStack(Material.WATER_BUCKET);
        ItemStack rare = new ItemStack(Material.BEACON);

        createItemMeta(ores, "Minerais",  "Achat et vente de minerais", false, true);
        createItemMeta(redstone, "Restone", "Achat de composants de restones", false, true);
        createItemMeta(eggs, "Oeufs", "Achats d'oeufs", false, true);
        createItemMeta(blocks, "Blocs", "Achats et ventes de blocs", false, true);
        createItemMeta(miscellaneous, "Divers", "Achats et vente d'objets divers", false, true);
        createItemMeta(rare, "Rares", "Achats et vente d'items rares", false, true);

        ItemStack[] shop_items = {ores, redstone, eggs, blocks, miscellaneous, rare};
        gui.setContents(shop_items);
        return gui;
    }

    public ItemStack createItemMeta(@NotNull ItemStack item, String display_name, String lore, boolean isEnchanted, boolean isUnmovable) {
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(display_name);
        ArrayList<String> item_lore = new ArrayList<>();
        item_lore.addAll(Arrays.asList(lore));
        item_meta.setLore(item_lore);
        if (isUnmovable) {
            item_meta.addItemFlags(ItemFlag.valueOf("unmovable"));
        }
        item.setItemMeta(item_meta);
        return item;
    }
}
