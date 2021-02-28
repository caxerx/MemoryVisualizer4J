package com.caxerx.memoryvisualizer4j.layout.objectlayout;

public class ObjectLayoutObjectFieldItem extends ObjectLayoutFieldItem {
    private final ObjectLayout layout;

    public ObjectLayoutObjectFieldItem(String name, String type, long memoryAddress, ObjectLayout layout) {
        super(name, type, memoryAddress);
        this.layout = layout;
    }

    public ObjectLayout getLayout() {
        return layout;
    }
}
