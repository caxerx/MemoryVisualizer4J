package com.caxerx.memoryvisualizer4j.layout.objectlayout;

import java.io.Serializable;

public abstract class ObjectLayoutFieldItem implements Serializable {
    private final String name;
    private final String type;
    private final long memoryAddress;

    public ObjectLayoutFieldItem(String name, String type, long memoryAddress) {
        this.name = name;
        this.type = type;
        this.memoryAddress = memoryAddress;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getMemoryAddress() {
        return memoryAddress;
    }
}
