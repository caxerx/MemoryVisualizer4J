package com.caxerx.memoryvisualizer4j.api;

/**
 * Sticky Broadcasts announce information that remains accessible.
 */
public interface StickyBroadcaster {
    /**
     * Create a sticky broadcast.
     *
     * @param string Content of the broadcast.
     */
    void broadcastSticky(String string);
}
