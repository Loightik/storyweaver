package com.pluginforge.generated.storyweaver.service;

import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigService {
    private final JavaPlugin plugin;

    public ConfigService(JavaPlugin plugin) { this.plugin = plugin; }
    public void reload() { plugin.reloadConfig(); }
    /** Language for messages.yml sections: en or ru. */
    public String language() {
        String locale = plugin.getConfig().getString("language",
                plugin.getConfig().getString("locale", "en"));
        if (locale == null || locale.isBlank()) return "en";
        return locale.trim().toLowerCase(java.util.Locale.ROOT);
    }
    public boolean discordEnabled() { return plugin.getConfig().getBoolean("integrations.discord.enabled", false); }
    public boolean telegramEnabled() { return plugin.getConfig().getBoolean("integrations.telegram.enabled", false); }
    public boolean bridgeChat() { return plugin.getConfig().getBoolean("bridge.chat", true); }
    public String discordWebhook() { return plugin.getConfig().getString("integrations.discord.webhook", ""); }
    public String telegramToken() { return plugin.getConfig().getString("integrations.telegram.bot-token", ""); }
    public String telegramChatId() { return plugin.getConfig().getString("integrations.telegram.chat-id", ""); }
    public long graveTtlMs() { return plugin.getConfig().getLong("gravequest.ttl-seconds", 900) * 1000L; }
    public int cargoPayout() { return plugin.getConfig().getInt("cargoroute.payout", 120); }
    public int eventEveryTicks() { return plugin.getConfig().getInt("chrona.every-ticks", 12000); }
    public String randomEvent() {
        var list = plugin.getConfig().getStringList("chrona.events");
        if (list == null || list.isEmpty()) return "supply-drop";
        return list.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(list.size()));
    }
}
