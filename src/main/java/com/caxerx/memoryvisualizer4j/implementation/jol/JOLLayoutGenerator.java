package com.caxerx.memoryvisualizer4j.implementation.jol;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.layout.classlayout.*;
import com.caxerx.memoryvisualizer4j.util.TypeUtils;
import com.google.common.collect.ImmutableList;
import org.openjdk.jol.info.ClassData;
import org.openjdk.jol.info.FieldLayout;

import java.util.ArrayList;
import java.util.List;

public class JOLLayoutGenerator implements LayoutGenerator {
    @Override
    public ClassLayout generateInstanceClassLayout(Object object) {
        if (object.getClass().isArray()) {
            return this.getInstanceArrayLayout(object);
        }
        return this.generateClassLayout(object.getClass());
    }

    @Override
    public ClassLayout generateClassLayout(Class clazz) {
        org.openjdk.jol.info.ClassLayout layout = org.openjdk.jol.info.ClassLayout.parseClass(clazz);
        List<ClassLayoutItem> items = new ArrayList<>();
        if (layout.headerSize() > 0) {
            items.add(new ClassLayoutPaddingItem(layout.headerSize(), 0, ClassLayoutPaddingItemType.HEADER));
        }

        long lastOffset = layout.headerSize();
        for (FieldLayout i : layout.fields()) {
            if (i.offset() > lastOffset) {
                items.add(new ClassLayoutPaddingItem(i.offset() - lastOffset, lastOffset, ClassLayoutPaddingItemType.ALIGNMENT));
            }
            items.add(new ClassLayoutFieldItem(i.size(), i.offset(), TypeUtils.isPrimitive(i.typeClass()) ? i.typeClass() : i.typeClass(), i.name()));
            lastOffset = i.offset() + i.size();
        }
        if (lastOffset != layout.instanceSize()) {
            items.add(new ClassLayoutPaddingItem(layout.instanceSize() - lastOffset, lastOffset, ClassLayoutPaddingItemType.ALIGNMENT));
        }
        return new ClassLayout(ImmutableList.copyOf(items), layout.instanceSize(), clazz.getCanonicalName());
    }

    public ArrayLayout getInstanceArrayLayout(Object object) {
        if (!object.getClass().isArray()) {
            throw new RuntimeException("The layout is not an array");
        }

        org.openjdk.jol.info.ClassLayout layout = org.openjdk.jol.info.ClassLayout.parseInstance(object);
        ClassData data = ClassData.parseInstance(object);

        if (layout.fields().size() != 1) {
            throw new RuntimeException("Unexpected field on array type");
        }

        List<ClassLayoutItem> items = new ArrayList<>();

        if (layout.headerSize() > 0) {
            items.add(new ClassLayoutPaddingItem(layout.headerSize(), 0, ClassLayoutPaddingItemType.HEADER));
        }

        long lastOffset = layout.headerSize();

        FieldLayout elementField = layout.fields().first();
        long eachSize = elementField.size() / data.arrayLength();
        for (int i = 0; i < data.arrayLength(); i++) {
            items.add(new ClassLayoutFieldItem(eachSize, lastOffset, TypeUtils.isPrimitive(elementField.typeClass()) ? elementField.typeClass() : object.getClass().getComponentType().getCanonicalName(), String.format("[%d]", i)));
            lastOffset += eachSize;
        }

        if (lastOffset != layout.instanceSize()) {
            items.add(new ClassLayoutPaddingItem(layout.instanceSize() - lastOffset, lastOffset, ClassLayoutPaddingItemType.ALIGNMENT));
        }
        return new ArrayLayout(ImmutableList.copyOf(items), layout.instanceSize(), object.getClass().getCanonicalName(), eachSize);
    }
}
