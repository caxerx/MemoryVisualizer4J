package com.caxerx.memoryvisualizer4j.demo;

import com.caxerx.memoryvisualizer4j.MemoryVisualizer4J;

// -Djdk.attach.allowAttachSelf
public class GeneratorTest2 {
    public static void main(String[] args) {
        TestInnerClass inner = new TestInnerClass();
        MemoryVisualizer4J.getMemoryVisualizer().visualizeObject(new SimpleLinkingClass(inner));
        MemoryVisualizer4J.getMemoryVisualizer().visualizeObject(new SimpleLinkingClass(inner));

    }
}
