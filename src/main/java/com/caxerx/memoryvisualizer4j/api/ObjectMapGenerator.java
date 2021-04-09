package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;

/**
 * ObjectMapGenerator is used to generate a object map of a instance
 * with all sub-instance and related class layout.
 * <p>
 * For the generator that only generate Class Layout Information.
 *
 * @see LayoutGenerator
 */
public interface ObjectMapGenerator {
    /**
     * Generate a object map of the input instance, with all sub-instance and related class layout.
     *
     * @param object the object instance
     * @return the object map of input object instance
     */
    ObjectLayoutMap generateObjectMap(Object object);
}
