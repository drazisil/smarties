package com.drazisil.smarties.ai;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

import static com.drazisil.smarties.Smarties.logger;

public class AiHelper {

    public static ArrayList<Location> getAllLocationsInBox(Location topX, Location bottomZ) throws Exception {
        ArrayList<Location> list = new ArrayList<>();

//        System.out.println("Start: " + topX);
//        System.out.println("End: " + bottomZ);

        int startX = topX.getBlockX();
        int startY = topX.getBlockY();
        int startZ = topX.getBlockZ();

        int endX = bottomZ.getBlockX();
        int endY = bottomZ.getBlockY();
        int endZ = bottomZ.getBlockZ();

        // Max number of locations before we kill the loop
        int maxBlocks = 90;

        // Current block count
        int blockCounter = 0;

        World world = topX.getWorld();

        for (int y = startY; y >= endY; y--) {
            for (int x = startX; x <= endX; x++) {
                for (int z = startZ; z <= endZ; z++) {
                    if (blockCounter >= maxBlocks) {
                        logger.warning("We have exceeded the maxBlocks count, aborting!");
                        logger.warning(list.toString());
                        throw new Exception("Too many blocks found");
                    }
//                    System.out.println(x + ", " + y + ", " + z);

                    list.add(new Location(world, x, y, z));
                    blockCounter++;
                }
            }
        }
        return list;
    }

    public static Boolean isGround(Location location) {

        ArrayList<Material> groundBlocks = new ArrayList<>();
        groundBlocks.add(Material.GRASS_BLOCK);
        groundBlocks.add(Material.GRASS_PATH);
        groundBlocks.add(Material.DIRT);
        groundBlocks.add(Material.ACACIA_WOOD);

        World world = location.getWorld();
        Block thisBlock = null;
        try {
            thisBlock = world.getBlockAt(location);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Location locationBelow = location.clone().subtract(0, 1, 0);
        Block blockBelow = world.getBlockAt(locationBelow);

        return thisBlock.isPassable() && !blockBelow.isPassable();
    }
}
