# MemoryVisualizer4J
MemoryVisualizer4J is a flexible and easy-to-use tool that solve the current situation of explaining memory layout of Java. 

# Getting Start
Check [Getting Start](./GettingStart) to install the library. 

Then check [Introduce to Visualizer UI](./IntroduceToVisualizerUI) for the User Guide of Visualizer UI.

# Example
There is a simple example for visualizing an Integer array, and a String array
```java
import com.caxerx.memoryvisualizer4j.MemoryVisualizer4J;

public class Main {
    public static void main(String[] args) {
        int[] intArray = {1,2,3};
        String[] stringArray = {"Hello", "World"};
        MemoryVisualizer4J.getMemoryVisualizer().registerObject(intArray, stringArray).visualize();
    }
}
```