package com.drazisil.smarties.ai.memory;

public abstract class Memory {

    protected String key;
    protected Object value;

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
