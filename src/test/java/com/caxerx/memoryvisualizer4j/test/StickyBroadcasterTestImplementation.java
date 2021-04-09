package com.caxerx.memoryvisualizer4j.test;

import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;

import java.util.List;

public class StickyBroadcasterTestImplementation implements StickyBroadcaster {
    private final List<String> stickyMessage;

    public StickyBroadcasterTestImplementation(List<String> stickyMessage) {
        this.stickyMessage = stickyMessage;
    }

    @Override
    public void broadcastSticky(String string) {
        stickyMessage.add(string);
    }
}
