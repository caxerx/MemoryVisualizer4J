package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;

/**
 * Memory Visualize is a high-level api for visualizing object to a visualizer UI.
 */
public interface MemoryVisualizer {
    /**
     * Register a object that need to be visualize.
     *
     * @param object object to be visualize
     */
    MemoryVisualizer registerObject(Object... object);


    /**
     * Generate a object map snapshot and visualize it.
     */
    MemoryVisualizer visualize();
}
