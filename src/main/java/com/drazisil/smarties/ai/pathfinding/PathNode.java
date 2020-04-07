package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Location;
import org.bukkit.World;

public class PathNode {

    protected final World world;
    protected int weight;
    protected int x;
    protected int z;

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
    public int getX() {
        return x;
    }

    /**
     * Get the node's Z position
     * @return int
     */
    public int getZ() {
        return z;
    }
}
