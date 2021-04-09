package com.caxerx.memoryvisualizer4j.demo;

public class TestRecursiveRef {
    private TestRecursiveRef ref;

    public TestRecursiveRef() {
        this.ref = this;
    }
}
