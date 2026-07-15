package com.pluginforge.generated.storyweaver.util;

import org.bukkit.ChatColor;

public final class Text {
    private Text() {}
    public static String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in == null ? "" : in);
    }
}
