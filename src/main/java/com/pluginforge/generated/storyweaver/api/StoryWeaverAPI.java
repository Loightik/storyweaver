package com.pluginforge.generated.storyweaver.api;

import com.pluginforge.generated.storyweaver.service.ServiceRegistry;
import org.bukkit.entity.Player;

/**
 * Public API for other plugins - similar spirit to LuckPerms API surfaces.
 */
public final class StoryWeaverAPI {
    private final ServiceRegistry services;

    public StoryWeaverAPI(ServiceRegistry services) {
        this.services = services;
    }

    public String conceptId() {
        return "storynpc";
    }

    public int profileScore(Player player) {
        return services.profiles().score(player.getUniqueId());
    }

    public void bumpScore(Player player, int delta, String reason) {
        services.profiles().bump(player.getUniqueId(), delta, reason);
    }

    public boolean notifyStaff(String message) {
        return services.notifyExternal(message);
    }
}
