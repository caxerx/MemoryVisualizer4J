package com.caxerx.memoryvisualizer4j.implementation;

import static org.junit.Assert.*;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.layout.classlayout.*;
import com.caxerx.memoryvisualizer4j.test.JOLTestModule;
import com.caxerx.memoryvisualizer4j.test.dataclass.TestPrimitiveDataClass;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class JOLLayoutGeneratorTest {
    ClassLayout layoutTestPrimitiveDataClass;

    @Before
    public void initDependency() {
        Injector injector = Guice.createInjector(new JOLTestModule());
        LayoutGenerator layoutGenerator = injector.getInstance(LayoutGenerator.class);
        layoutTestPrimitiveDataClass = layoutGenerator.generateClassLayout(TestPrimitiveDataClass.class);
    }

    @Test
    public void testInstanceSize() {
        assertEquals(48, layoutTestPrimitiveDataClass.getInstanceSize());
    }

    @Test
    public void testFieldCount() {
        assertEquals(10, layoutTestPrimitiveDataClass.getLayout().size());
    }

    @Test
    public void testFieldType() {
        ClassLayoutItem header = layoutTestPrimitiveDataClass.getLayout().get(0);
        assertTrue(header instanceof ClassLayoutPaddingItem);
        assertEquals(((ClassLayoutPaddingItem) header).getType(), ClassLayoutPaddingItemType.HEADER);
    }

    @Test
    public void testFieldName() {

    }

    @Test
    public void testFieldSize() {
        List<String> typeList = Arrays.asList("int", "long", "double", "float", "char", "short", "byte", "boolean");
        int[] typeSize = {4, 8, 8, 4, 2, 2, 1, 1};
        layoutTestPrimitiveDataClass.getLayout().stream().filter(field -> field instanceof ClassLayoutFieldItem).forEach(field -> {
            assertEquals(typeSize[typeList.indexOf(((ClassLayoutFieldItem) field).getType())], field.getSize());
        });
    }

    @Test
    public void testFieldOffset() {

    }
}
