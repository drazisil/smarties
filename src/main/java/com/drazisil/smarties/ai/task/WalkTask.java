package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;
import com.drazisil.smarties.ai.pathfinding.PathMapper;
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
        currentPathNode.populateChildNodes();

        logger.warning("Current: " + currentPathNode.toString());
        logger.warning("Children nodes:");
        for (PathNode node: currentPathNode.getChildNodes()) {
            logger.warning(node.toString());
        }

        PathNode targetPathNode = new PathNode(new Location(villagerLocation.getWorld(), 100, 54, 100));
//        targetPathNode.populateChildNodes();

        logger.warning("Target: " + targetPathNode.toString());

        logger.warning(String.format("Distance between the two points: X= %d, Y= %d, Z= %d",
                PathMapper.getDistance(currentPathNode.getX(), targetPathNode.getX()),
                PathMapper.getDistance(currentPathNode.getY(), targetPathNode.getY()),
                PathMapper.getDistance(currentPathNode.getZ(), targetPathNode.getZ())));

//        logger.warning("Children nodes:");
//        for (PathNode node: targetPathNode.getChildren()) {
//            logger.warning(node.toString());
//        }
//
//        PathMapper pathMapper = new PathMapper(currentPathNode, targetPathNode);
//        PathNode weightedNode = null;
//        try {
//            weightedNode = pathMapper.weightChildNodes();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        logger.warning("Weighted: " + weightedNode.toString());
//        logger.warning("Children nodes:");
//        for (PathNode node: weightedNode.getChildren()) {
//            logger.warning(node.toString());
//        }

        this.villager.brain.setTickRunning(false);
    }


}
