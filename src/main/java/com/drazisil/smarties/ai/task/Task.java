package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;

public abstract class Task {

    protected SmartVillager smartVillager;

    public Task(SmartVillager villager) {
        smartVillager = villager;
    }

    public abstract void doTask();
}
