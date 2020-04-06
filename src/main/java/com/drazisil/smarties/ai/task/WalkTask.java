package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;
import com.drazisil.smarties.ai.pathfinding.PathNode;
import org.bukkit.Location;

import static com.drazisil.smarties.Smarties.logger;

public class WalkTask extends Task {
    public WalkTask(SmartVillager villager) {
        super(villager);
    }

    @Override
    public void doTask() {

        Location villagerLocation = this.villager.getVillager().getLocation();

        PathNode currentPathNode = new PathNode(villagerLocation);
        currentPathNode.calculateChildren();

        logger.warning("Current: " + currentPathNode.toString());
        logger.warning("Children nodes:");
        for (PathNode node: currentPathNode.getChildren()) {
            logger.warning(node.toString());
        }

        PathNode targetPathNode = new PathNode(new Location(villagerLocation.getWorld(), 100, 54, 100));
        targetPathNode.calculateChildren();

        logger.warning("Target: " + targetPathNode.toString());
        logger.warning("Children nodes:");
        for (PathNode node: targetPathNode.getChildren()) {
            logger.warning(node.toString());
        }


        this.villager.brain.setTickRunning(false);
    }


}
