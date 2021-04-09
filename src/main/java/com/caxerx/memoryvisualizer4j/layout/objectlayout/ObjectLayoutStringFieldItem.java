package com.caxerx.memoryvisualizer4j.layout.objectlayout;

public class ObjectLayoutStringFieldItem extends ObjectLayoutObjectFieldItem {
    private final String value;

    public ObjectLayoutStringFieldItem(String name, String type, long memoryAddress, long objectAddress, String value) {
        super(name, type, memoryAddress, objectAddress);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
