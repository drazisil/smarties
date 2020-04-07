package com.drazisil.smarties.ai.pathfinding;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Objects;

public class PathChunkNode extends PathNode {

    protected ArrayList<PathChunkNode> childNodes = new ArrayList<>();

    public PathChunkNode(Location location) {
        this(1, location);
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

    public Chunk asChunk() {
        return world.getChunkAt(x, z);
    }
}
