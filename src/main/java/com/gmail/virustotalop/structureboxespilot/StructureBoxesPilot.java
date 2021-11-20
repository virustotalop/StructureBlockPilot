package com.gmail.virustotalop.structureboxespilot;

import io.github.eirikh1996.structureboxes.compat.we6.IWorldEditHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class StructureBoxesPilot extends JavaPlugin {

    private static StructureBoxesPilot instance;

    public static StructureBoxesPilot get() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        this.getLogger().info("Attempting to inject new WorldEditHandler into StructureBoxes");
        if(this.injectHandler()) {
            this.getLogger().info("Successfully injected a new WorldEditHandler into StructureBoxes");
        } else {
            this.getLogger().info("Unable to inject a new WorldEditHandler into StructureBoxes, check console...");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    private boolean injectHandler() {
        try {
            IWorldEditHandler.inject();
            return true;
        } catch(Exception ex) {
            return false;
        }
    }
}