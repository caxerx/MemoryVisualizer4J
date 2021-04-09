package com.caxerx.memoryvisualizer4j.implementation.visualizer;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;

import java.io.Serializable;

public class ObjectMapMessage implements Serializable {
    private final String objectType;
    private final long createTime;
    private final ObjectLayoutMap objectLayout;

    public ObjectMapMessage(String objectType, long createTime, ObjectLayoutMap objectLayout) {
        this.objectType = objectType;
        this.createTime = createTime;
        this.objectLayout = objectLayout;
    }

    public String getObjectType() {
        return objectType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public ObjectLayoutMap getObjectLayout() {
        return objectLayout;
    }
}
