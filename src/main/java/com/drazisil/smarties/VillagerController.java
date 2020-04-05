package com.drazisil.smarties;


import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class VillagerController {

    private static final ArrayList<SmartVillager> villagers = new ArrayList<>();
    private static boolean tickRunning = false;

    public static void addVillager(Villager villager, Villager.Profession profession) {
        if (!hasVillager(villager)) {

            // Disable vanilla AI
            villager.setAI(false);

            // Make villager invulnerable for testing.
            villager.setInvulnerable(true);

            SmartVillager smartVillager = new SmartVillager(villager, profession);
            villagers.add(smartVillager);
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

//        logger.info("Running tick for VillagerController..");

        for (SmartVillager villager: villagers) {
            villager.brain.livingTick();
        }

//        logger.info("Running VillagerController tick complete.");

    }

    public static void setTickRunning(boolean shouldRun) {
        tickRunning = shouldRun;
    }

    public static int getCount() {
        return villagers.size();
    }

    public static boolean isCorrectTool(ItemStack tool) {

        if (tool.hasItemMeta()) {
            ItemMeta toolMeta = tool.getItemMeta();
            assert toolMeta != null;
            if (toolMeta.hasDisplayName()) {
                return (toolMeta.getDisplayName().equals("+2 Int"));

            }
        }
        return false;
    }

}
