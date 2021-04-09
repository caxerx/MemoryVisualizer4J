package com.caxerx.memoryvisualizer4j.layout.objectlayout;

public class ObjectLayoutObjectFieldItem extends ObjectLayoutFieldItem {
    private final long objectAddress;

    public ObjectLayoutObjectFieldItem(String name, String type, long memoryAddress, long objectAddress) {
        super(name, type, memoryAddress);
        this.objectAddress = objectAddress;
    }
}
