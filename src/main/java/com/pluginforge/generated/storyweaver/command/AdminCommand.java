package com.pluginforge.generated.storyweaver.command;

import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class AdminCommand implements CommandExecutor {
    private final ServiceRegistry services;

    public AdminCommand(ServiceRegistry services) { this.services = services; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(services.plugin().getName().toLowerCase().replace(" ", "") + ".admin")) {
            services.messages().send(sender, "common.no-permission");
            return true;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("watch")) {
            Player t = Bukkit.getPlayerExact(args[1]);
            if (t == null) {
                services.messages().send(sender, "admin.offline");
                return true;
            }
            services.profiles().setWatched(t.getUniqueId(), true);
            services.messages().send(sender, "admin.watching", java.util.Map.of("player", t.getName()));
            return true;
        }
        if (args.length >= 1 && args[0].equalsIgnoreCase("notify")) {
            if (args.length < 2) {
                services.messages().send(sender, "admin.notify-empty");
                return true;
            }
            String msg = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)).trim();
            if (msg.isEmpty()) {
                services.messages().send(sender, "admin.notify-empty");
                return true;
            }
            if (!services.integrationsEnabled()) {
                services.messages().send(sender, "integrations.disabled");
                return true;
            }
            final CommandSender notifySender = sender;
            final String notifyMsg = msg;
            services.plugin().getServer().getScheduler().runTaskAsynchronously(services.plugin(), () -> {
                boolean ok = services.notifyExternal(notifyMsg);
                services.plugin().getServer().getScheduler().runTask(services.plugin(), () ->
                        services.messages().send(notifySender, ok ? "admin.sent" : "admin.notify-failed"));
            });
            return true;
        }
        services.messages().send(sender, "admin.usage");
        return true;
    }
}
