package com.drazisil.smarties;

import com.drazisil.smarties.ai.Brain;
import com.drazisil.smarties.ai.task.SpawnGolemTask;
import com.drazisil.smarties.ai.task.Task;
import com.drazisil.smarties.ai.task.WalkTask;
import org.bukkit.entity.Villager;

import static com.drazisil.smarties.Smarties.logger;


public class SmartVillager {

    private final Villager villager;
    public final Brain brain;

    public SmartVillager(Villager villager, Villager.Profession profession) {
        this.villager = villager;
        this.brain = new Brain(this.villager);

        // Assign AI tasks
        Task newTask;
        logger.warning("Current profession: " + profession);
        if (profession == Villager.Profession.FARMER) {
            newTask = new WalkTask(this);
        } else {
            newTask = new SpawnGolemTask(this);
        }
        this.brain.addTask(newTask);
    }

    public Villager getVillager() {
        return this.villager;
    }

    }

