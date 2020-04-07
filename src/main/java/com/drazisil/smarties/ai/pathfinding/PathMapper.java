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
        for (int i = 0; i < childrenCount; i = i + 1) {
            PathNode node = children.get(i);

            if (node.getX() == targetNode.getX()) node.addWeight();
            if (node.getY() == targetNode.getY()) node.addWeight();
            if (node.getZ() == targetNode.getZ()) node.addWeight();

        }

        for (PathNode node: weightedNode.getChildNodes()) {
        }

        return weightedNode;
    }

    public static Block getBlockAtPathNode(PathNode node) {
        return node.getWorld().getBlockAt(node.getX(), node.getY(), node.getZ());
    }



//    public PathNodeSet weightByDistance(PathNode targetNode, PathNode node1, PathNode node2) {
//    }
//
//    public int getDistance(int one, int two) {
//    }

}
