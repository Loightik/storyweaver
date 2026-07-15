package com.pluginforge.generated.storyweaver.gui;

import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class MenuListener implements Listener {
    private final ServiceRegistry services;
    public MenuListener(ServiceRegistry services) { this.services = services; }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player)) return;
        if (event.getInventory().getHolder() instanceof com.pluginforge.generated.storyweaver.gui.MainMenu.MenuHolder) {
            event.setCancelled(true);
        }
    }
}
