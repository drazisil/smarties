package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;
import org.bukkit.Location;
import org.bukkit.entity.IronGolem;

@SuppressWarnings("SpellCheckingInspection")
public class SpawnGolemTask extends Task {

    public SpawnGolemTask(SmartVillager villager) {
        super(villager);
    }

    public void doTask() {
        Location locationToSpawn = this.villager.getVillager().getLocation().clone();
        try {
            locationToSpawn.getWorld().spawn(locationToSpawn, IronGolem.class);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
