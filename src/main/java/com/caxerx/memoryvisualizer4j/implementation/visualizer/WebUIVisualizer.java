package com.caxerx.memoryvisualizer4j.implementation.visualizer;

import com.caxerx.memoryvisualizer4j.api.MemoryVisualizer;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class WebUIVisualizer implements MemoryVisualizer {
    @Inject
    public StickyBroadcaster broadcaster;

    @Inject
    public ObjectMapGenerator objectMapGenerator;

    @Override
    public void visualizeObject(Object object) {
        visualizeObjectMap(objectMapGenerator.generateObjectMap(object));
    }

    @Override
    public void visualizeObjectMap(ObjectLayoutMap objectLayout) {
        Gson gson = new Gson();
        broadcaster.broadcastSticky(gson.toJson(new ObjectMapMessage(objectLayout.getEntryPoint().getType(), System.currentTimeMillis(), objectLayout)));
    }
}
