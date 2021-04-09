package com.caxerx.memoryvisualizer4j.layout.objectlayout;

import java.io.Serializable;
import java.util.List;

public class ObjectLayout implements Serializable {
    private final String type;
    private final List<ObjectLayoutFieldItem> fields;
    private final long memoryAddress;

    public ObjectLayout(String type, List<ObjectLayoutFieldItem> fields, long memoryAddress) {
        this.type = type;
        this.fields = fields;
        this.memoryAddress = memoryAddress;
    }

    public List<ObjectLayoutFieldItem> getFields() {
        return fields;
    }

    public long getMemoryAddress() {
        return memoryAddress;
    }

    public String getType() {
        return type;
    }
}
