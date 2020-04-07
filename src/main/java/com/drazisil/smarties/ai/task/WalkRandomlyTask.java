package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;
import com.drazisil.smarties.ai.AiHelper;
import com.drazisil.smarties.ai.memory.Memory;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class WalkRandomlyTask extends Task {
    public WalkRandomlyTask(SmartVillager villager) {
        super(villager);
    }

    @Override
    public void doTask() {
        startExecuting(this.villager.getVillager());
    }

    protected void startExecuting(Villager entityIn) {
        Location blockpos = entityIn.getLocation().clone();
        World world = entityIn.getLocation().getWorld();


        // Stop the tick due to memory troubleshooting!
//        this.villager.brain.setTickRunning(false);



//        List<BlockPos> list = BlockPos.getAllInBox(blockpos.add(-1, -1, -1), blockpos.add(1, 1, 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
        ArrayList<Location> list = null;
        try {
            Location topX = blockpos.clone().add(-1.0d, 1.0d, -1.0d);
            Location bottomZ = blockpos.clone().add(1.0d, -1.0d, 1.0d);
            list = AiHelper.getAllLocationsInBox(topX, bottomZ);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        logger.warning("Possible locations: " + list.toString());

        Collections.shuffle(list);
        Optional<Location> optional = list.stream().filter((p_220428_1_) -> {
            try {
                // TODO: and light level at block = 15
                return world.getBlockAt(p_220428_1_).getLightFromSky() == 15;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }).filter(AiHelper::isGround).findFirst();
        //            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(p_220430_2_, this.field_220431_a, 0));
        optional.ifPresent(location -> {
            Location newLocation = location.add(0.5d, 0, 0.5d);
            this.villager.brain.addMemory(new Memory("WALK_TARGET", newLocation.toString()));
            entityIn.teleport(newLocation);
        });

    }
}
