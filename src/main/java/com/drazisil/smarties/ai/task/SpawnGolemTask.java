package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.VillagerController;
import org.bukkit.Location;
import org.bukkit.entity.IronGolem;

public class SpawnGolemTask {

    private final VillagerController.SmartVillager smartVillager;

    public SpawnGolemTask(VillagerController.SmartVillager villager) {
        this.smartVillager = villager;
    }

    public void doTask() {
        Location locationToSpawn = this.smartVillager.getVillager().getLocation().clone();
        locationToSpawn.getWorld().spawn(locationToSpawn, IronGolem.class);
    }
}
