package com.caxerx.memoryvisualizer4j.layout.objectlayout;

import com.caxerx.memoryvisualizer4j.layout.classlayout.ClassLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ObjectLayoutMap implements Serializable {
    Map<Long, ObjectLayout> objectLayouts;
    Map<Long, ClassLayout> classLayouts;
    ObjectLayout entryPoint;

    public ObjectLayoutMap(Map<Long, ObjectLayout> objectLayouts, HashMap<Long, ClassLayout> classLayouts, ObjectLayout entryPoint) {
        this.objectLayouts = objectLayouts;
        this.classLayouts = classLayouts;
        this.entryPoint = entryPoint;
    }

    public Map<Long, ObjectLayout> getObjectLayouts() {
        return objectLayouts;
    }

    public Map<Long, ClassLayout> getClassLayouts() {
        return classLayouts;
    }

    public ObjectLayout getEntryPoint() {
        return entryPoint;
    }
}
