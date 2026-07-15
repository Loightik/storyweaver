package com.pluginforge.generated.storyweaver.command;

import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class ReloadCommand implements CommandExecutor {
    private final ServiceRegistry services;
    public ReloadCommand(ServiceRegistry services) { this.services = services; }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(services.plugin().getName().toLowerCase().replace(" ", "") + ".reload")
                && !sender.hasPermission(services.plugin().getName().toLowerCase().replace(" ", "") + ".admin")) {
            services.messages().send(sender, "common.no-permission");
            return true;
        }
        services.reloadAll();
        services.messages().send(sender, "common.reloaded");
        return true;
    }
}
