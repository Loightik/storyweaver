package com.pluginforge.generated.storyweaver.service;

import com.pluginforge.generated.storyweaver.storage.YamlStorage;

import java.util.UUID;

public final class PlayerProfileService {
    private final YamlStorage storage;

    public PlayerProfileService(YamlStorage storage) { this.storage = storage; }
    public void load() {}
    public void save() { storage.saveAll(); }
    public int score(UUID id) { return storage.getInt("profiles." + id + ".score", 0); }
    public void bump(UUID id, int delta, String reason) {
        storage.set("profiles." + id + ".score", Math.max(0, score(id) + delta));
        storage.set("profiles." + id + ".last-reason", reason);
        storage.saveAll();
    }
    public boolean isWatched(UUID id) { return storage.getBoolean("profiles." + id + ".watched", false); }
    public void setWatched(UUID id, boolean watched) {
        storage.set("profiles." + id + ".watched", watched);
        storage.saveAll();
    }
}
