package com.gmail.virustotalop.structureboxespilot;

import io.github.eirikh1996.structureboxes.compat.we6.IWorldEditHandler;
import io.github.eirikh1996.structureboxes.StructureBoxes;
import io.github.eirikh1996.structureboxes.localisation.I18nSupport;
import io.github.eirikh1996.structureboxes.settings.Settings;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class StructureBoxesPilot extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("Attempting to inject new WorldEditHandler into StructureBoxes");
        if(this.injectHandler()) {
            this.getLogger().info("Successfully injected a new WorldEditHandler into StructureBoxes");
        } else {
            this.getLogger().info("Unable to inject a new WorldEditHandler into StructureBoxes, check console...");
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