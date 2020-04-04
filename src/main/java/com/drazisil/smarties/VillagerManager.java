package com.drazisil.smarties;

import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class VillagerManager {

    private static ArrayList<VillagerRecord> villagers = new ArrayList<>();

    public static void addVillager(Villager villager) {
        if (!hasVillager(villager)) {
            villagers.add(new VillagerRecord(villager));
            logger.info("Added a villager to the villager manager.");
        } else {
            logger.info("Villager already exists in the villager manager.");
        }

    }

    public static int getCount() {
        return villagers.size();
    }

    private static boolean hasVillager(Villager inVillager) {
        UUID villagerId = inVillager.getUniqueId();
        for (VillagerRecord vr: villagers) {
            if (vr.rawVillager.getUniqueId().equals(villagerId)) {
                return true;
            }
        }
        return false;
    }



    static class VillagerRecord {

        private Villager rawVillager;

        public VillagerRecord(Villager villager) {
            this.rawVillager = villager;
        }


    }
}
