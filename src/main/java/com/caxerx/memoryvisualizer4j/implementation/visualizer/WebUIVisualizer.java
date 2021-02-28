package com.caxerx.memoryvisualizer4j.implementation.visualizer;

import com.caxerx.memoryvisualizer4j.api.MemoryVisualizer;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayout;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class WebUIVisualizer implements MemoryVisualizer {
    @Inject
    StickyBroadcaster broadcaster;

    @Inject
    ObjectMapGenerator objectMapGenerator;

    @Override
    public void visualizeObject(Object object) {
        visualizeObjectMap(objectMapGenerator.generateObjectMap(object));
    }

    @Override
    public void visualizeObjectMap(ObjectLayout objectLayout) {
        Gson gson = new Gson();
        String[] typeArray = objectLayout.getClassLayout().getType().split("\\.");
        broadcaster.broadcastSticky(gson.toJson(new ObjectMapMessage(typeArray[typeArray.length - 1], System.currentTimeMillis(), objectLayout)));
    }
}
