package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class PathNode {

    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private final int weight;
    private ArrayList<PathNode> children;

    public PathNode(Location location) {
        this(1, location);
    }

    public PathNode(int weight, Location location) {
        this.weight = weight;
        this.world = location.getWorld();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public void calculateChildren() {
        int startX = this.x - 1;
        int startY = this.y + 1;
        int startZ = this.z - 1;

        int endX = this.x + 1;
        int endY = this.y - 1;
        int endZ = this.z + 1;

        this.children = new ArrayList<>();

        for (int y = startY; y >= endY; y = y - 1) {
            for (int x = startX; x <= endX; x = x + 1) {
                for (int z = startZ; z <= endZ; z = z +1) {
                    if(!(this.x == x && this.y == y && this.z == z))
                        this.children.add(new PathNode(1, new Location(this.world, x, y, z)));
                }
            }
        }
    }

    public ArrayList<PathNode> getChildren() {
        return children;
    }

    public String toString() {
        return String.format("weight: %d, world: %s, x: %d, y: %d, z: %d", weight, world, x, y, z);
    }
}
