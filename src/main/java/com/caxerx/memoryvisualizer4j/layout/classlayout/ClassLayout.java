package com.caxerx.memoryvisualizer4j.layout.classlayout;

import java.io.Serializable;
import java.util.List;

/**
 * Describe a Memory Layout of Class
 */
public class ClassLayout implements Serializable {
    private final List<ClassLayoutItem> layout;
    private final long instanceSize;
    private final String type;

    /**
     * Create a ClassLayout
     *
     * @param layout       Layout of the class, each item is describing a field or a padding
     * @param instanceSize The instance size the this class
     */
    public ClassLayout(List<ClassLayoutItem> layout, long instanceSize, String type) {
        this.layout = layout;
        this.instanceSize = instanceSize;
        this.type = type;
    }

    /**
     * Get layout of the class
     *
     * @return layout of the class, each item is describing a field or a padding
     */
    public List<ClassLayoutItem> getLayout() {
        return layout;
    }

    /**
     * Get instance size of the class
     *
     * @return instance size of the class in bytes, showing how many bytes an instance will take.
     */
    public long getInstanceSize() {
        return instanceSize;
    }

    public String getType() {
        return type;
    }
}
