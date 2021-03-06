package com.drazisil.smarties.ai.task;

import com.drazisil.smarties.SmartVillager;

public abstract class Task {

    protected final SmartVillager villager;

    public Task(SmartVillager villager) {
        this.villager = villager;
    }

    public abstract void doTask();
}
