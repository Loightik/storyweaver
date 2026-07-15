package com.pluginforge.generated.storyweaver.integration;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/** Lightweight Discord webhook client (no shaded Discord library required). */
public final class DiscordWebhookClient {
    private final JavaPlugin plugin;
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();

    public DiscordWebhookClient(JavaPlugin plugin) { this.plugin = plugin; }

    /**
     * Soft-fail when webhook blank. HTTP is always async when called from the main thread.
     * When already off-main, performs the request on the calling thread.
     */
    public boolean send(String content) {
        String webhook = plugin.getConfig().getString("integrations.discord.webhook", "");
        if (webhook == null || webhook.isBlank()) return false;
        if (Bukkit.isPrimaryThread()) {
            // Queue on async; return true once accepted for delivery.
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> sendHttp(webhook, content));
            return true;
        }
        return sendHttp(webhook, content);
    }

    private boolean sendHttp(String webhook, String content) {
        try {
            String body = "{\"content\":\"" + escape(content) + "\"}";
            HttpRequest req = HttpRequest.newBuilder(URI.create(webhook))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.statusCode() >= 200 && resp.statusCode() < 300;
        } catch (Exception e) {
            plugin.getLogger().warning("Discord webhook failed: " + e.getMessage());
            return false;
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
