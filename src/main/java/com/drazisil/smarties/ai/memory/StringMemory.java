package com.drazisil.smarties.ai.memory;

public class StringMemory extends Memory {

    private final String value;

    public StringMemory(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return (key + ": " + value);
    }
}
