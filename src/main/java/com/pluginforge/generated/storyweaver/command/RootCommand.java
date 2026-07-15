package com.pluginforge.generated.storyweaver.command;

import com.pluginforge.generated.storyweaver.gui.MainMenu;
import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class RootCommand implements CommandExecutor, TabCompleter {
    private final ServiceRegistry services;
    private final AdminCommand admin;
    private final ReloadCommand reload;

    public RootCommand(ServiceRegistry services) {
        this.services = services;
        this.admin = new AdminCommand(services);
        this.reload = new ReloadCommand(services);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                new MainMenu(services).open(player);
            } else {
                services.messages().send(sender, "help.header");
            }
            return true;
        }
        String sub = args[0].toLowerCase(Locale.ROOT);
        String[] rest = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];
        return switch (sub) {
            case "help" -> {
                services.messages().send(sender, "help.header");
                services.messages().send(sender, "help.menu");
                services.messages().send(sender, "help.admin");
                services.messages().send(sender, "help.reload");
                yield true;
            }
            case "admin" -> admin.onCommand(sender, command, label, rest);
            case "reload" -> reload.onCommand(sender, command, label, rest);
            case "recover", "submit", "contract", "deliver" -> domainShortcut(sender, args);

            default -> {
                services.messages().send(sender, "error.unknown-subcommand");
                yield true;
            }
        };
    }

    private boolean domainShortcut(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            services.messages().send(sender, "common.player-only");
            return true;
        }
        try {
            if (args[0].equalsIgnoreCase("recover") && args.length >= 2) {
                var m = services.domain().getClass().getMethod("recover", Player.class, String.class);
                m.invoke(services.domain(), player, args[1]);
            } else if (args[0].equalsIgnoreCase("submit") && args.length >= 2) {
                var m = services.domain().getClass().getMethod("submitPlot", Player.class, String.class);
                m.invoke(services.domain(), player, args[1]);
            } else if (args[0].equalsIgnoreCase("contract") && args.length >= 2) {
                var m = services.domain().getClass().getMethod("takeContract", Player.class, String.class);
                m.invoke(services.domain(), player, args[1]);
            } else if (args[0].equalsIgnoreCase("deliver")) {
                var m = services.domain().getClass().getMethod("deliver", Player.class);
                m.invoke(services.domain(), player);
            } else {
                new MainMenu(services).open(player);
            }
        } catch (ReflectiveOperationException ignored) {
            new MainMenu(services).open(player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String p = args[0].toLowerCase(Locale.ROOT);
            return List.of("admin", "reload", "help").stream()
                    .filter(s -> s.startsWith(p))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin")) {
            String p = args[1].toLowerCase(Locale.ROOT);
            return List.of("watch", "notify").stream()
                    .filter(s -> s.startsWith(p))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return List.of();
    }
}
