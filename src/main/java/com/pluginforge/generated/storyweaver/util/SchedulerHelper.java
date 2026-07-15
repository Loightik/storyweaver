package com.pluginforge.generated.storyweaver.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class SchedulerHelper {
    private SchedulerHelper() {}
    public static BukkitTask later(JavaPlugin plugin, Runnable r, long delay) {
        return plugin.getServer().getScheduler().runTaskLater(plugin, r, delay);
    }
    public static BukkitTask async(JavaPlugin plugin, Runnable r) {
        return plugin.getServer().getScheduler().runTaskAsynchronously(plugin, r);
    }
}
