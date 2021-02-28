package com.caxerx.memoryvisualizer4j.layout.classlayout;

public class ClassLayoutPaddingItem extends ClassLayoutItem {
    private ClassLayoutPaddingItemType type;

    public ClassLayoutPaddingItem(long size, long offset, ClassLayoutPaddingItemType type) {
        super(size, offset);
        this.type = type;
    }

    public ClassLayoutPaddingItemType getType() {
        return type;
    }
}
