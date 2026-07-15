package com.pluginforge.generated.storyweaver.gui;

import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class MainMenu {
    public static final String TITLE = "§8§lStoryWeaver";
    private final ServiceRegistry services;

    public MainMenu(ServiceRegistry services) { this.services = services; }

    public void open(Player player) {
        MenuHolder holder = new MenuHolder();
        Inventory inv = Bukkit.createInventory(holder, 27, TITLE);
        holder.bind(inv);
        inv.setItem(11, icon(Material.NETHER_STAR, "&bOverview", "&7Branching NPC dialogue with conditions, items and quest flags."));
        inv.setItem(13, icon(Material.COMPASS, "&aStatus", "&7Score: &f" + services.profiles().score(player.getUniqueId())));
        inv.setItem(15, icon(Material.BOOK, "&eHelp", "&7Use subcommands from /help"));
        player.openInventory(inv);
    }

    public static final class MenuHolder implements org.bukkit.inventory.InventoryHolder {
        private Inventory inventory;
        void bind(Inventory inventory) { this.inventory = inventory; }
        @Override public Inventory getInventory() { return inventory; }
    }

    private ItemStack icon(Material mat, String name, String lore) {
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(services.messages().color(name));
        meta.setLore(List.of(services.messages().color(lore)));
        stack.setItemMeta(meta);
        return stack;
    }
}
