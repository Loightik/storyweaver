package com.pluginforge.generated.storyweaver.listener;

import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public final class CoreListener implements Listener {
    private final ServiceRegistry services;
    private org.bukkit.scheduler.BukkitTask chronaTask;

    public CoreListener(ServiceRegistry services) {
        this.services = services;
        if ("chrona".equals(services.domain().conceptId())) {
            chronaTask = new BukkitRunnable() {
                @Override public void run() {
                    try {
                        services.domain().getClass().getMethod("tickEvent").invoke(services.domain());
                    } catch (ReflectiveOperationException ex) {
                        services.plugin().getLogger().warning("tickEvent: " + ex.getMessage());
                    }
                }
            }.runTaskTimer(services.plugin(), 100L, 100L);
        }
    }

    /** Cancel repeating timers (called from DomainService.shutdown / quality gate). */
    public void cancelTasks() {
        if (chronaTask != null) {
            chronaTask.cancel();
            chronaTask = null;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try {
            services.domain().getClass().getMethod("watchJoin", org.bukkit.entity.Player.class)
                    .invoke(services.domain(), event.getPlayer());
        } catch (ReflectiveOperationException ex) {
            services.plugin().getLogger().warning("watchJoin: " + ex.getMessage());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        try {
            services.domain().getClass().getMethod("onPlayerQuit", org.bukkit.entity.Player.class)
                    .invoke(services.domain(), event.getPlayer());
        } catch (ReflectiveOperationException ignored) {
            // optional concept hook
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        final org.bukkit.entity.Player player = event.getPlayer();
        final String message = event.getMessage();
        // Never do HTTP on the async chat thread
        services.plugin().getServer().getScheduler().runTask(services.plugin(), () -> {
            try {
                services.domain().getClass().getMethod("mirrorChat", org.bukkit.entity.Player.class, String.class)
                        .invoke(services.domain(), player, message);
            } catch (ReflectiveOperationException ex) {
                services.plugin().getLogger().warning("mirrorChat: " + ex.getMessage());
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        if (!"gravequest".equals(services.domain().conceptId())) return;
        try {
            services.domain().getClass()
                    .getMethod("onPlayerDeath", org.bukkit.entity.Player.class, org.bukkit.Location.class)
                    .invoke(services.domain(), event.getEntity(), event.getEntity().getLocation());
            event.getDrops().clear();
            event.setDroppedExp(0);
        } catch (ReflectiveOperationException ex) {
            services.plugin().getLogger().warning("onPlayerDeath failed: " + ex.getMessage());
        }
    }
}
