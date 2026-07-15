package com.pluginforge.generated.storyweaver.service;

import com.pluginforge.generated.storyweaver.integration.DiscordWebhookClient;
import com.pluginforge.generated.storyweaver.integration.TelegramClient;
import com.pluginforge.generated.storyweaver.listener.CoreListener;
import com.pluginforge.generated.storyweaver.storage.YamlStorage;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServiceRegistry {
    private final JavaPlugin plugin;
    private final YamlStorage storage;
    private final ConfigService config;
    private final MessageService messages;
    private final PlayerProfileService profiles;
    private final DomainService domain;
    private DiscordWebhookClient discord;
    private TelegramClient telegram;
    private CoreListener coreListener;

    public ServiceRegistry(JavaPlugin plugin, YamlStorage storage) {
        this.plugin = plugin;
        this.storage = storage;
        this.config = new ConfigService(plugin);
        this.messages = new MessageService(plugin);
        this.profiles = new PlayerProfileService(storage);
        this.domain = new DomainService(this, "storynpc");
    }

    public void boot() {
        config.reload();
        messages.reload();
        profiles.load();
        domain.boot();
    }

    public void shutdown() {
        if (coreListener != null) coreListener.cancelTasks();
        domain.shutdown();
        profiles.save();
    }

    public void reloadAll() {
        plugin.reloadConfig();
        config.reload();
        messages.reload();
        domain.reload();
    }

    public void attachDiscord(DiscordWebhookClient client) { this.discord = client; }
    public void attachTelegram(TelegramClient client) { this.telegram = client; }
    public void attachCoreListener(CoreListener listener) { this.coreListener = listener; }

    /** True when at least one external bridge is enabled and attached. */
    public boolean integrationsEnabled() {
        boolean d = discord != null && config.discordEnabled();
        boolean t = telegram != null && config.telegramEnabled();
        return d || t;
    }

    public boolean notifyExternal(String message) {
        boolean ok = false;
        if (discord != null && config.discordEnabled()) {
            ok |= discord.send(message);
        }
        if (telegram != null && config.telegramEnabled()) {
            ok |= telegram.send(message);
        }
        return ok;
    }

    public JavaPlugin plugin() { return plugin; }
    public ConfigService config() { return config; }
    public MessageService messages() { return messages; }
    public PlayerProfileService profiles() { return profiles; }
    public DomainService domain() { return domain; }
    public YamlStorage storage() { return storage; }
    public DiscordWebhookClient discord() { return discord; }
    public TelegramClient telegram() { return telegram; }
}
