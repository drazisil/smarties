package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.block.Block;

import java.util.ArrayList;

/**
 * Provided methods to map paths between two {@link PathBlockNode} objects.
 */
public class PathMapper {

    private final PathBlockNode sourceNode;
    private final PathBlockNode targetNode;

    public PathMapper(PathBlockNode sourceNode, PathBlockNode targetNode) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }

    public PathBlockNode weightChildNodes() throws Exception {

        PathBlockNode weightedNode = this.sourceNode.clone();

        if (targetNode == null) {
            throw new Exception("No targetNode, unable to weight");
        }

        ArrayList<PathBlockNode> children = weightedNode.getChildNodes();
        for (PathBlockNode node : children) {
            if (node.getX() == targetNode.getX()) node.addWeight();
            if (node.getY() == targetNode.getY()) node.addWeight();
            if (node.getZ() == targetNode.getZ()) node.addWeight();

        }

        return weightedNode;
    }

    public static Block getBlockAtPathNode(PathBlockNode node) {
        return node.getWorld().getBlockAt((int)node.getX(), node.getY(), (int)node.getZ());
    }



//    public PathNodeSet weightByDistance(PathNode targetNode, PathNode node1, PathNode node2) {
//    }
//
    public static int getDistance(double one, double two) {
        int result;
        if (one < 0 && two >= 0) result = (Math.abs((int)one) + (int)two);
        else if (one < 0) result = (Math.abs((int)one - (int)two));
        else if (two < 0) result = ((int)one + Math.abs((int)two));
        else result = (Math.abs((int)one - (int)two));
        return result;
    }

    public static int getNextClosestNumber(int sourceNumber, int targetNumber) {
        if (sourceNumber < 0 && targetNumber >= 0) return sourceNumber + 1;
        else if (sourceNumber >=0 && targetNumber < 0) return sourceNumber - 1;
        else if (sourceNumber < 0 && targetNumber < 0) {
            if (sourceNumber > targetNumber) return sourceNumber - 1;
            else if (targetNumber > sourceNumber) return sourceNumber + 1;
        }
        else if (sourceNumber >= 0 && targetNumber >= 0) {
            if (sourceNumber > targetNumber) return sourceNumber + 1;
            else if (targetNumber > sourceNumber) return sourceNumber - 1;
        }
        return sourceNumber;
    }

    public static PathBlockNode centerOnBlock(PathBlockNode node) {
        PathBlockNode centeredNode = node.clone();
        centeredNode.setX(node.getX() + 0.5);
        centeredNode.setZ(node.getZ() + 0.5);
        return centeredNode;
    }


}
