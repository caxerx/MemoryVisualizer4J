package com.caxerx.memoryvisualizer4j.implementation.jol;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.layout.classlayout.ClassLayout;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.*;
import com.caxerx.memoryvisualizer4j.util.TypeUtils;
import com.google.inject.Inject;
import org.openjdk.jol.info.ClassData;
import org.openjdk.jol.info.FieldData;
import org.openjdk.jol.info.FieldLayout;
import org.openjdk.jol.vm.VirtualMachine;

import java.lang.reflect.Array;
import java.util.*;

public class JOLObjectMapGenerator implements ObjectMapGenerator {
    @Inject
    LayoutGenerator layoutGenerator;

    @Inject
    VirtualMachine vm;

    @Override
    public ObjectLayoutMap generateObjectMap(Object object) {
        if (object == null) {
            return new ObjectLayoutMap(new HashMap<>(), new HashMap<>(), new ObjectLayout("null", new LinkedList<>(), 0));
        }
        HashMap<Long, ClassLayout> classLayouts = new HashMap<>();
        HashMap<Long, ObjectLayout> objectLayouts = new HashMap<>();
        return generateUnknownTree(object, classLayouts, objectLayouts);
    }

    private ObjectLayoutMap generateUnknownTree(Object object, HashMap<Long, ClassLayout> classLayouts, HashMap<Long, ObjectLayout> objectLayouts) {
        if (ClassData.parseInstance(object).isArray()) {
            return generateArrayTree(object, classLayouts, objectLayouts);
        }
        return generateObjectTree(object, classLayouts, objectLayouts);
    }

    private ObjectLayoutMap generateArrayTree(Object object, HashMap<Long, ClassLayout> classLayouts, HashMap<Long, ObjectLayout> objectLayouts) {
        List<ObjectLayoutFieldItem> fields = new LinkedList<>();
        long currentAddress = vm.addressOf(object);
        ClassLayout classLayout = layoutGenerator.generateInstanceClassLayout(object);
        classLayouts.put(currentAddress, classLayout);
        ObjectLayout objectLayout = new ObjectLayout(classLayout.getType(), fields, currentAddress);
        objectLayouts.put(currentAddress, objectLayout);

        FieldLayout field = org.openjdk.jol.info.ClassLayout.parseInstance(object).fields().first();
        ClassData classData = ClassData.parseInstance(object);

        boolean primitive = TypeUtils.isPrimitive(classData.arrayComponentType());

        for (int i = 0; i < classData.arrayLength(); i++) {
            Object value = Array.get(object, i);
            if (primitive) {
                fields.add(
                        new ObjectLayoutPrimitiveFieldItem(String.format("[%d]", i), field.typeClass(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), value)
                );
            } else if (value instanceof String) {
                fields.add(
                        new ObjectLayoutStringFieldItem(String.format("[%d]", i), value.getClass().getCanonicalName(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), vm.addressOf(value), (String) value)
                );
                if (!objectLayouts.containsKey(vm.addressOf(value))) {
                    generateUnknownTree(value, classLayouts, objectLayouts);
                }
            } else if (value == null) {
                fields.add(
                        new ObjectLayoutPrimitiveFieldItem(String.format("[%d]", i), classData.name(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), "null")
                );
            } else {
                fields.add(
                        new ObjectLayoutObjectFieldItem(String.format("[%d]", i), value.getClass().getCanonicalName(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), vm.addressOf(value))
                );
                if (!objectLayouts.containsKey(vm.addressOf(value))) {
                    generateUnknownTree(value, classLayouts, objectLayouts);
                }
            }
        }

        return new ObjectLayoutMap(objectLayouts, classLayouts, objectLayout);
    }

    private ObjectLayoutMap generateObjectTree(Object object, HashMap<Long, ClassLayout> classLayouts, HashMap<Long, ObjectLayout> objectLayouts) {
        List<ObjectLayoutFieldItem> fields = new LinkedList<>();
        long currentAddress = vm.addressOf(object);
        ClassLayout classLayout = layoutGenerator.generateInstanceClassLayout(object);
        classLayouts.put(currentAddress, classLayout);
        ObjectLayout objectLayout = new ObjectLayout(classLayout.getType(), fields, currentAddress);
        objectLayouts.put(currentAddress, objectLayout);

        for (FieldData field : ClassData.parseInstance(object).fields()) {
            field.refField().setAccessible(true);
            if (TypeUtils.isPrimitive(field.typeClass())) {
                fields.add(generatePrimitiveField(object, field));
            } else {
                try {
                    Object fieldObject = field.refField().get(object);
                    if (fieldObject != null && !objectLayouts.containsKey(vm.addressOf(fieldObject))) {
                        generateUnknownTree(fieldObject, classLayouts, objectLayouts);
                    }
                } catch (IllegalAccessException e) {
                    // ignore
                } finally {
                    fields.add(generateField(object, field));
                }

            }
        }

        return new ObjectLayoutMap(objectLayouts, classLayouts, objectLayout);
    }

    private ObjectLayoutFieldItem generatePrimitiveField(Object object, FieldData field) {
        try {
            return new ObjectLayoutPrimitiveFieldItem(field.name(), field.typeClass(), vm.addressOf(object) + field.vmOffset(), field.refField().get(object));
        } catch (IllegalAccessException e) {
            return new ObjectLayoutPrimitiveFieldItem(field.name(), field.typeClass(), vm.addressOf(object) + field.vmOffset(), "[Error] Inaccessible");
        }
    }

    private ObjectLayoutFieldItem generateField(Object object, FieldData field) {
        try {
            Object fieldObject = field.refField().get(object);
            if (fieldObject instanceof String) {
                return new ObjectLayoutStringFieldItem(field.name(), field.typeClass(), vm.addressOf(object) + field.vmOffset(), vm.addressOf(fieldObject), (String) fieldObject);
            }
            return new ObjectLayoutObjectFieldItem(field.name(), field.typeClass(), vm.addressOf(object) + field.vmOffset(), vm.addressOf(fieldObject));
        } catch (IllegalAccessException e) {
            return new ObjectLayoutPrimitiveFieldItem(field.name(), field.typeClass(), vm.addressOf(object) + field.vmOffset(), "[Error] Inaccessible");
        }
    }
}
