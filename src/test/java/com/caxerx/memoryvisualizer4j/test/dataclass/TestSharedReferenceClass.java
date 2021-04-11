package com.caxerx.memoryvisualizer4j.test.dataclass;

public class TestSharedReferenceClass {
    public SharedReferenceInnerClass c1 = new SharedReferenceInnerClass(this);
    public SharedReferenceInnerClass c2 = new SharedReferenceInnerClass(this);
}

class SharedReferenceInnerClass {
    public TestSharedReferenceClass ref;

    public SharedReferenceInnerClass(TestSharedReferenceClass ref) {
        this.ref = ref;
    }
}
