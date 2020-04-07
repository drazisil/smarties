package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.World;
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

    public static PathNode shiftNode(NodeDirection direction, PathNode node) {
        int weight = node.getWeight();
        World world = node.getWorld();
        int x = node.getX();
        int y = node.getY();
        int z = node.getZ();

        if (direction == NodeDirection.NORTHEAST)
            return new PathNode(weight, world, x - 1, y, z - 1);

        if (direction == NodeDirection.NORTH)
            return new PathNode(weight, world, x, y, z - 1);

        if (direction == NodeDirection.NORTHWEST)
            return new PathNode(weight, world, x + 1, y, z - 1);

        if (direction == NodeDirection.EAST)
            return new PathNode(weight, world, x - 1, y, z);

        if (direction == NodeDirection.WEST)
            return new PathNode(weight, world, x + 1, y, z);

        if (direction == NodeDirection.SOUTHEAST)
            return new PathNode(weight, world, x - 1, y, z + 1);

        if (direction == NodeDirection.SOUTH)
            return new PathNode(weight, world, x, y, z + 1);

        if (direction == NodeDirection.SOUTHWEST)
            return new PathNode(weight, world, x + 1, y, z + 1);

        if (direction == NodeDirection.UP)
            return new PathNode(weight, world, x, y + 1, z);

        if (direction == NodeDirection.DOWN)
            return new PathNode(weight, world, x, y - 1, z);

        // We never get here, right?
        return node;

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
