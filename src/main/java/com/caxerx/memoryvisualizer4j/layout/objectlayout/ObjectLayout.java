package com.caxerx.memoryvisualizer4j.layout.objectlayout;

import com.caxerx.memoryvisualizer4j.layout.classlayout.ClassLayout;

import java.io.Serializable;
import java.util.List;

public class ObjectLayout implements Serializable {
    private final ClassLayout classLayout;
    private final List<ObjectLayoutFieldItem> fields;
    private final long memoryAddress;

    public ObjectLayout(ClassLayout classLayout, List<ObjectLayoutFieldItem> fields, long memoryAddress) {
        this.classLayout = classLayout;
        this.fields = fields;
        this.memoryAddress = memoryAddress;
    }

    public ClassLayout getClassLayout() {
        return classLayout;
    }

    public List<ObjectLayoutFieldItem> getFields() {
        return fields;
    }

    public long getMemoryAddress() {
        return memoryAddress;
    }
}
