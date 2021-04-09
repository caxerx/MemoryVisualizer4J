package com.caxerx.memoryvisualizer4j.demo;

public class SimpleLinkedList {
    Node first;
    Node last;

    public void add(int i) {
        if (first == null) {
            first = last = new Node(i);
            return;
        }
        last.next = new Node(i);
        last.next.prev = last;
        last = last.next;
    }
}

class Node {
    public Node(int data) {
        this.data = data;
    }

    int data;
    Node prev;
    Node next;
}