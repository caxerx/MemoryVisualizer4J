package com.caxerx.memoryvisualizer4j.layout.classlayout;

import java.util.List;

public class ArrayLayout extends ClassLayout {
    private final long itemSize;

    /**
     * Create a ClassLayout
     *
     * @param layout       Layout of the class, each item is describing a field or a padding
     * @param instanceSize The instance size the this class
     * @param itemSize     size of each item in array
     */
    public ArrayLayout(List<ClassLayoutItem> layout, long instanceSize, String type, long itemSize) {
        super(layout, instanceSize, type);
        this.itemSize = itemSize;
    }

    /**
     * Get the array item size
     *
     * @return array item size, in bytes
     */
    public long getItemSize() {
        return itemSize;
    }
}
