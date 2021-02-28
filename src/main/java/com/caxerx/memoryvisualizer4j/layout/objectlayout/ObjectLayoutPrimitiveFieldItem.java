package com.caxerx.memoryvisualizer4j.layout.objectlayout;

public class ObjectLayoutPrimitiveFieldItem extends ObjectLayoutFieldItem {
    private final Object value;

    public ObjectLayoutPrimitiveFieldItem(String name, String type, long memoryAddress, Object value) {
        super(name, type, memoryAddress);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
