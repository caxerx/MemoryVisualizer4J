/* tslint:disable */
// Generated using typescript-generator version 2.9.456 on 2021-03-25 03:37:45.

declare namespace MemoryVisualizer4J {
  interface ObjectMapMessage {
    objectType: string;
    createTime: number;
    objectLayout: ObjectLayoutTree;
  }

  interface ArrayLayout extends ClassLayout {
    itemSize: number;
  }

  interface ClassLayout {
    layout: ClassLayoutItem[];
    instanceSize: number;
    type: string;
  }

  interface ClassLayoutFieldItem extends ClassLayoutItem {
    type: string;
    name: string;
  }

  interface ClassLayoutItem {
    offset: number;
    size: number;
  }

  interface ClassLayoutPaddingItem extends ClassLayoutItem {
    type: ClassLayoutPaddingItemType;
  }

  interface ObjectLayout {
    type: string;
    fields: ObjectLayoutFieldItem[];
    memoryAddress: number;
  }

  interface ObjectLayoutFieldItem {
    name: string;
    type: string;
    memoryAddress: number;
  }

  interface ObjectLayoutObjectFieldItem extends ObjectLayoutFieldItem {}

  interface ObjectLayoutPrimitiveFieldItem extends ObjectLayoutFieldItem {
    value: any;
  }

  interface ObjectLayoutStringFieldItem extends ObjectLayoutObjectFieldItem {
    value: string;
  }

  interface ObjectLayoutTree {
    objectLayouts: { [index: string]: ObjectLayout };
    classLayouts: { [index: string]: ClassLayout };
    entryPoint: ObjectLayout;
  }

  type ClassLayoutPaddingItemType = "HEADER" | "ALIGNMENT";
}
