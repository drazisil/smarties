package com.drazisil.smarties.ai;

import com.drazisil.smarties.ai.task.Task;
import org.bukkit.Location;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class Brain {
    private final Villager villager;
    private final Villager.Profession profession;
    private final Location location;
    private final UUID id;
    private ArrayList<Task> tasks = new ArrayList<>();
    private boolean isDead;
    public boolean tickRunning = false;

    public Brain(Villager rawVillager) {
        this.villager = rawVillager;
        this.profession = this.villager.getProfession();
        this.location = this.villager.getLocation();
        this.isDead = this.villager.isDead();
        this.id = this.villager.getUniqueId();

    }

    public void livingTick() {
        if (this.isDead) return;
        if (!this.tickRunning) return;

        logger.info("Brain livingTick for villager: " + this.id + " who is a " + this.profession);
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
        logger.info("Tick for " + this.getId() + " set to: " + this.tickRunning);
    }

    private UUID getId() {
        return this.id;
    }


    public void addTask(Task task) {
        this.tasks.add(task);
    }

}
