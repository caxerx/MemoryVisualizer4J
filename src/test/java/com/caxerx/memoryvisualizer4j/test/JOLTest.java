package com.caxerx.memoryvisualizer4j.test;

import org.openjdk.jol.info.ClassData;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JOLTest {
    static Unsafe unsafe = getUnsafe();
    static final VirtualMachine vm = VM.current();

    public static void main(String... args) {

        System.out.println(Long.toHexString(ProcessHandle.current().pid()));

//        TestDataClass obj = new TestDataClass();
//
//        printObjectBase(0, obj);
//
//        TestRecursiveRef r1 = new TestRecursiveRef();
//        TestRecursiveRef r2 = new TestRecursiveRef();
//        r1.setRef(r2);
//        r2.setRef(r1);
//
//        printObjectBase(0, r1);

        int[] iarr = {1, 2, 3, 4, 5, 6, 7, 8};
        SimpleDataClass sdc = new SimpleDataClass();
        SimpleDataClass[] arr = {new SimpleDataClass(), sdc, sdc, sdc, new SimpleDataClass()};
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
        System.out.println(ClassLayout.parseInstance(sdc).toPrintable());

        System.out.println(ClassData.parseInstance(iarr).arrayComponentType());
//        unsafe.getAddress()
        return;
    }

    public static void printArray(int nest, Object object) {
        if (nest > 10) {
            System.out.println(" ".repeat(nest * 2) + "Reach Maximum Layer");
            return;
        }
        long base = vm.addressOf(object) + vm.arrayHeaderSize();
        for (int i = 0; i < Array.getLength(object); i++) {
            System.out.printf("%s%d: %s\n", " ".repeat(nest * 2), i, Long.toHexString(base + i * unsafe.arrayIndexScale(object.getClass())));
            printObjectBase(nest + 1, Array.get(object, i));
        }
    }

    public static void printObjectBase(int nest, Object object) {
        if (nest > 10) {
            System.out.println(" ".repeat(nest * 2) + "Reach Maximum Layer");
            return;
        }
        System.out.printf("%sObj %s: by vm:%s\n", " ".repeat(nest * 2), object.getClass().getTypeName(), Long.toHexString(vm.addressOf(object)));
        if (object.getClass().isArray()) {
            printArray(nest + 1, object);
        } else {
            printObject(nest + 1, object);
        }
    }

    public static void printObject(int nest, Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (isStatic) {
                    continue;
                }

                field.setAccessible(true);
                long objAddr = vm.addressOf(object);
                Object fObj = field.get(object);
                if (field.getType().isArray()) {
                    System.out.printf("%s%s(%s): by vm:%s, base:%s, idx:%s, size:%d\n",
                            " ".repeat(nest * 2),
                            field.getName(),
                            field.getType().toGenericString(),
                            Long.toHexString(vm.addressOf(fObj)),
                            unsafe.arrayBaseOffset(field.getType()),
                            unsafe.arrayIndexScale(field.getType()),
                            Array.getLength(fObj));

                    printArray(nest + 1, fObj);
                } else if (field.getType().isPrimitive()) {
                    System.out.printf("%s%s(%s): addr: %s, val: %s\n",
                            " ".repeat(nest * 2),
                            field.getName(),
                            field.getType().toGenericString(),
                            Long.toHexString(objAddr + unsafe.objectFieldOffset(field)),
                            fObj.toString());
                } else {
                    System.out.printf("%sObj %s(%s): addr by vm:%s\n", " ".repeat(nest * 2),
                            field.getName(),
                            field.getType().toGenericString(),
                            Long.toHexString(vm.addressOf(field.get(object))));
                    printObjectBase(nest + 1, field.get(object));
                }
            } catch (IllegalAccessException | IllegalArgumentException e) {
                System.out.printf("%s%s(%s): %s\n", " ".repeat(nest * 2), field.getName(), field.getType().toString(), "Inaccessible:" + e.getClass().getName());
            }
        }

    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}