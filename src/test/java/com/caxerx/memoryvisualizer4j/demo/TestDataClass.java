package com.caxerx.memoryvisualizer4j.demo;

public class TestDataClass {
    private String str1 = "STR1";
    private int int1 = 1;
    private char char1 = '1';
    private short short1 = 1;
    private long long1 = 1;
    private byte byte1 = 1;
    private byte[] byteArr1 = {0, 1, 2, 3};
    private byte[][] byteArrNested1 = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private TestInnerClass inn1 = new TestInnerClass();
    private TestInnerClass[] innArr1 = {new TestInnerClass(), new TestInnerClass(), new TestInnerClass()};
    private TestInnerClass[][] innArrNested1 = {{new TestInnerClass(), new TestInnerClass()}, {new TestInnerClass(), new TestInnerClass()}};
}
