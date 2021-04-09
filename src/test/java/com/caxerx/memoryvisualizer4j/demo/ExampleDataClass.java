package com.caxerx.memoryvisualizer4j.demo;

public class ExampleDataClass {
    private TestInnerClass[] i;
    private TestInnerClass inner;
    private TestRecursiveRef rec = new TestRecursiveRef();

    public ExampleDataClass() {
        TestInnerClass ic = new TestInnerClass();
        i = new TestInnerClass[]{new TestInnerClass(), ic, ic};
        inner = ic;
    }
}
