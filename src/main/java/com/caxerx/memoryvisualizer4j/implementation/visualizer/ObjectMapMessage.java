package com.caxerx.memoryvisualizer4j.implementation.visualizer;

import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayout;

import java.io.Serializable;

public class ObjectMapMessage implements Serializable {
    private final String objectType;
    private final long createTime;
    private final ObjectLayout objectLayout;

    public ObjectMapMessage(String objectType, long createTime, ObjectLayout objectLayout) {
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

    public ObjectLayout getObjectLayout() {
        return objectLayout;
    }
}
