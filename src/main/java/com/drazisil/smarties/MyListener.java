package com.drazisil.smarties;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.drazisil.smarties.Smarties.logger;

public class MyListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        ArrayList<Villager> worldVillagers = (ArrayList<Villager>) world.getEntitiesByClass(Villager.class);
        logger.info("There are " + worldVillagers.size() + " villagers in " + world.getName());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Welcome!");
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Villager) {
            logger.info("A villager spawned.");
            VillagerManager.addVillager((Villager) event.getEntity());

            logger.info("There are now " + VillagerManager.getCount() + " villagers in the overworld.");

        }
    }

    @EventHandler
    public void onVillagerCareerChangeEvent(VillagerCareerChangeEvent event) {
        System.out.println(event.getProfession() + " getting set at " + event.getEntity().getLocation());
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        // This is required as PlayerInteractEntityEvent fires twice, one for each hand
        if (!(event.getHand() == EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();
        Material itemUsed = player.getInventory().getItemInMainHand().getType();
        Entity clickedEntity = event.getRightClicked();

        // Only convert if not a SmartVillager
        if (clickedEntity instanceof Villager) {
            Villager villager = (Villager) clickedEntity;
            if (itemUsed.equals(Material.STICK) && !VillagerManager.hasVillager(villager)) {
                VillagerManager.addVillager(villager);
            }

        }
    }
}
