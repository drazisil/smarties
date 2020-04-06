package com.drazisil.smarties.ai;

import com.drazisil.smarties.ai.memory.Memory;
import com.drazisil.smarties.ai.memory.MemoryManager;
import com.drazisil.smarties.ai.task.Task;
import org.bukkit.Location;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class Brain {

    private Villager villager;
    private UUID id;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final MemoryManager memories = new MemoryManager();
    private boolean isDead;
    public boolean tickRunning = false;

    public Brain(Villager rawVillager) {
        this.villager = rawVillager;
        addMemory(new Memory("PROFESSION", this.villager.getProfession().toString()));
        addMemory(new Memory("LOCATION", this.villager.getLocation().toString()));
        addMemory(new Memory("WALK_TARGET", new Location(this.villager.getWorld(), -16, 5, -196).toString()));
        this.isDead = this.villager.isDead();
        this.id = this.villager.getUniqueId();

    }

    public void livingTick() {
        if (this.isDead) return;
        if (!this.tickRunning) return;

        logger.info("Brain livingTick for villager: " + this.id + " who is a " + this.memories.get("PROFESSION"));
        logger.warning("Current memories: " + this.memories.getAll().toString());
        for (Task task: tasks) {
            task.doTask();
        }
//        setTickRunning(false);

    }

    public void toggleTick() {
        setTickRunning(!this.tickRunning);
    }

    public void setTickRunning(boolean shouldRun) {
        this.tickRunning = shouldRun;
        logger.info("Tick for " + this.id + " set to: " + this.tickRunning);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addMemory(Memory memory) {this.memories.add(memory);}

    public Memory getMemory(String key) {
        return this.memories.getMemory(key);
    }

}
