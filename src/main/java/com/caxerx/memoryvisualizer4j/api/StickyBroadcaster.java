package com.caxerx.memoryvisualizer4j.api;

import com.caxerx.memoryvisualizer4j.implementation.visualizer.ObjectMapMessage;

/**
 * Sticky Broadcasts announce information that remains accessible.
 */
public interface StickyBroadcaster {
    /**
     * Create a sticky broadcast.
     *
     * @param message Message to broadcast.
     */
    void broadcastSticky(ObjectMapMessage message);
}
