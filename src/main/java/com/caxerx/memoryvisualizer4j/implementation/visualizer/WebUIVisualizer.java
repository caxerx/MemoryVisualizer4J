package com.caxerx.memoryvisualizer4j.implementation.visualizer;

import com.caxerx.memoryvisualizer4j.api.MemoryVisualizer;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class WebUIVisualizer implements MemoryVisualizer {
    @Inject
    public StickyBroadcaster broadcaster;

    @Inject
    public ObjectMapGenerator objectMapGenerator;

    private final LinkedList<Object> registeredObject = new LinkedList<>();

    @Override
    public MemoryVisualizer registerObject(Object... object) {
        if (object == null) return this;
        object = Arrays.stream(object).filter(Objects::nonNull).toArray();
        registeredObject.addAll(Arrays.asList(object));
        return this;
    }

    @Override
    public MemoryVisualizer visualize() {
        Gson gson = new Gson();
        for (Object obj : registeredObject) {
            ObjectLayoutMap objectMap = objectMapGenerator.generateObjectMap(obj);
            broadcaster.broadcastSticky(new ObjectMapMessage(objectMap.getEntryPoint().getType(), System.currentTimeMillis(), objectMap));
        }
        return this;
    }
}
