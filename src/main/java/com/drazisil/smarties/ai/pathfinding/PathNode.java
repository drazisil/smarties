package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Optional;

import static com.drazisil.smarties.Smarties.logger;
import static com.drazisil.smarties.ai.pathfinding.PathMapper.*;

/**
 * Specifies a single 3D location in the world, with methods to explain details about it.
 */
public class PathNode {

    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private int weight;
    private ArrayList<PathNode> childNodes = new ArrayList<>();

    /**
     * 
     * @param location location to create node from
     */
    public PathNode(Location location) {
        this(1, location);
    }

    /**
     * 
     * @param weight weight to use in path finding. Higher is better
     * @param location location to create node from
     */
    public PathNode(int weight, Location location) {
        this.weight = weight;
        this.world = location.getWorld();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    /**
     * 
     * @param weight weight to use in path finding. Higher is better
     * @param world world node is in
     * @param x x position of node
     * @param y y position on node
     * @param z z position of node
     */
    public PathNode(int weight, World world, int x, int y, int z) {
        this.weight = weight;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adjust the XYZ by direction. Returns a new PathNode.
     *
     * Negitive Z is north and negitive X is west
     *
     * @param direction NodeDirection}
     * @param node PathNode
     * @return PathNode
     */
    public PathNode adjustByDirection(NodeDirection direction, PathNode node) {
        int weight = node.getWeight();
        World world = node.getWorld();
        int x = node.getX();
        int y = node.getY();
        int z = node.getZ();

        if (direction == NodeDirection.NORTHWEST)
            return new PathNode(weight, world, x - 1, y, z - 1);

        if (direction == NodeDirection.NORTH)
            return new PathNode(weight, world, x, y, z - 1);

        if (direction == NodeDirection.NORTHEAST)
            return new PathNode(weight, world, x + 1, y, z - 1);

        if (direction == NodeDirection.WEST)
            return new PathNode(weight, world, x - 1, y, z);

        if (direction == NodeDirection.EAST)
            return new PathNode(weight, world, x + 1, y, z);

        if (direction == NodeDirection.SOUTHWEST)
            return new PathNode(weight, world, x - 1, y, z + 1);

        if (direction == NodeDirection.SOUTH)
            return new PathNode(weight, world, x, y, z + 1);

        if (direction == NodeDirection.SOUTHEAST)
            return new PathNode(weight, world, x + 1, y, z + 1);

        if (direction == NodeDirection.UP)
            return new PathNode(weight, world, x, y + 1, z);

        if (direction == NodeDirection.DOWN)
            return new PathNode(weight, world, x, y - 1, z);

        // We never get here, right?
        return node;

    }


    /**
     * Populate the list of movable PathNodes from this node.
     */
    public void populateChildNodes() {

        PathNode node;

        try {
            node = adjustByDirection(NodeDirection.NORTHEAST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.NORTH, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.NORTHWEST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.EAST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.WEST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.SOUTHEAST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.SOUTH, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = adjustByDirection(NodeDirection.SOUTHWEST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Is this node a valid move target
     * @param node
     * @return boolean
     */
    public boolean isPossibleNode(PathNode node) {
        Optional<PathNode> possibleNode = getTopNode(node);
        return possibleNode.isPresent();
    }

//    /**
//     * Given an X and Y, return if Y, Y-1, or Y+1 are valld PathNodes
//     * @param world
//     * @param x
//     * @param y
//     * @param z
//     * @return {@link PathNode}
//     */
//    public Optional<PathNode> getTopNode(World world, int x, int y, int z) {
//
//        return getTopNode(new PathNode(1, world, x, y, z));
//
//    }



    /**
     * Check if the node above, this node, or the node below are valid move targets. In that order.
     * @param node node to check
     * @return Optional<PathNode>
     */
    public Optional<PathNode> getTopNode(PathNode node) {

        PathNode possibleNode;

        possibleNode = adjustByDirection(NodeDirection.UP, node);
        if (isValidMoveBlock(possibleNode)) {
            return Optional.of(possibleNode);
        }

        possibleNode = node;
        if (isValidMoveBlock(possibleNode)) {
            return Optional.of(possibleNode);
        }

        possibleNode = adjustByDirection(NodeDirection.DOWN, node);
        if (isValidMoveBlock(possibleNode)) {
            return Optional.of(possibleNode);
        }

        return Optional.empty();
    }

    /**
     * Is this a valid two block high space to move into with a solid floor. 
     * @param node node to check
     * @return boolean
     */
    public boolean isValidMoveBlock(PathNode node) {

        PathNode nodeUp = adjustByDirection(NodeDirection.UP, node);
        PathNode nodeDown = adjustByDirection(NodeDirection.DOWN, node);

        return (!isGround(node) && !isGround(nodeUp) && isGround(nodeDown));
    }


    /**
     * Get an arry of child nodes
     * @return ArrayList<PathNode>
     */
    public ArrayList<PathNode> getChildNodes() {
        if (childNodes == null) {
            logger.warning("No children!");
            return new ArrayList<>();
        }
        return childNodes;
    }

    /**
     * Replace the array of child nodes
     * @param childNodes set of path nodes to replace to
     */
    public void setChildNodes(ArrayList<PathNode> childNodes) {
        this.childNodes = childNodes;
    }

    /**
     * Increment node weight
     */
    public void addWeight() {
        addWeight(1);
    }

    /**
     * Add to node weight
     * @param weightToAdd add this weith to existing node weight
     */
    public void addWeight(int weightToAdd) {
        this.weight = this.weight + weightToAdd;
    }

    /**
     * Get current node weight
     * @return the node's weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Get the world the node is in
     * @return {@link org.bukkit.World}
     */
    public World getWorld() {
        return world;
    }

    /**
     * Copy the node
     * @return {@link PathNode}
     */
    @Override
    public PathNode clone(){
        PathNode clonedNode = new PathNode(weight, world, x, y, z);
        clonedNode.setChildNodes(getChildNodes());
        return clonedNode;

    }

    /**
     * Get the node's X postion
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Get the node's Y postion
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Get the node's Z position
     * @return int
     */
    public int getZ() {
        return z;
    }

    /**
     *  Returns true if all blocks above are passable
     *
     * @param node the node to check
     * @return boolean
     */
    public static boolean isGround(PathNode node) {
        World world = node.getWorld();
        int maxHeight = world.getMaxHeight();

        if (getBlockAtPathNode(node).isPassable()) return false;

        for (int i = node.getY() + 1; i <= maxHeight; i++) {
            if (!world.getBlockAt(node.getX(), node.getY() + i, node.getZ()).isPassable()) return  false;
        }

        return true;


    }

    /**
     * Serialize the node for output
     * @return String
     */
    public String toString() {
        return String.format("weight: %d, world: %s, x: %d, y: %d, z: %d", weight, world, x, y, z);
    }
}
