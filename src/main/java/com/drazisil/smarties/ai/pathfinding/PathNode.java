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
     * @param location
     */
    public PathNode(Location location) {
        this(1, location);
    }

    /**
     * 
     * @param weight
     * @param location
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
     * @param weight
     * @param world
     * @param x
     * @param y
     * @param z
     */
    public PathNode(int weight, World world, int x, int y, int z) {
        this.weight = weight;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Populate the list of movable PathNodes from this node.
     */
    public void populateChildNodes() {

        PathNode node;

        try {
            node = shiftNode(NodeDirection.NORTHEAST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = shiftNode(NodeDirection.NORTH, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = shiftNode(NodeDirection.NORTHWEST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = shiftNode(NodeDirection.EAST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = shiftNode(NodeDirection.WEST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = shiftNode(NodeDirection.SOUTHEAST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node =shiftNode(NodeDirection.SOUTH, this);
            if (isPossibleNode(node)) this.childNodes.add(node);

            node = shiftNode(NodeDirection.SOUTHWEST, this);
            if (isPossibleNode(node)) this.childNodes.add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Given an X and Y, return if Y, Y-1, or Y+1 are valld PathNodes
     * @param world
     * @param x
     * @param y
     * @param z
     * @return {@link PathNode}
     */
    public Optional<PathNode> getTopNode(World world, int x, int y, int z) {

        return getTopNode(new PathNode(1, world, x, y, z));

    }

    /**
     * 
     * @param node
     * @return boolean
     */
    public boolean isPossibleNode(PathNode node) {
        Optional<PathNode> possibleNode = getTopNode(node);
        return possibleNode.isPresent();
    }

    /**
     * 
     * @param node
     * @return
     */
    public Optional<PathNode> getTopNode(PathNode node) {

        PathNode possibleNode;

        possibleNode = shiftNode(NodeDirection.UP, node);
        if (isValidMoveBlock(possibleNode)) {
            return Optional.of(possibleNode);
        }

        possibleNode = node;
        if (isValidMoveBlock(possibleNode)) {
            return Optional.of(possibleNode);
        }

        possibleNode = shiftNode(NodeDirection.DOWN, node);
        if (isValidMoveBlock(possibleNode)) {
            return Optional.of(possibleNode);
        }

        return Optional.empty();
    }

    /**
     * Is this a valid two block high space to move into with a solid floor. 
     * @param node
     * @return boolean
     */
    public boolean isValidMoveBlock(PathNode node) {

        PathNode nodeUp = shiftNode(NodeDirection.UP, node);
        PathNode nodeDown = shiftNode(NodeDirection.DOWN, node);

        return (!isGround(node) && !isGround(nodeUp) && isGround(nodeDown));
    }


    /**
     * Get an arry of child nodes
     * @return
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
     * @param childNodes
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
     * @param weightToAdd
     */
    public void addWeight(int weightToAdd) {
        this.weight = this.weight + weightToAdd;
    }

    /**
     * Get current node weight
     * @return
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
     * @param node
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
