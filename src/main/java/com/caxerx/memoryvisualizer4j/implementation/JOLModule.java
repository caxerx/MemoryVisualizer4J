package com.caxerx.memoryvisualizer4j.implementation;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.MemoryVisualizer;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;
import com.caxerx.memoryvisualizer4j.implementation.jol.JOLLayoutGenerator;
import com.caxerx.memoryvisualizer4j.implementation.jol.JOLObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.implementation.visualizer.WebUIVisualizer;
import com.caxerx.memoryvisualizer4j.implementation.visualizer.VisualizerWebServer;
import com.google.inject.AbstractModule;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

public class JOLModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LayoutGenerator.class).to(JOLLayoutGenerator.class);
        bind(MemoryVisualizer.class).to(WebUIVisualizer.class);
        bind(ObjectMapGenerator.class).to(JOLObjectMapGenerator.class);

        bind(VirtualMachine.class).toInstance(VM.current());

        VisualizerWebServer webSocketServer = new VisualizerWebServer(20215);
        bind(StickyBroadcaster.class).toInstance(webSocketServer);
    }
}
