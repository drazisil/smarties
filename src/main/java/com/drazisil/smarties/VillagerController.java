package com.drazisil.smarties;

import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class VillagerController {

    private static ArrayList<SmartVillager> villagers = new ArrayList<>();
    private static boolean tickRunning = false;

    public static void addVillager(Villager villager) {
        if (!hasVillager(villager)) {

            // Disable vanilla AI
            villager.setAI(false);

            // Make villager invulnerable for testing.
            villager.setInvulnerable(true);

            SmartVillager smartVillager = new SmartVillager(villager);
            villagers.add(new SmartVillager(villager));
            logger.info("Added a villager to the villager manager.");

        } else {
            logger.warning("Villager already exists in the villager manager.");
        }

    }

    public static boolean hasVillager(Villager inVillager) {
        UUID villagerId = inVillager.getUniqueId();
        for (SmartVillager vr: villagers) {
            if (vr.getVillager().getUniqueId().equals(villagerId)) {
                return true;
            }
        }
        return false;
    }

    public static SmartVillager getSmartVillager(UUID id) {
        for (SmartVillager vr: villagers) {
            if (vr.getVillager().getUniqueId().equals(id)) {
                return vr;
            }
        }
        return null;
    }

    public static void doTick() {
        if (!tickRunning) return;

        logger.fine("Running tick for VillagerController..");

        for (SmartVillager villager: villagers) {
            villager.brain.livingTick();
        }

        logger.fine("Running VillagerController tick complete.");

    }

    public static void toggleTick() {
        tickRunning = !tickRunning;
    }

    public static void setTickRunning(boolean shouldRun) {
        tickRunning = shouldRun;
    }

    public static int getCount() {
        return villagers.size();
    }

}
