package com.caxerx.memoryvisualizer4j.demo;

import com.caxerx.memoryvisualizer4j.MemoryVisualizer4J;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;

import java.util.LinkedList;

// -Djdk.attach.allowAttachSelf
public class GeneratorTest {
    public static void main(String[] args) {
//        LayoutGenerator generator = MemoryVisualizer4J.getLayoutGenerator();
//
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();

        String[] test = {"A", "B", "A"};
        int[] t2 = {0, 1, 2};
        boolean[] t3 = {true, true, false};
        long[] t4 = {0, 1000000000L, 2042305823094L};
        TestInnerClass[] t5 = {new TestInnerClass(), new TestInnerClass(), new TestInnerClass()};

        MemoryVisualizer4J.getMemoryVisualizer().registerObject(null);
        MemoryVisualizer4J.getMemoryVisualizer().registerObject(null, null);
        MemoryVisualizer4J.getMemoryVisualizer().registerObject(t4, t5);

        MemoryVisualizer4J.getMemoryVisualizer()
                .registerObject(new SimpleDataClass())
                .registerObject(new TestDataClass())
                .registerObject(new TestRecursiveRef())
                .registerObject(new ExampleDataClass());

        TestInnerClass inner = new TestInnerClass();

        SimpleLinkedList list = new SimpleLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        MemoryVisualizer4J.getMemoryVisualizer()
                .registerObject(new SimpleLinkingClass(inner), new SimpleLinkingClass(inner))
                .registerObject(list).visualize();


    }
}
