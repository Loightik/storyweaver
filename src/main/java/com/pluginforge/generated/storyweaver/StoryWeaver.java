package com.pluginforge.generated.storyweaver;

import com.pluginforge.generated.storyweaver.api.StoryWeaverAPI;
import com.pluginforge.generated.storyweaver.command.RootCommand;
import com.pluginforge.generated.storyweaver.gui.MenuListener;
import com.pluginforge.generated.storyweaver.listener.CoreListener;
import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import com.pluginforge.generated.storyweaver.storage.YamlStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * StoryWeaver - Loightik
 * Architecture: API + services + storage.
 * Root command: /storyweaver with subcommands admin|reload|help
 */
public final class StoryWeaver extends JavaPlugin {
    private static StoryWeaver instance;
    private StoryWeaverAPI api;
    private ServiceRegistry services;
    private YamlStorage storage;
    private CoreListener coreListener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveResource("messages.yml", false);
        this.storage = new YamlStorage(this);
        this.services = new ServiceRegistry(this, storage);
        this.services.boot();
        this.api = new StoryWeaverAPI(services);

        this.coreListener = new CoreListener(services);
        services.attachCoreListener(coreListener);
        Bukkit.getPluginManager().registerEvents(coreListener, this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(services), this);
        bind("storyweaver", new RootCommand(services));
        getLogger().info("StoryWeaver enabled | concept=storynpc | integrations=none | author=Loightik");
    }

    @Override
    public void onDisable() {
        // Bukkit auto-clears listeners registered for this plugin; HandlerList.unregisterAll(this)
        // is not required for ordinary Listener instances bound via PluginManager.
        if (coreListener != null) coreListener.cancelTasks();
        if (services != null) services.shutdown();
        if (storage != null) storage.saveAll();
    }

    public static StoryWeaver get() { return instance; }
    public StoryWeaverAPI api() { return api; }
    public ServiceRegistry services() { return services; }

    private void bind(String name, org.bukkit.command.CommandExecutor executor) {
        PluginCommand cmd = getCommand(name);
        if (cmd == null) {
            getLogger().severe("Missing command in plugin.yml: " + name);
            return;
        }
        cmd.setExecutor(executor);
        if (executor instanceof org.bukkit.command.TabCompleter tab) {
            cmd.setTabCompleter(tab);
        }
    }
}
