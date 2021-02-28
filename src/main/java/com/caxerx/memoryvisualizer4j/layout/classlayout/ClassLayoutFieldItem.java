package com.caxerx.memoryvisualizer4j.layout.classlayout;

public class ClassLayoutFieldItem extends ClassLayoutItem {
    private final String type;
    private final String name;

    public ClassLayoutFieldItem(long size, long offset, String type, String name) {
        super(size, offset);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
