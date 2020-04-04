package com.drazisil.smarties.ai;

import com.drazisil.smarties.ai.task.SpawnGolemTask;
import org.bukkit.Location;
import org.bukkit.entity.Villager;

import java.util.UUID;

import static com.drazisil.smarties.Smarties.logger;

public class Brain {
    private final Villager villager;
    private final Villager.Profession profession;
    private final Location location;
    private final UUID id;
    private SpawnGolemTask task;
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

        logger.info("Brain livingTick for villager: " + this.id);
        this.task.doTask();


    }

    public void toggleTick() {
        setTickRunning(!this.tickRunning);
    }

    public void setTickRunning(boolean shouldRun) {
        this.tickRunning = shouldRun;
        logger.info("Tick for " + this.getId() + "set to :" + this.tickRunning);
    }

    private UUID getId() {
        return this.id;
    }


    public void addTask(SpawnGolemTask task) {
        this.task = task;
    }
}
