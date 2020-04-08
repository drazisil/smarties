package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Location;
import org.bukkit.World;

public class PathNode {

    protected World world;
    protected int weight;
    protected double x;
    protected double z;

    /**
     * Create an empty node with a weight of 1
     */
    public PathNode() {
        this.weight = 1;
    }

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
    }

    /**
     * Get the node's X postion
     * @return int
     */
    public double getX() {
        return x;
    }

    /**
     * Get the node's Z position
     * @return int
     */
    public double getZ() {
        return z;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public static boolean isThere(Location currentNode, Location targetNode) {
        int cX = currentNode.getBlockX();
        int cY = currentNode.getBlockY();
        int cZ = currentNode.getBlockZ();

        int tX = targetNode.getBlockX();
        int tY = targetNode.getBlockY();
        int tZ = targetNode.getBlockZ();

        return (cX == tX && cY == tY && cZ == tZ);

    }
}
