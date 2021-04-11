package com.caxerx.memoryvisualizer4j.implementation;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.demo.TestRecursiveRef;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutMap;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutObjectFieldItem;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutPrimitiveFieldItem;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.ObjectLayoutStringFieldItem;
import com.caxerx.memoryvisualizer4j.test.JOLTestModule;
import com.caxerx.memoryvisualizer4j.test.dataclass.*;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JOLObjectMapGeneratorTest {
    ObjectMapGenerator objectMapGenerator;

    @Before
    public void initDependency() {
        Injector injector = Guice.createInjector(new JOLTestModule());
        objectMapGenerator = injector.getInstance(ObjectMapGenerator.class);
    }

    @Test
    public void testMapCount() {
        assertEquals(1, objectMapGenerator.generateObjectMap(new TestCircularClass()).getClassLayouts().keySet().size());
        assertEquals(1, objectMapGenerator.generateObjectMap(new TestCircularClass()).getObjectLayouts().keySet().size());

        assertEquals(3, objectMapGenerator.generateObjectMap(new TestSharedReferenceClass()).getClassLayouts().keySet().size());
        assertEquals(3, objectMapGenerator.generateObjectMap(new TestSharedReferenceClass()).getObjectLayouts().keySet().size());

        assertEquals(9, objectMapGenerator.generateObjectMap(new TestPrimitiveArrayDataClass()).getClassLayouts().keySet().size());
        assertEquals(9, objectMapGenerator.generateObjectMap(new TestPrimitiveArrayDataClass()).getObjectLayouts().keySet().size());

        assertEquals(11, objectMapGenerator.generateObjectMap(new TestSimpleNestedClass()).getClassLayouts().keySet().size());
        assertEquals(11, objectMapGenerator.generateObjectMap(new TestSimpleNestedClass()).getObjectLayouts().keySet().size());
    }

    @Test
    public void testEntryPoint() {
        ObjectLayoutMap layout = objectMapGenerator.generateObjectMap(new TestCircularClass());
        assertTrue(layout.getObjectLayouts().values().contains(layout.getEntryPoint()));
    }

    @Test
    public void testTypeName() {
        ObjectLayoutMap layout = objectMapGenerator.generateObjectMap(new TestSimpleDataClass());
        assertEquals(TestSimpleDataClass.class.getCanonicalName(), layout.getEntryPoint().getType());
        assertEquals("boolean", layout.getEntryPoint().getFields().get(0).getType());
        assertEquals("boolean[]", layout.getEntryPoint().getFields().get(1).getType());

        ObjectLayoutMap layout2 = objectMapGenerator.generateObjectMap(new boolean[]{true, false});
        assertEquals("boolean[]", layout2.getEntryPoint().getType());
        assertEquals("boolean", layout2.getEntryPoint().getFields().get(0).getType());
        assertEquals("boolean", layout2.getEntryPoint().getFields().get(1).getType());
    }

    @Test
    public void testFieldName() {
        ObjectLayoutMap layout = objectMapGenerator.generateObjectMap(new TestSimpleDataClass());
        assertEquals(TestSimpleDataClass.class.getCanonicalName(), layout.getEntryPoint().getType());
        assertEquals("bool", layout.getEntryPoint().getFields().get(0).getName());
        assertEquals("boolArray", layout.getEntryPoint().getFields().get(1).getName());

        ObjectLayoutMap layout2 = objectMapGenerator.generateObjectMap(new boolean[]{true, false});
        assertEquals("[0]", layout2.getEntryPoint().getFields().get(0).getName());
        assertEquals("[1]", layout2.getEntryPoint().getFields().get(1).getName());

        ObjectLayoutMap layout3 = objectMapGenerator.generateObjectMap(new TestSimpleDataClass[]{new TestSimpleDataClass(), new TestSimpleDataClass()});
        assertEquals("[0]", layout3.getEntryPoint().getFields().get(0).getName());
        assertEquals("[1]", layout3.getEntryPoint().getFields().get(1).getName());
    }

    @Test
    public void testValue() {
        ObjectLayoutMap layout = objectMapGenerator.generateObjectMap(new TestSimpleDataClass());
        assertEquals(TestSimpleDataClass.class.getCanonicalName(), layout.getEntryPoint().getType());
        assertEquals(true, ((ObjectLayoutPrimitiveFieldItem) layout.getEntryPoint().getFields().get(0)).getValue());

        ObjectLayoutMap layout4 = objectMapGenerator.generateObjectMap(new TestStringAndNullClass());
        assertEquals(TestStringAndNullClass.class.getCanonicalName(), layout4.getEntryPoint().getType());
        assertEquals("42", ((ObjectLayoutStringFieldItem) layout4.getEntryPoint().getFields().get(0)).getValue());

        ObjectLayoutMap layout2 = objectMapGenerator.generateObjectMap(new boolean[]{true, false});
        assertEquals(true, ((ObjectLayoutPrimitiveFieldItem) layout2.getEntryPoint().getFields().get(0)).getValue());
        assertEquals(false, ((ObjectLayoutPrimitiveFieldItem) layout2.getEntryPoint().getFields().get(1)).getValue());

        ObjectLayoutMap layout3 = objectMapGenerator.generateObjectMap(new String[]{"42", null});
        assertEquals("42", ((ObjectLayoutStringFieldItem) layout3.getEntryPoint().getFields().get(0)).getValue());
        assertEquals("null", ((ObjectLayoutPrimitiveFieldItem) layout3.getEntryPoint().getFields().get(1)).getValue());
    }

    @Test
    public void testMemoryAddress() {
        ObjectLayoutMap layout = objectMapGenerator.generateObjectMap(new TestRecursiveRef());
        assertEquals(layout.getEntryPoint().getMemoryAddress(), ((ObjectLayoutObjectFieldItem) layout.getEntryPoint().getFields().get(0)).getObjectAddress());

        long offset = layout.getClassLayouts().get(layout.getEntryPoint().getMemoryAddress()).getLayout().get(1).getOffset();
        assertEquals(layout.getEntryPoint().getMemoryAddress() + offset, layout.getEntryPoint().getFields().get(0).getMemoryAddress());
    }

}
