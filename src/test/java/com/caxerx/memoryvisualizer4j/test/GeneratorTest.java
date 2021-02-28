package com.caxerx.memoryvisualizer4j.test;

import com.caxerx.memoryvisualizer4j.MemoryVisualizer4J;
import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.MemoryVisualizer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// -Djdk.attach.allowAttachSelf
public class GeneratorTest {
    public static void main(String[] args) {
//        LayoutGenerator generator = MemoryVisualizer4J.getLayoutGenerator();
//
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//
//        String[] test = {"A", "B", "A"};
//        int[] t2 = {0, 1, 2};
//        boolean[] t3 = {true, true, false};
//        long[] t4 = {0, 1000000000L, 2042305823094L};
//

        MemoryVisualizer4J.getMemoryVisualizer().visualizeObject(new SimpleDataClass());
        MemoryVisualizer4J.getMemoryVisualizer().visualizeObject(new TestDataClass());

        long[] t4 = {0, 1000000000L, 2042305823094L};
        MemoryVisualizer4J.getMemoryVisualizer().visualizeObject(t4);
    }
}
