package com.drazisil.smarties;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import static com.drazisil.smarties.Smarties.logger;

public class MyListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Welcome!");
    }

//    @EventHandler
//    public void onEntitySpawn(EntitySpawnEvent event) {
//        if (event.getEntity() instanceof Villager) {
//            logger.info("A villager spawned.");
//            VillagerController.addVillager((Villager) event.getEntity());
//
//            logger.info("There are now " + VillagerController.getCount() + " villagers in the VillagerController.");
//
//        }
//    }

    @EventHandler
    public void onVillagerCareerChangeEvent(VillagerCareerChangeEvent event) {
        System.out.println(event.getProfession() + " getting set at " + event.getEntity().getLocation());
        VillagerController.addVillager(event.getEntity(), event.getProfession());
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        // This is required as PlayerInteractEntityEvent fires twice, one for each hand
        if (!(event.getHand() == EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();
        ItemStack itemUsed = player.getInventory().getItemInMainHand();
        Entity clickedEntity = event.getRightClicked();


        if (clickedEntity instanceof Villager) {
            Villager villager = (Villager) clickedEntity;
            // Only add if not already controlled and used the correct tool
            if (!VillagerController.hasVillager(villager)) {
                VillagerController.addVillager(villager, villager.getProfession());
            }

            SmartVillager smartVillager = VillagerController.getSmartVillager(villager.getUniqueId());
            if (smartVillager == null) {
                logger.warning("Unable to locate a villager with id of " + villager.getUniqueId() + " in the VillagerController!");
                return;
            }



            if (VillagerController.isCorrectTool(itemUsed) && player.isSneaking()) {
                smartVillager.brain.toggleTick();
                event.setCancelled(true);
            }


        }
    }
}
