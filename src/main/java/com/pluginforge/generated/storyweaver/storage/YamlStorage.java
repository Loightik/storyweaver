package com.pluginforge.generated.storyweaver.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class YamlStorage {
    private final JavaPlugin plugin;
    private final File file;
    private FileConfiguration yaml;

    public YamlStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "data.yml");
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().warning(e.getMessage());
            }
        }
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public void saveAll() {
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            yaml.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("YamlStorage save failed: " + e.getMessage());
        }
    }

    public long getLong(String path, long def) {
        Object raw = yaml.get(path);
        if (raw instanceof Number n) return n.longValue();
        if (raw != null) {
            try { return Long.parseLong(String.valueOf(raw).trim()); }
            catch (Exception ignored) {}
        }
        return def;
    }

    public void set(String path, Object value) { yaml.set(path, value); }
    public boolean contains(String path) { return yaml.contains(path); }
    public String getString(String path) { return yaml.getString(path); }
    public int getInt(String path, int def) { return yaml.getInt(path, def); }
    public boolean getBoolean(String path, boolean def) { return yaml.getBoolean(path, def); }
}
