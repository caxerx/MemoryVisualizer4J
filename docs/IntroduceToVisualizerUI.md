# Introduce to Visualizer UI
Welcome to the Visulizer UI of MemoryVisualizer4J. In this page, a full introduce of the Visualizer UI will be provided. Make sure you have checked the [Getting Start](./GettingStart) guide to setup your visualizer first.

## Main UI
![](images/8649b0faf706c431c6252f2aa33d108cc777f63022438929e1a46f1784b74448.png)  
This is the main UI of the Visualizer. 

### Switching between Table view and Diagram view
In the top toolbar, there is a switch ![](images/d65268d9f7e7e74475272452010330da9d4d03c832851b0bddbe5261ea67acc4.png) that you can toggle between diagram view and table view.

### Connectivity
In the top of left bar, there are the status about the connectivity between Java and Web UI. In most of the situation, you will see `Connected` in this section.

![](images/2afd1d13fc3342d2dd23a619669a717f74255849c3de387f7fadcc20efec1077.png)  

But in some of the situation. For example, you had stopped the Java Program. The Visualizer will show `Connecting` until you open the Java Program again.

![](images/b4ca26152b698b846110c149b9a4276271ed84b2bb7a4c146c573985032ca214.png)  

In the status of `Connecting`, you will still able to check the previous visualized object in the UI.

### Selecting Object
In the bottom section of left bar, it will show all of the object you had visualized. Click on the object to view the layout of it.

![](images/81e53e43a745ae8bbca1d540ce9f63a70f7d48cd69a1dac8df0a9ceeb768c99b.png)  

In Diagram view, you will able to show multiple object at once. But in table view, only 1 object can be show in the table each time.

## Diagram View
![](images/bb522544ddac23863d4f4aa4529973a5fb97a12bc6439e35c8e1cdbbb87ba2e8.png)  

In diagram view, you can select object and show it as a diagram. Multiple object can be select and show as a diagram.

### Add and Remove Object
Click the object in the left panel to add or remove the object in diagram. The selected object will be highlighted on the list.

![](images/3e73bb75097eb2958e485bd91ddc49777416ff3e3afc0fbe6d37a7062b86221d.png)  

![](images/4afb5a400bd3200a060b93cd80f99f5c70ed305f280058d707eb9f8c64356f87.png)  


### Moving Node
Sometimes the link of reference in the diagram will be overlapped. You can move the object node in Diagram View by dragging to the correct place.
![](images/f70f4aafea0ad9fda45b7292d36e0d17df4a3c911de4fca938ed4e28a1edaf39.png)  

### Reseting Layout
If you want to reset the diagram to default layout, Click the ![`Reset Layout`](images/f05b2859ba38e5f8c2b87837658988a83d3289f5f4a1c25a600702473792753e.png) button on the top bar of Diagram View.

![](images/b4ea8bca17c19c45c96dbdf44bc38b1f552cd2b4a0eb5da85e64311eb2cd83ac.png)  

### Converting Vertical and Horizontal Layout
Sometimes, object can be more suitable to show as a horizontal Layout. The layout can be switched on the top bar of Diagram View ![](images/a7f69ae43035460607ddbb96c9aaefcf7bf16c4a2e9e4e2198dd3ec5634655fa.png)

#### Example of Vertical Layout Diagram

![](images/c4c4d64e3abb3ed4076ed405d6df1828585af3c4f8e37f66645f2c390d9be6b0.png)  

#### Example of Horizontal Layout Diagram

![](images/f784e231197ea70eea9e938cb13288d31cf563b0697bb1e95b6471cc23d814d8.png)  

### Exporting Diagram
If you wish to save the diagram, click the ![`Export Diagram`](images/ff060803c4c67bfc23c4acd1ed682e8791cd23c10fe65bcd99136ecf334b9da3.png) Button on the top bar of Diagram View to save it.
> Be aware that the saved diagram will only be the visible area of your browser. Zoom-out the image to save whole diagram if your diagram is large.

## Table View
In Table view, you can view the details of the Object and Class Layout of the object. In addition, the padding and memory offset of each fields.

### Selecting Object
Same as Diagram View, select the object on the left bar to check the layout.

![](images/81e53e43a745ae8bbca1d540ce9f63a70f7d48cd69a1dac8df0a9ceeb768c99b.png)  

### Navigate the Object
In table view, you can navigate the nested object by clicking ![](images/03ebbe16e8858ef65636fca671a94980599ddef19f4de7c254a19a28aa8fd155.png) in the left panel of table view.

Moreover, you can click the object and show the details of the object.

![](images/d97247b7c317db7aa45a933100e99a645f5f1c788cabdc6f73277964e06d1bd1.png)  

### Viewing Object Layout
By default, you will see the object layout after you selected a object in table view. If the type of fields in the object is Primitive type or String, the value will show in the table too.
> Be aware that if the type of fields is an Object, the memory address in the table is not the actual memory of that object. It is a offset address base on the base Object address. You need to navigate into that object and check the actualy memory address of an Object **on the top of table view**.

![](images/284e4c289a52f058c17b13200a620f8958afe181a15fe64dd22eabe01d1a9608.png)  

### Viewing Class Layout
By toggling the switch ![](images/9be4b5f069ac665be606c59223d952018c2e4ea313672d90d553c42d89c5c556.png) on the top of table view, you can see the class layout of the current object that you are viewing.  

![](images/77bc038a4dfde621413004dd74a5ab80dd14fd5d09cfdfba49c5e455a498826b.png)
