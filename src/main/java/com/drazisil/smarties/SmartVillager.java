package com.drazisil.smarties;

import com.drazisil.smarties.ai.Brain;
import com.drazisil.smarties.ai.task.SpawnGolemTask;
import com.drazisil.smarties.ai.task.Task;
import com.drazisil.smarties.ai.task.WalkRandomlyTask;
import org.bukkit.entity.Villager;

import static com.drazisil.smarties.Smarties.logger;


public class SmartVillager {

        private Villager villager;
        private Villager.Profession profession;
        public Brain brain;

        public SmartVillager(Villager villager, Villager.Profession profession) {
            this.villager = villager;
            this.brain = new Brain(this.villager);

            // Assign AI tasks
            Task newTask;
            this.profession = profession;
            logger.warning("Current profession: " + this.profession);
            if (this.profession == Villager.Profession.FARMER) {
                newTask = new WalkRandomlyTask(this);
            } else {
                newTask = new SpawnGolemTask(this);
            }
            this.brain.addTask(newTask);
        }

        public Villager getVillager() {
            return this.villager;
        }

    }

