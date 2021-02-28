package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayout;

public interface MemoryVisualizer {
    void visualizeObject(Object object);

    void visualizeObjectMap(ObjectLayout objectLayout);
}
