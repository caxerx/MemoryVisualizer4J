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
    ArrayLayout layoutArray;
    List<String> typeList = Arrays.asList("int", "long", "double", "float", "char", "short", "byte", "boolean");
    int[] typeSize = {4, 8, 8, 4, 2, 2, 1, 1};

    @Before
    public void initDependency() {
        Injector injector = Guice.createInjector(new JOLTestModule());
        LayoutGenerator layoutGenerator = injector.getInstance(LayoutGenerator.class);
        layoutTestPrimitiveDataClass = layoutGenerator.generateInstanceClassLayout(new TestPrimitiveDataClass());
        layoutArray = (ArrayLayout) layoutGenerator.generateInstanceClassLayout(typeSize);
    }

    @Test
    public void testInstanceSize() {
        assertEquals(48, layoutTestPrimitiveDataClass.getInstanceSize());
        assertEquals(48, layoutArray.getInstanceSize());
    }

    @Test
    public void testFieldCount() {
        assertEquals(10, layoutTestPrimitiveDataClass.getLayout().size());
        assertEquals(9, layoutArray.getLayout().size());
    }

    @Test
    public void testArrayItemSize(){
        assertEquals(4, layoutArray.getItemSize());
    }

    @Test
    public void testFieldType() {
        ClassLayoutItem header = layoutTestPrimitiveDataClass.getLayout().get(0);
        assertTrue(header instanceof ClassLayoutPaddingItem);
        assertEquals(((ClassLayoutPaddingItem) header).getType(), ClassLayoutPaddingItemType.HEADER);
    }

    @Test
    public void testFieldName() {
        String[] fieldNames = {"primitiveInteger", "primitiveLong", "primitiveDouble", "primitiveFloat", "primitiveCharacter", "primitiveShort", "primitiveByte", "primitiveBoolean"};
        String[] fieldName = layoutTestPrimitiveDataClass.getLayout().stream().filter(i -> i instanceof ClassLayoutFieldItem).map(i -> ((ClassLayoutFieldItem) i).getName()).toArray(String[]::new);
        assertArrayEquals(fieldName, fieldNames);

        String[] fieldNames2 = {"[0]", "[1]", "[2]", "[3]", "[4]", "[5]", "[6]", "[7]"};
        String[] fieldName2 = layoutArray.getLayout().stream().filter(i -> i instanceof ClassLayoutFieldItem).map(i -> ((ClassLayoutFieldItem) i).getName()).toArray(String[]::new);
        assertArrayEquals(fieldName, fieldNames);
    }

    @Test
    public void testFieldSize() {
        layoutTestPrimitiveDataClass.getLayout().stream().filter(field -> field instanceof ClassLayoutFieldItem).forEach(field -> {
            assertEquals(typeSize[typeList.indexOf(((ClassLayoutFieldItem) field).getType())], field.getSize());
        });

        layoutArray.getLayout().stream().filter(field -> field instanceof ClassLayoutFieldItem).forEach(field -> {
            assertEquals(typeSize[typeList.indexOf(((ClassLayoutFieldItem) field).getType())], field.getSize());
        });
    }

    @Test
    public void testFieldOffset() {
        long[] size = layoutTestPrimitiveDataClass.getLayout().stream().mapToLong(ClassLayoutItem::getSize).toArray();
        long[] offset = layoutTestPrimitiveDataClass.getLayout().stream().mapToLong(ClassLayoutItem::getOffset).toArray();
        assertEquals(0, offset[0]);
        long sum = 0;
        for (int i = 1; i < offset.length; i++) {
            sum += size[i - 1];
            assertEquals(sum, offset[i]);
        }

        long[] size2 = layoutArray.getLayout().stream().mapToLong(ClassLayoutItem::getSize).toArray();
        long[] offset2 = layoutArray.getLayout().stream().mapToLong(ClassLayoutItem::getOffset).toArray();
        assertEquals(0, offset2[0]);
        long sum2 = 0;
        for (int i = 1; i < offset2.length; i++) {
            sum2 += size2[i - 1];
            assertEquals(sum2, offset2[i]);
        }
    }
}
