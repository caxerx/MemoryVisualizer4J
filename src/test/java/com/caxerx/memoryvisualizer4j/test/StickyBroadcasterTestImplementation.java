package com.caxerx.memoryvisualizer4j.test;

import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;
import com.caxerx.memoryvisualizer4j.implementation.visualizer.ObjectMapMessage;
import com.google.gson.Gson;

import java.util.List;

public class StickyBroadcasterTestImplementation implements StickyBroadcaster {
    private final List<String> stickyMessage;

    public StickyBroadcasterTestImplementation(List<String> stickyMessage) {
        this.stickyMessage = stickyMessage;
    }

    @Override
    public void broadcastSticky(ObjectMapMessage msg) {
        stickyMessage.add(new Gson().toJson(msg));
    }
}
