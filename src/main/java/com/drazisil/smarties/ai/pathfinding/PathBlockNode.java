package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Optional;

import static com.drazisil.smarties.Smarties.logger;
import static com.drazisil.smarties.ai.pathfinding.PathMapper.getBlockAtPathNode;

/**
 * Specifies a single 3D location in the world, with methods to explain details about it.
 */
public class PathBlockNode extends PathNode {

    private final PathChunkNode chunkNode;
    private int y;
    private ArrayList<PathBlockNode> childNodes = new ArrayList<>();

    /**
     * 
     * @param location location to create node from
     */
    public PathBlockNode(Location location) {
        this(1, location);
    }

    /**
     * 
     * @param weight weight to use in path finding. Higher is better
     * @param location location to create node from
     */
    public PathBlockNode(int weight, Location location) {
        super(weight, location);
        this.chunkNode = new PathChunkNode(weight, location);
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
    public PathBlockNode(int weight, World world, double x, int y, double z) {
        super(weight, new Location(world, x, y, z));
        this.chunkNode = new PathChunkNode(weight, world, (int)Math.floor(x), y, (int)Math.floor(z));
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
    public PathBlockNode adjustByDirection(NodeDirection direction, PathBlockNode node) {
        int weight = node.getWeight();
        World world = node.getWorld();
        double x = node.getX();
        int y = node.getY();
        double z = node.getZ();

        if (direction == NodeDirection.NORTHWEST)
            return new PathBlockNode(weight, world, x - 1, y, z - 1);

        if (direction == NodeDirection.NORTH)
            return new PathBlockNode(weight, world, x, y, z - 1);

        if (direction == NodeDirection.NORTHEAST)
            return new PathBlockNode(weight, world, x + 1, y, z - 1);

        if (direction == NodeDirection.WEST)
            return new PathBlockNode(weight, world, x - 1, y, z);

        if (direction == NodeDirection.EAST)
            return new PathBlockNode(weight, world, x + 1, y, z);

        if (direction == NodeDirection.SOUTHWEST)
            return new PathBlockNode(weight, world, x - 1, y, z + 1);

        if (direction == NodeDirection.SOUTH)
            return new PathBlockNode(weight, world, x, y, z + 1);

        if (direction == NodeDirection.SOUTHEAST)
            return new PathBlockNode(weight, world, x + 1, y, z + 1);

        if (direction == NodeDirection.UP)
            return new PathBlockNode(weight, world, x, y + 1, z);

        if (direction == NodeDirection.DOWN)
            return new PathBlockNode(weight, world, x, y - 1, z);

        // We never get here, right?
        return node;

    }


    /**
     * Populate the list of movable PathNodes from this node.
     */
    public void populateChildNodes() {

        PathBlockNode node;

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
    public boolean isPossibleNode(PathBlockNode node) {
        Optional<PathBlockNode> possibleNode = getTopNode(node);
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
    public Optional<PathBlockNode> getTopNode(PathBlockNode node) {

        PathBlockNode possibleNode;

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
    public boolean isValidMoveBlock(PathBlockNode node) {

        PathBlockNode nodeUp = adjustByDirection(NodeDirection.UP, node);
        PathBlockNode nodeDown = adjustByDirection(NodeDirection.DOWN, node);

        return (!isGround(node) && !isGround(nodeUp) && isGround(nodeDown));
    }


    /**
     * Get an arry of child nodes
     * @return ArrayList<PathNode>
     */
    public ArrayList<PathBlockNode> getChildNodes() {
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
    public void setChildNodes(ArrayList<PathBlockNode> childNodes) {
        this.childNodes = childNodes;
    }



    /**
     * Copy the node
     * @return {@link PathBlockNode}
     */
    @Override
    public PathBlockNode clone(){
        PathBlockNode clonedNode = new PathBlockNode(weight, world, x, y, z);
        clonedNode.setChildNodes(getChildNodes());
        return clonedNode;

    }

    /**
     * Get the node's Y postion
     * @return int
     */
    public int getY() {
        return y;
    }

    public PathChunkNode getChunk() { return this.chunkNode; }

    /**
     *  Returns true if all blocks above are passable
     *
     * @param node the node to check
     * @return boolean
     */
    public static boolean isGround(PathBlockNode node) {
        World world = node.getWorld();
        int maxHeight = world.getMaxHeight();

        if (getBlockAtPathNode(node).isPassable()) return false;

        for (int i = node.getY() + 1; i <= maxHeight; i++) {
            if (!world.getBlockAt((int)node.getX(), node.getY() + i, (int)node.getZ()).isPassable()) return  false;
        }

        return true;


    }

    /**
     * Serialize the node for output
     * @return String
     */
    public String toString() {
        return String.format("weight: %d, world: %s, x: %f, y: %d, z: %f", weight, world, x, y, z);
    }

    /**
     * Convery to a {@link Location} object
     * @return
     */
    public Location toLocation() {
        return new Location(world, x, y, z);
    }

    public void setY(int y) {
        this.y = y;
    }
}
