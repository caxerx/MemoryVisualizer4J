package com.caxerx.memoryvisualizer4j.layout.classlayout;

import java.io.Serializable;

public abstract class ClassLayoutItem implements Serializable {
    private final long offset;
    private final long size;

    public ClassLayoutItem(long size, long offset) {
        this.size = size;
        this.offset = offset;
    }

    public long getSize() {
        return size;
    }

    public long getOffset() {
        return offset;
    }
}
