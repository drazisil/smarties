package com.drazisil.smarties.ai.memory;

import org.bukkit.Location;

public class LocationMemory extends Memory {

    private final Location value;

    public LocationMemory(String key, Location location) {
        this.key = key;
        this.value = location;
    }

    public String getKey() {
        return key;
    }

    public Location getValue() {
        return value;
    }

    public String toString() {
        return (key + ": " + value);
    }
}
