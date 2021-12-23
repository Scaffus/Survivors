package com.scaffus.survivors.gui;

import com.scaffus.survivors.Commands.ShopCommand;
import com.scaffus.survivors.Survivors;
import com.scaffus.survivors.SurvivorsUtils;
import com.scaffus.survivors.sql.SQLGetter;
import it.unimi.dsi.fastutil.Hash;
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
import java.util.HashMap;

public class ShopGUI {

    private final Survivors plugin;
    private SurvivorsUtils sUtils;
    private SQLGetter data;

    public ShopGUI(Survivors plugin) {
        this.plugin = plugin;
        this.data = plugin.data;
        this.sUtils = plugin.sUtils;
    }

    public Inventory shop(Player p, int size) {

        Inventory gui = createNewInventory(p, size, ChatColor.GOLD + "Shop");

        ItemStack ores = new ItemStack(Material.IRON_ORE);
        ItemStack redstone = new ItemStack(Material.PISTON);
        ItemStack eggs = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemStack blocks = new ItemStack(Material.STONE);
        ItemStack miscellaneous = new ItemStack(Material.WATER_BUCKET);
        ItemStack rare = new ItemStack(Material.BEACON);

        createItemMeta(ores, ChatColor.GRAY + "Minerais",  "Achat et vente de minerais", false, true);
        createItemMeta(redstone, ChatColor.GRAY + "Restone", "Achat de composants de restones", false, true);
        createItemMeta(eggs, ChatColor.GRAY + "Oeufs", "Achats d'oeufs", false, true);
        createItemMeta(blocks, ChatColor.GRAY + "Blocs", "Achats et ventes de blocs", false, true);
        createItemMeta(miscellaneous, ChatColor.GRAY + "Divers", "Achats et vente d'objets divers", false, true);
        createItemMeta(rare, ChatColor.LIGHT_PURPLE + "Rares", "Achats et vente d'items rares", false, true);

        gui.setItem(20, ores);
        gui.setItem(21, redstone);
        gui.setItem(22, eggs);
        gui.setItem(23, blocks);
        gui.setItem(24, miscellaneous);
        gui.setItem(31, rare);
        return gui;
    }

    public Inventory shop_ore(Player p, int size) {
        Inventory gui = createNewInventory(p, size, ChatColor.GOLD + "Shop | Minerais");
        ChatColor color = ChatColor.GRAY;

        ItemStack coal_ore = new ItemStack(Material.COAL_ORE);
        ItemStack iron_ore = new ItemStack(Material.IRON_ORE);
        ItemStack gold_ore = new ItemStack(Material.GOLD_ORE);
        ItemStack redstone_ore = new ItemStack(Material.REDSTONE_ORE);
        ItemStack lapis_ore = new ItemStack(Material.LAPIS_ORE);
        ItemStack emerald_ore = new ItemStack(Material.EMERALD_ORE);
        ItemStack diamond_ore = new ItemStack(Material.DIAMOND_ORE);
        ItemStack ancient_debris = new ItemStack(Material.ANCIENT_DEBRIS);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemStack iron_ingot = new ItemStack(Material.IRON_INGOT);
        ItemStack gold_ingot = new ItemStack(Material.GOLD_INGOT);
        ItemStack redstone_dust = new ItemStack(Material.REDSTONE);
        ItemStack lapis_lazuli = new ItemStack(Material.LAPIS_LAZULI);
        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemStack netherite_scrap = new ItemStack(Material.NETHERITE_SCRAP);

        createItemMeta(coal_ore, color + "Minerais de charbon", "", false, false);
        createItemMeta(iron_ore, color + "Minerais de fer", "", false, false);
        createItemMeta(gold_ore, color + "Minerais d'or", "", false, false);
        createItemMeta(redstone_ore, color + "Minerais de redstone", "", false, false);
        createItemMeta(lapis_ore, color + "Minerais de lapis", "", false, false);
        createItemMeta(emerald_ore, color + "Minerais d'émeraude", "", false, false);
        createItemMeta(diamond_ore, color + "Minerais de diamant", "", false, false);
        createItemMeta(ancient_debris, color + "Minerais de charbon", "", false, false);

        createItemMeta(coal, color + "Charbon", "", false, false);
        createItemMeta(iron_ingot, color + "Lingot de fer", "", false, false);
        createItemMeta(gold_ingot, color + "Lingot d'or", "", false, false);
        createItemMeta(redstone_dust, color + "Poudre de Redstone", "", false, false);
        createItemMeta(lapis_lazuli, color + "Lapis Lazuli", "", false, false);
        createItemMeta(emerald, color + "Emeraude", "", false, false);
        createItemMeta(diamond, color + "Diamant", "", false, false);
        createItemMeta(netherite_scrap, color + "Fragments de netherite", "", false, false);

        gui.setItem(11, coal_ore);
        gui.setItem(20, coal);

        gui.setItem(12, iron_ore);
        gui.setItem(21, iron_ingot);

        gui.setItem(13, gold_ore);
        gui.setItem(22, gold_ingot);

        gui.setItem(14, redstone_ore);
        gui.setItem(23, redstone_dust);

        gui.setItem(15, lapis_ore);
        gui.setItem(24, lapis_lazuli);

        gui.setItem(30, emerald_ore);
        gui.setItem(39, emerald);

        gui.setItem(31, diamond_ore);
        gui.setItem(40, diamond);

        gui.setItem(32, ancient_debris);
        gui.setItem(41, netherite_scrap);

        return gui;
    }

    public Inventory createNewInventory(@NotNull Player p, @NotNull int size, @NotNull String name) {
        Inventory gui = Bukkit.createInventory(p, size, name);
        ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack close = new ItemStack(Material.BARRIER);
        createItemMeta(close, ChatColor.DARK_RED + "Fermer", "", false, false);
        for (int i = 0; i < size; i++) {
            gui.setItem(i, background);
        }
        gui.setItem(size - 5, close);
        return gui;
    }

    public ItemStack createItemMeta(@NotNull ItemStack item, String display_name, String lore, boolean isEnchanted, boolean isUnmovable) {
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(display_name);
        ArrayList<String> item_lore = new ArrayList<>();
        item_lore.addAll(Arrays.asList(lore));
        item_meta.setLore(item_lore);
        item.setItemMeta(item_meta);
        return item;
    }
}
