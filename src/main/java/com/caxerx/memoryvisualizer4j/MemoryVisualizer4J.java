package com.caxerx.memoryvisualizer4j;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.MemoryVisualizer;
import com.caxerx.memoryvisualizer4j.implementation.JOLModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class MemoryVisualizer4J {
    private final static MemoryVisualizer4J INSTANCE;

    @Inject
    private LayoutGenerator layoutGenerator;

    @Inject
    private MemoryVisualizer memoryVisualizer;

    static {
        Injector injector = Guice.createInjector(new JOLModule());
        INSTANCE = injector.getInstance(MemoryVisualizer4J.class);
    }

    public static LayoutGenerator getLayoutGenerator() {
        return INSTANCE.layoutGenerator;
    }

    public static MemoryVisualizer getMemoryVisualizer() {
        return INSTANCE.memoryVisualizer;
    }
}
