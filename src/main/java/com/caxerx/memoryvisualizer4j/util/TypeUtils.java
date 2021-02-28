package com.caxerx.memoryvisualizer4j.util;

public class TypeUtils {
    public static boolean isPrimitive(String type) {
        switch (type) {
            case "boolean":
            case "byte":
            case "char":
            case "short":
            case "int":
            case "long":
            case "float":
            case "double":
                return true;
            default:
                return false;
        }
    }
}
