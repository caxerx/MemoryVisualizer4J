package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.classlayout.ClassLayout;

/**
 * LayoutGenerator is used to generate a Class layout information. No object instance data will be
 * generated in this generator.
 * <p>
 * For the object map generator with instance-related information,
 *
 * @see ObjectMapGenerator
 */
public interface LayoutGenerator {
    /**
     * Generate the class layout of an object instance, if the instance is an array,
     * each element will be considered as a field with name "[index]"
     *
     * @param object the object instance
     * @return class layout of the input object instance
     */
    ClassLayout generateInstanceClassLayout(Object object);

    /**
     * Generate the class layout of a class
     *
     * @param clazz the class
     * @return class layout of the input class
     */
    ClassLayout generateClassLayout(Class<?> clazz);
}
