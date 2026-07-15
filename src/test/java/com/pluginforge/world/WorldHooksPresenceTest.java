package com.pluginforge.world;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Offline world-hook presence checks (runs without a live client). */
class WorldHooksPresenceTest {
    @Test
    void domainHasCriticalHooks() throws Exception {
        Path javaRoot = Path.of("src/main/java");
        assertTrue(Files.isDirectory(javaRoot));
        StringBuilder all = new StringBuilder();
        try (Stream<Path> s = Files.walk(javaRoot)) {
            s.filter(p -> p.toString().endsWith("DomainService.java")).forEach(p -> {
                try { all.append(Files.readString(p)); } catch (Exception ignored) {}
            });
        }
        String src = all.toString();
        assertTrue(src.contains("class DomainService"), "DomainService missing");
        String artifact = "storyweaver";
        if (artifact.contains("grave")) {
            assertTrue(src.contains("onPlayerDeath"), "grave death hook missing");
            assertTrue(src.contains("recover"), "grave recover missing");
            assertTrue(src.contains("serializeItems") || src.contains("items"), "grave items persistence missing");
        } else if (artifact.contains("echo")) {
            assertTrue(src.contains("mirrorChat"), "echo chat bridge missing");
            assertTrue(src.contains("watchJoin"), "echo watchJoin missing");
        } else if (artifact.contains("plot")) {
            assertTrue(src.contains("submitPlot") && src.contains("vote"));
        } else if (artifact.contains("cargo")) {
            assertTrue(src.contains("takeContract") && src.contains("deliver"));
        } else if (artifact.contains("chrona")) {
            assertTrue(src.contains("tickEvent"));
        } else if (artifact.contains("story") || artifact.contains("weaver")) {
            assertTrue(src.contains("startDialogue") && src.contains("choose"));
        }
    }
}
