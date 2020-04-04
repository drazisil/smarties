package com.drazisil.smarties;

import com.drazisil.smarties.ai.Brain;
import com.drazisil.smarties.ai.task.SpawnGolemTask;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class VillagerController {

    private static final ArrayList<SmartVillager> villagers = new ArrayList<>();
    private static boolean tickRunning = false;

    public static void addVillager(Villager villager) {
        if (!hasVillager(villager)) {

            // Disable vanilla AI
            villager.setAI(false);

            // Make villager invulnerable for testing.
            villager.setInvulnerable(true);


            villagers.add(new SmartVillager(villager));
            logger.info("Added a villager to the villager manager.");
        } else {
            logger.warning("Villager already exists in the villager manager.");
        }

    }

    public static boolean hasVillager(Villager inVillager) {
        UUID villagerId = inVillager.getUniqueId();
        for (SmartVillager vr: villagers) {
            if (vr.villager.getUniqueId().equals(villagerId)) {
                return true;
            }
        }
        return false;
    }

    public static SmartVillager getSmartVillager(UUID id) {
        for (SmartVillager vr: villagers) {
            if (vr.villager.getUniqueId().equals(id)) {
                return vr;
            }
        }
        return null;
    }

    public static void doTick() {
        if (!tickRunning) return;

        logger.info("Running tick for VillagerController..");

        for (SmartVillager villager: villagers) {
            villager.brain.livingTick();
        }

        logger.info("Running VillagerController tick complete.");

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

    public static class SmartVillager {

        private Villager villager;
        public Brain brain;

        public SmartVillager(Villager villager) {
            this.villager = villager;
            this.brain = new Brain(this.villager);
            this.brain.addTask(new SpawnGolemTask(this));
        }

        public Villager getVillager() {
            return this.villager;
        }

    }
}
