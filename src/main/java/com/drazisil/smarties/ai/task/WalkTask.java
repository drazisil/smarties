package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;
import com.drazisil.smarties.ai.pathfinding.PathBlockNode;
import com.drazisil.smarties.ai.pathfinding.PathChunkNode;
import com.drazisil.smarties.ai.pathfinding.PathMapper;
import org.bukkit.Location;

import static com.drazisil.smarties.Smarties.logger;

public class WalkTask extends Task {
    public WalkTask(SmartVillager villager) {
        super(villager);
    }

    @Override
    public void doTask() {

        Location villagerLocation = this.villager.getVillager().getLocation();

        PathBlockNode currentPathBlockNode = new PathBlockNode(villagerLocation);
        currentPathBlockNode.populateChildNodes();

        PathChunkNode currentChunk = currentPathBlockNode.getChunk();
        currentChunk.populateChildNodes();


        logger.warning("Current: " + currentPathBlockNode.toString());
        logger.warning("Children nodes:");
        for (PathBlockNode node: currentPathBlockNode.getChildNodes()) {
            logger.warning(node.toString());
        }

        logger.warning("Current Chunk: " + currentChunk.toString());
        logger.warning("Children nodes:");
        for (PathChunkNode node: currentChunk.getChildNodes()) {
            logger.warning(node.toString());
        }

        PathBlockNode targetPathBlockNode = new PathBlockNode(new Location(villagerLocation.getWorld(), 128, 70, -364));
//        targetPathNode.populateChildNodes();
        PathChunkNode targetChunk = targetPathBlockNode.getChunk();

        logger.warning("Target: " + targetPathBlockNode.toString());
        logger.warning("Target Chunk: " + targetChunk.toString());

        logger.warning(String.format("Distance between the two points: X= %d, Y= %d, Z= %d",
                PathMapper.getDistance(currentPathBlockNode.getX(), targetPathBlockNode.getX()),
                PathMapper.getDistance(currentPathBlockNode.getY(), targetPathBlockNode.getY()),
                PathMapper.getDistance(currentPathBlockNode.getZ(), targetPathBlockNode.getZ())));

        logger.warning(String.format("Distance between the two chunks: X= %d, Z= %d",
                PathMapper.getDistance(currentPathBlockNode.getChunk().getX(),
                        targetPathBlockNode.getChunk().getX()),
                PathMapper.getDistance(currentPathBlockNode.getChunk().getZ(),
                        targetPathBlockNode.getChunk().getZ())));

        PathBlockNode suggestedNextBlock = new PathBlockNode(1,
                currentPathBlockNode.getWorld(),
                PathMapper.getNextClosestNumber(currentPathBlockNode.getX(), targetPathBlockNode.getX()),
                PathMapper.getNextClosestNumber(currentPathBlockNode.getY(), targetPathBlockNode.getY()),
                PathMapper.getNextClosestNumber(currentPathBlockNode.getZ(), targetPathBlockNode.getZ()));

        logger.warning(String.format("Suggested next block: %s", suggestedNextBlock.toString()));

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
