package com.pluginforge.generated.storyweaver.service;

public final class DomainService {
    private final ServiceRegistry services;
    private final String conceptId;

    public DomainService(ServiceRegistry services, String conceptId) {
        this.services = services;
        this.conceptId = conceptId;
    }

    /** Boot domain logic after config/messages load. */
    public void boot() { services.plugin().getLogger().info("Domain boot: " + conceptId); }
    /** Shutdown domain; cancel concept timers via listener if present. */
    public void shutdown() {}
    /** Hot-reload domain caches after config change. */
    public void reload() {}
    /** Concept id this domain implements. */
    public String conceptId() { return conceptId; }

    public void startDialogue(org.bukkit.entity.Player player, String npcId) {
            if (player == null || !player.isOnline()) return;
        services.storage().set("dialogue." + player.getUniqueId() + ".npc", npcId);
        services.storage().set("dialogue." + player.getUniqueId() + ".node", "start");
        services.storage().saveAll();
        services.messages().send(player, "story.started", java.util.Map.of("npc", npcId));
    }

    public boolean choose(org.bukkit.entity.Player player, String option) {
        String path = "dialogue." + player.getUniqueId();
        if (!services.storage().contains(path + ".npc")) {
            services.messages().send(player, "story.none");
            return false;
        }
        services.storage().set(path + ".node", option);
        services.profiles().bump(player.getUniqueId(), 1, "story-" + option);
        services.storage().saveAll();
        services.messages().send(player, "story.choice", java.util.Map.of("option", option));
        return true;
    }
}
