package com.caxerx.memoryvisualizer4j.implementation.jol;

import com.caxerx.memoryvisualizer4j.api.LayoutGenerator;
import com.caxerx.memoryvisualizer4j.api.ObjectMapGenerator;
import com.caxerx.memoryvisualizer4j.layout.classlayout.ClassLayout;
import com.caxerx.memoryvisualizer4j.layout.objectlayout.*;
import com.caxerx.memoryvisualizer4j.util.TypeUtils;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.openjdk.jol.info.ClassData;
import org.openjdk.jol.info.FieldData;
import org.openjdk.jol.info.FieldLayout;
import org.openjdk.jol.vm.VirtualMachine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class JOLObjectMapGenerator implements ObjectMapGenerator {
    private static final int GENERATION_DEPTH = 12;

    @Inject
    LayoutGenerator layoutGenerator;

    @Inject
    VirtualMachine vm;

    @Override
    public ObjectLayout generateObjectMap(Object object) {
        return generateObjectMap(object, 0);
    }

    private ObjectLayout generateArrayMap(Object object, int depth) {
        if (depth > GENERATION_DEPTH) {
            return null;
        }

        if (!object.getClass().isArray()) {
            throw new RuntimeException("Unexpected Non-array object in generateArrayMap");
        }

        ClassLayout layout = this.layoutGenerator.generateInstanceClassLayout(object);

        List<ObjectLayoutFieldItem> items = new ArrayList<>();

        SortedSet<FieldLayout> fields = org.openjdk.jol.info.ClassLayout.parseInstance(object).fields();
        ClassData classData = ClassData.parseInstance(object);

        if (fields.size() > 1) {
            throw new RuntimeException("Unexpected field in array class");
        }

        FieldLayout field = fields.first();

        boolean primitive = TypeUtils.isPrimitive(classData.arrayComponentType());

        for (int i = 0; i < classData.arrayLength(); i++) {
            Object value = Array.get(object, i);
            items.add(
                    primitive ?
                            new ObjectLayoutPrimitiveFieldItem(String.format("[%d]", i), field.typeClass(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), value) :
                            (value instanceof String ?
                                    new ObjectLayoutStringFieldItem(String.format("[%d]", i), value.getClass().getCanonicalName(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), generateObjectMap(value, depth + 1), (String) value)
                                    : new ObjectLayoutObjectFieldItem(String.format("[%d]", i), value.getClass().getCanonicalName(), vm.addressOf(object) + field.offset() + (i * (field.size() / classData.arrayLength())), generateObjectMap(value, depth + 1)))
            );
        }

        return new ObjectLayout(layout, ImmutableList.copyOf(items), vm.addressOf(object));

    }

    private ObjectLayout generateObjectMap(Object object, int depth) {
        if (depth > GENERATION_DEPTH) {
            return null;
        }

        if (object.getClass().isArray()) {
            return generateArrayMap(object, depth);
        }

        ClassLayout layout = this.layoutGenerator.generateInstanceClassLayout(object);

        List<ObjectLayoutFieldItem> items = new ArrayList<>();

        for (FieldData field : ClassData.parseInstance(object).fields()) {
            Object value = "[Error] Inaccessible";
            boolean inaccessible = false;
            try {
                field.refField().setAccessible(true);
                value = field.refField().get(object);
            } catch (IllegalAccessException e) {
                inaccessible = true;
            }

            if (TypeUtils.isPrimitive(field.typeClass())) {
                items.add(new ObjectLayoutPrimitiveFieldItem(field.name(), field.typeClass(), vm.addressOf(object) + field.vmOffset(), value));
            } else {
                if (value instanceof String && !inaccessible) {
                    items.add(new ObjectLayoutStringFieldItem(field.name(), value.getClass().getCanonicalName(), vm.addressOf(object) + field.vmOffset(), inaccessible ? null : generateObjectMap(value, depth + 1), (String) value));
                } else {
                    items.add(new ObjectLayoutObjectFieldItem(field.name(), value.getClass().getCanonicalName(), vm.addressOf(object) + field.vmOffset(), inaccessible ? null : generateObjectMap(value, depth + 1)));
                }
            }
        }

        return new ObjectLayout(layout, ImmutableList.copyOf(items), vm.addressOf(object));
    }
}
