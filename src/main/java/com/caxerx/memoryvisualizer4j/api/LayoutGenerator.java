package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.layout.classlayout.ClassLayout;

public interface LayoutGenerator {
    ClassLayout generateInstanceClassLayout(Object object);

    ClassLayout generateClassLayout(Class clazz);
}
