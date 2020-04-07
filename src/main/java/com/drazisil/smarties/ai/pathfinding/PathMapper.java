package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.block.Block;

import java.util.ArrayList;

/**
 * Provided methods to map paths between two {@link PathNode} objects.
 */
public class PathMapper {

    private final PathNode sourceNode;
    private final PathNode targetNode;

    public PathMapper(PathNode sourceNode, PathNode targetNode) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }

    public PathNode weightChildNodes() throws Exception {

        PathNode weightedNode = this.sourceNode.clone();

        if (targetNode == null) {
            throw new Exception("No targetNode, unable to weight");
        }

        ArrayList<PathNode> children = weightedNode.getChildNodes();
        int childrenCount = children.size();
        for (PathNode node : children) {
            if (node.getX() == targetNode.getX()) node.addWeight();
            if (node.getY() == targetNode.getY()) node.addWeight();
            if (node.getZ() == targetNode.getZ()) node.addWeight();

        }

        return weightedNode;
    }

    public static Block getBlockAtPathNode(PathNode node) {
        return node.getWorld().getBlockAt(node.getX(), node.getY(), node.getZ());
    }



//    public PathNodeSet weightByDistance(PathNode targetNode, PathNode node1, PathNode node2) {
//    }
//
    public static int getDistance(int one, int two) {
        int result;
        if (one < 0 && two >= 0) result = (Math.abs(one) + two);
        else if (one < 0) result = (Math.abs(one - two));
        else if (two < 0) result = (one + Math.abs(two));
        else result = (Math.abs(one - two));
        return result;
    }

}
