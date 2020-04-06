package com.drazisil.smarties;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class Smarties extends JavaPlugin {

    private static Smarties instance;
    private final Smarties plugin = Smarties.getInstance();
    public static Logger logger;
    private final ArrayList<Villager> worldVillagers = new ArrayList<>();
    private BukkitTask VillagerControllerTick;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        instance = this;
        getServer().getPluginManager().registerEvents(new MyListener(), instance);

        // Start the VillagerController tick
        VillagerControllerTick = Bukkit.getScheduler().runTaskTimer(instance,
                VillagerController::doTick, 20, 20);

        // Enable the VillagerControllerTick
        VillagerController.setTickRunning(true);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Smarties getInstance() {
        return instance;
    }
}
