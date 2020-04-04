package com.drazisil.smarties;

import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class Smarties extends JavaPlugin {

    public static Logger logger;
    private ArrayList<Villager> worldVillagers = new ArrayList<Villager>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        getServer().getPluginManager().registerEvents(new MyListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
