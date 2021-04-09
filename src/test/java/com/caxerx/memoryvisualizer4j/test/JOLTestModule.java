package com.caxerx.memoryvisualizer4j.test;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.implementation.jol.JOLLayoutGenerator;
import com.caxerx.memoryvisualizer4j.implementation.jol.JOLObjectMapGenerator;
import com.google.inject.AbstractModule;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

public class JOLTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LayoutGenerator.class).to(JOLLayoutGenerator.class);
        bind(ObjectMapGenerator.class).to(JOLObjectMapGenerator.class);

        bind(VirtualMachine.class).toInstance(VM.current());
    }
}
