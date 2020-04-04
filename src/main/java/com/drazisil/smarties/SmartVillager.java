package com.drazisil.smarties;

import com.drazisil.smarties.ai.Brain;
import com.drazisil.smarties.ai.task.SpawnGolemTask;
import org.bukkit.entity.Villager;


    public class SmartVillager {

        private Villager villager;
        public Brain brain;

        public SmartVillager(Villager villager) {
            this.villager = villager;
            this.brain = new Brain(this.villager);
            SpawnGolemTask newTask = new SpawnGolemTask(this);
            this.brain.addTask(newTask);
        }

        public Villager getVillager() {
            return this.villager;
        }

    }

