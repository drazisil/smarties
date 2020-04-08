package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Objects;

import static com.drazisil.smarties.Smarties.logger;

public class PathChunkNode extends PathNode {

    protected ArrayList<PathChunkNode> childNodes = new ArrayList<>();

    public PathChunkNode(Location location) {
        this(1, location);
    }

    public PathChunkNode(Chunk chunk) {
        super();
        this.weight = 1;
        this.world = chunk.getWorld();
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    public PathChunkNode(int weight, Location location) {
        super(weight, location);
        Chunk chunk = Objects.requireNonNull(location.getWorld()).getChunkAt(location);
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    public PathChunkNode(int weight, World world, int x, int y, int z) {
        super(weight, world, x, y, z);
        Chunk chunk = world.getChunkAt(x, z);
        this.x = chunk.getX();
        this.z = chunk.getZ();
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
    public PathChunkNode adjustByDirection(NodeDirection direction, PathChunkNode node) {
        int weight = node.getWeight();
        World world = node.getWorld();
        int x = (int)node.getX();
        int z = (int)node.getZ();

        if (direction == NodeDirection.NORTHWEST)
            return new PathChunkNode(world.getChunkAt(x - 1, z - 1));

        if (direction == NodeDirection.NORTH)
            return new PathChunkNode(world.getChunkAt(x, z - 1));

        if (direction == NodeDirection.NORTHEAST)
            return new PathChunkNode(world.getChunkAt(x + 1, z - 1));

        if (direction == NodeDirection.WEST)
            return new PathChunkNode(world.getChunkAt(x - 1, z));

        if (direction == NodeDirection.EAST)
            return new PathChunkNode(world.getChunkAt(x + 1, z));

        if (direction == NodeDirection.SOUTHWEST)
            return new PathChunkNode(world.getChunkAt(x - 1, z + 1));

        if (direction == NodeDirection.SOUTH)
            return new PathChunkNode(world.getChunkAt(x, z + 1));

        if (direction == NodeDirection.SOUTHEAST)
            return new PathChunkNode(world.getChunkAt(x + 1, z + 1));

        // We never get here, right?
        return node;

    }

    /**
     * Populate the list of movable chunks from this node.
     */
    public void populateChildNodes() {

        PathChunkNode node;

        try {
            this.childNodes.add(adjustByDirection(NodeDirection.NORTHEAST, this));

            this.childNodes.add(adjustByDirection(NodeDirection.NORTH, this));

            this.childNodes.add(adjustByDirection(NodeDirection.NORTHWEST, this));

            this.childNodes.add(adjustByDirection(NodeDirection.EAST, this));

            this.childNodes.add(adjustByDirection(NodeDirection.WEST, this));

            this.childNodes.add(adjustByDirection(NodeDirection.SOUTHEAST, this));

            this.childNodes.add(adjustByDirection(NodeDirection.SOUTH, this));

            this.childNodes.add(adjustByDirection(NodeDirection.SOUTHWEST, this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get an arry of child nodes
     * @return ArrayList<PathChunkNode>
     */
    public ArrayList<PathChunkNode> getChildNodes() {
        if (childNodes == null) {
            logger.warning("No children!");
            return new ArrayList<>();
        }
        return childNodes;
    }

    /**
     * Serialize the node for output
     * @return String
     */
    public String toString() {
        return String.format("weight: %d, world: %s, x: %f, z: %f", weight, world, x, z);
    }

    public Chunk asChunk() {
        return world.getChunkAt((int)x, (int)z);
    }
}
