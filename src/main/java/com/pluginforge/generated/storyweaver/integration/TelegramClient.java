package com.pluginforge.generated.storyweaver.integration;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/** Telegram Bot API notify helper. */
public final class TelegramClient {
    private final JavaPlugin plugin;
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();

    public TelegramClient(JavaPlugin plugin) { this.plugin = plugin; }

    /** Soft-fail when token/chat blank. HTTP hops off the main thread when needed. */
    public boolean send(String content) {
        String token = plugin.getConfig().getString("integrations.telegram.bot-token", "");
        String chat = plugin.getConfig().getString("integrations.telegram.chat-id", "");
        if (token == null || token.isBlank() || chat == null || chat.isBlank()) return false;
        if (Bukkit.isPrimaryThread()) {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> sendHttp(token, chat, content));
            return true;
        }
        return sendHttp(token, chat, content);
    }

    private boolean sendHttp(String token, String chat, String content) {
        try {
            String url = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id="
                    + URLEncoder.encode(chat, StandardCharsets.UTF_8)
                    + "&text=" + URLEncoder.encode(content == null ? "" : content, StandardCharsets.UTF_8);
            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(10)).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.statusCode() >= 200 && resp.statusCode() < 300;
        } catch (Exception e) {
            plugin.getLogger().warning("Telegram send failed: " + e.getMessage());
            return false;
        }
    }
}
