package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;

/**
 * Memory Visualize is a high-level api for visualizing object to a visualizer UI.
 */
public interface MemoryVisualizer {
    /**
     * Generate a object map and visualize the generated map.
     *
     * @param object object to be visualize
     */
    void visualizeObject(Object object);

    /**
     * Visualize a generated object map.
     *
     * @param objectLayout The object map
     */
    void visualizeObjectMap(ObjectLayoutMap objectLayout);
}
