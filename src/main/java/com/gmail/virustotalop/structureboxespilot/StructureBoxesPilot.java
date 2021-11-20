package com.gmail.virustotalop.structureboxespilot;

import com.gmail.virustotalop.structureboxespilot.worldedit.PilotWorldEditHandler;
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
        //WorldEditHandler
        Plugin worldEditPlugin = this.getServer().getPluginManager().getPlugin("WorldEdit");
        final Map data;
        try {
            File weConfig = new File(worldEditPlugin.getDataFolder(), "config" + (Settings.FAWE ? "-legacy" : "") + ".yml");
            Yaml yaml = new Yaml();
            data = yaml.load(new FileInputStream(weConfig));
        } catch (IOException e){
            getLogger().severe(I18nSupport.getInternationalisedString("Startup - Error reading WE config"));
            e.printStackTrace();
            return false;
        }
        File schemDir = new File(worldEditPlugin.getDataFolder(), (String) ((Map) data.get("saving")).get("dir"));
        StructureBoxes structureBoxes = (StructureBoxes) this.getServer()
                .getPluginManager()
                .getPlugin("StructureBoxes");
        try {
            Field field = structureBoxes.getClass().getDeclaredField("worldEditHandler");
            field.setAccessible(true);
            field.set(structureBoxes, new PilotWorldEditHandler(schemDir, structureBoxes));
            return true;
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}