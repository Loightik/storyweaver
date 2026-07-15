package com.pluginforge.generated.storyweaver.service;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public final class MessageService {
    private final JavaPlugin plugin;
    private FileConfiguration yaml;

    public MessageService(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) plugin.saveResource("messages.yml", false);
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public String raw(String path) {
        if (path == null || path.isBlank()) return "";
        String locale = plugin.getConfig().getString("language",
                plugin.getConfig().getString("locale", "en"));
        if (locale == null || locale.isBlank()) locale = "en";
        locale = locale.trim().toLowerCase(java.util.Locale.ROOT);
        String value = yaml.getString(locale + "." + path);
        if (value == null) value = yaml.getString("en." + path, path);
        String prefix = color(plugin.getConfig().getString("prefix", "&8[&bPF&8]&r "));
        return color(value.replace("{prefix}", prefix));
    }

    public void send(CommandSender sender, String path) {
        if (sender == null) return;
        sender.sendMessage(raw(path));
    }

    public void send(CommandSender sender, String path, Map<String, String> vars) {
        if (sender == null) return;
        String text = raw(path);
        if (vars != null) {
            for (var e : vars.entrySet()) {
                String v = e.getValue() == null ? "" : e.getValue();
                text = text.replace("{" + e.getKey() + "}", v);
            }
        }
        sender.sendMessage(text);
    }

    public String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in == null ? "" : in);
    }
}
