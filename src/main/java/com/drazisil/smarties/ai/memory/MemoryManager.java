package com.drazisil.smarties.ai.memory;

import java.util.ArrayList;

public class MemoryManager {

    private final ArrayList<Memory> memories = new ArrayList<>();

    public MemoryManager() {
    }

//    public void clearMemories() {
//        this.memories.clear();
//    }

    public Memory getMemory(String key) {
        for (Memory memory: this.memories) {
            if (memory.getKey().equals(key)) return memory;
        }
        return null;
    }

    public Object get(String key) {
        Memory memory = getMemory(key);
        if (memory == null) {
            return "";
        }
        return memory.getValue();
    }

    public ArrayList<Memory> getAll() {
        return this.memories;
    }

    public void remove(String key) {
        for (Memory memory: this.memories) {
            if (memory.getKey().equals(key)) {
                memories.remove(memory);
                return;
            }
        }
    }

    public void add(Memory memory) {
        remove(memory.getKey());
        this.memories.add(memory);
    }
}
