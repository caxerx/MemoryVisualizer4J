package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayout;

public interface ObjectMapGenerator {
    ObjectLayout generateObjectMap(Object object);
}
